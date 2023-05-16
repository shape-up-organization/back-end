package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.quest.TrainingHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.quest.TrainingUserRequest;
import br.com.shapeup.adapters.input.web.controller.response.quest.ExerciseResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingResponse;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.domain.quest.dto.TrainingUserWithStatus;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.ports.input.quest.FinishTrainingInputPort;
import br.com.shapeup.core.ports.input.quest.PeriodicUpdateUncompletedUserTrainingInputPort;
import br.com.shapeup.core.ports.input.quest.QuestInputPort;
import br.com.shapeup.core.ports.input.quest.RemoveTrainingFromUserInputPort;
import br.com.shapeup.core.ports.input.quest.TrainingsUserWithFullStatusInputPort;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/quests")
public class QuestController {

    private final QuestInputPort questInputPort;
    private final TrainingsUserWithFullStatusInputPort trainingsUserWithFullStatusInputPort;
    private final TrainingHttpMapper trainingHttpMapper;
    private final RemoveTrainingFromUserInputPort removeTrainingFromUserInputPort;
    private final FinishTrainingInputPort finishTrainingInputPort;
    private final PeriodicUpdateUncompletedUserTrainingInputPort periodicUpdateUncompletedUserTrainingInputPort;

    @GetMapping("/search-training")
    public ResponseEntity<List<TrainingResponse>> searchTrainingByName(
            HttpServletRequest request,
            @RequestParam String name
    ) {
        List<Training> trainings = questInputPort.searchTrainingByName(name);
        List<TrainingResponse> trainingResponses = trainingHttpMapper.toTrainingResponse(trainings);

        return ResponseEntity.status(HttpStatus.OK.value()).body(trainingResponses);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<TrainingResponse>> searchTrainingByCategory(
            HttpServletRequest request,
            @PathVariable String category
    ) {
        CategoryEnum categoryEnum = CategoryEnum.valueOf(category.toUpperCase());
        List<Training> trainings = questInputPort.searchTrainingByCategory(categoryEnum);
        List<TrainingResponse> trainingResponses = trainingHttpMapper.toTrainingResponse(trainings);

        return ResponseEntity.status(HttpStatus.OK.value()).body(trainingResponses);
    }

    @GetMapping("/user/trainings")
    public ResponseEntity<List<TrainingUserWithStatus>> searchTrainingByUserId(HttpServletRequest request) {
        String token = TokenUtils.getToken(request);
        String userId = JwtService.extractIdFromToken(token);

        List<TrainingUserWithStatus> trainingUserWithStatus = trainingsUserWithFullStatusInputPort
                .getTrainingsUserWithFullStatus(UUID.fromString(userId));

        return ResponseEntity.status(HttpStatus.OK.value()).body(trainingUserWithStatus);
    }

    @PostMapping("/user/add-training")
    public ResponseEntity<TrainingUserWithStatus> addTrainingToUser(
            HttpServletRequest request,
            @RequestBody TrainingUserRequest trainingUserRequest
    ) {
        String token = TokenUtils.getToken(request);
        String username = JwtService.extractAccountNameFromToken(token);

        Training training = questInputPort.addTrainingToUser(username, trainingUserRequest);

        TrainingDayResponse trainingDayResponse = new TrainingDayResponse(
                trainingUserRequest.dayOfWeek(),
                trainingUserRequest.period()
        );

        List<ExerciseResponse> exercisesResponse = new ArrayList<>();

        training.getExercises().forEach(exercise -> {
            ExerciseResponse exerciseResponse = new ExerciseResponse(
                    exercise.getId().getValue().toString(),
                    exercise.getExercise(),
                    exercise.getDuration()
            );

            exercisesResponse.add(exerciseResponse);
        });

        TrainingUserWithStatus trainingUserResponse = new TrainingUserWithStatus(
                training.getId().getValue().toString(),
                training.getCategory().name(),
                trainingDayResponse,
                exercisesResponse,
                "PENDING"
        );

        return ResponseEntity.ok(trainingUserResponse);
    }

    @DeleteMapping("/user/remove-training")
    public ResponseEntity<?> removeTrainingsFromUser(
            HttpServletRequest request,
            @RequestBody TrainingUserRequest trainingUserRequest
    ) {
        String token = TokenUtils.getToken(request);
        String username = JwtService.extractAccountNameFromToken(token);

        removeTrainingFromUserInputPort.execute(
                username,
                trainingUserRequest.trainingId(),
                trainingUserRequest.dayOfWeek(),
                trainingUserRequest.period()
        );

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/user/finish-training")
    public ResponseEntity<TrainingUserWithStatus> finishTraining(
            HttpServletRequest request,
            @RequestBody TrainingUserRequest trainingUserRequest
    ) {
        String token = TokenUtils.getToken(request);
        String username = JwtService.extractAccountNameFromToken(token);

        TrainingUserWithStatus trainingUserWithStatus = finishTrainingInputPort.execute(
                username,
                trainingUserRequest.trainingId(),
                trainingUserRequest.dayOfWeek(),
                trainingUserRequest.period()
        );

        if(trainingUserWithStatus == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(trainingUserWithStatus);
    }

    @Scheduled(cron = "0 0 * * 7 *")
    @PutMapping("/user/periodic-training-update")
    public ResponseEntity<?> periodicTrainingUpdate() {
        periodicUpdateUncompletedUserTrainingInputPort.execute();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
