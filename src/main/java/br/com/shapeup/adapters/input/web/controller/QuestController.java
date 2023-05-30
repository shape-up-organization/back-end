package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.mapper.quest.TrainingHttpMapper;
import br.com.shapeup.adapters.input.web.controller.request.quest.TrainingUserRequest;
import br.com.shapeup.adapters.input.web.controller.response.quest.FindTrainingsOfUserResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingDayResponse;
import br.com.shapeup.adapters.input.web.controller.response.quest.TrainingResponse;
import br.com.shapeup.adapters.output.repository.model.quest.TrainingDayEntity;
import br.com.shapeup.common.domain.enums.CategoryEnum;
import br.com.shapeup.common.utils.DayOfWeekUtils;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.domain.quest.dto.TrainingDayEntityDto;
import br.com.shapeup.core.domain.quest.training.Training;
import br.com.shapeup.core.ports.input.quest.FindTrainingDaysFromUserInputPort;
import br.com.shapeup.core.ports.input.quest.FinishTrainingInputPort;
import br.com.shapeup.core.ports.input.quest.PeriodicUpdateUncompletedUserTrainingInputPort;
import br.com.shapeup.core.ports.input.quest.QuestInputPort;
import br.com.shapeup.core.ports.input.quest.RemoveTrainingFromUserInputPort;
import br.com.shapeup.core.ports.input.quest.SearchAllTrainingsInputPort;
import br.com.shapeup.core.ports.input.quest.UpdateTrainingOfUserInputPort;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@CrossOrigin(origins = "*")
@RequestMapping("/shapeup/quests")
public class QuestController {

    private final QuestInputPort questInputPort;
    private final FindTrainingDaysFromUserInputPort findTrainingDaysFromUserInputPort;
    private final TrainingHttpMapper trainingHttpMapper;
    private final RemoveTrainingFromUserInputPort removeTrainingFromUserInputPort;
    private final FinishTrainingInputPort finishTrainingInputPort;
    private final PeriodicUpdateUncompletedUserTrainingInputPort periodicUpdateUncompletedUserTrainingInputPort;
    private final SearchAllTrainingsInputPort searchAllTrainingsInputPort;
    private final UpdateTrainingOfUserInputPort updateTrainingOfUserInputPort;

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
    public ResponseEntity<List<FindTrainingsOfUserResponse>> searchTrainingByUserId(HttpServletRequest request) {
        String token = TokenUtils.getToken(request);
        String userId = JwtService.extractIdFromToken(token);

        List<TrainingDayEntity> trainingsDaysUser = findTrainingDaysFromUserInputPort
                .execute(UUID.fromString(userId));

        List<FindTrainingsOfUserResponse> trainingsOfUserResponses = trainingHttpMapper.toFindTrainingsOfUserResponse(trainingsDaysUser);

        return ResponseEntity.status(HttpStatus.OK.value()).body(trainingsOfUserResponses);
    }

    @PostMapping("/user/add-training")
    public ResponseEntity<TrainingDayEntityDto> addTrainingToUser(
            HttpServletRequest request,
            @RequestBody TrainingUserRequest trainingUserRequest
    ) {
        String token = TokenUtils.getToken(request);
        String username = JwtService.extractAccountNameFromToken(token);
        String dayOfWeekRequest = trainingUserRequest.dayOfWeek().toUpperCase();

        String currentDayName = LocalDate.now().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();
        Integer currentDayValue = DayOfWeekUtils.abbreviations().get(currentDayName);

        Training training = questInputPort.addTrainingToUser(username, trainingUserRequest);
        Map<String, Integer> dayOfWeekAbbreviations = DayOfWeekUtils.abbreviations();
        int dayOfWeekFromAbbreviations = dayOfWeekAbbreviations.get(dayOfWeekRequest);

        TrainingDayResponse trainingDayResponse = new TrainingDayResponse(
                dayOfWeekRequest,
                trainingUserRequest.period()
        );

        if (dayOfWeekFromAbbreviations < currentDayValue) {
            TrainingDayEntityDto trainingUserResponse = new TrainingDayEntityDto(
                    training.getId().getValue().toString(),
                    training.getCategory().name(),
                    trainingDayResponse,
                    training.getExercises(),
                    training.getXp(),
                    "UNCOMPLETED"
            );

            return ResponseEntity.ok(trainingUserResponse);
        }

        TrainingDayEntityDto trainingUserResponse = new TrainingDayEntityDto(
                training.getId().getValue().toString(),
                training.getCategory().name(),
                trainingDayResponse,
                training.getExercises(),
                training.getXp(),
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
    public ResponseEntity<TrainingDayEntityDto> finishTraining(
            HttpServletRequest request,
            @RequestBody TrainingUserRequest trainingUserRequest
    ) {
        String token = TokenUtils.getToken(request);
        String username = JwtService.extractAccountNameFromToken(token);

        TrainingDayEntityDto trainingDayEntityDto = finishTrainingInputPort.execute(
                username,
                trainingUserRequest.trainingId(),
                trainingUserRequest.dayOfWeek().toUpperCase(),
                trainingUserRequest.period().toUpperCase()
        );

        if(trainingDayEntityDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(trainingDayEntityDto);
    }

    @Scheduled(cron = "* 59 23 * * 0", zone = "America/Sao_Paulo")
    @PutMapping("/user/periodic-training-update")
    public ResponseEntity<?> periodicTrainingUpdate() {
        log.info("Periodic training update started");
        periodicUpdateUncompletedUserTrainingInputPort.execute();
        log.info("Periodic training update finished");

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/trainings")
    public ResponseEntity<List<TrainingResponse>> searchAllTrainings() {
        List<Training> trainings = searchAllTrainingsInputPort.execute();
        List<TrainingResponse> trainingResponses = trainingHttpMapper.toTrainingResponse(trainings);

        return ResponseEntity.status(HttpStatus.OK.value()).body(trainingResponses);
    }

    @PutMapping("/user/update-training")
    public ResponseEntity<TrainingDayEntityDto> updateTraining(
            @RequestBody TrainingUserRequest trainingUserRequest,
            HttpServletRequest request
    ) {
        String token = TokenUtils.getToken(request);
        String userId = JwtService.extractIdFromToken(token);
        TrainingDayEntityDto trainingDayEntityDto = updateTrainingOfUserInputPort.execute(trainingUserRequest, userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(trainingDayEntityDto);
    }
}
