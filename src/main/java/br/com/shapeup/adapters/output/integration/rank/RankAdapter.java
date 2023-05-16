package br.com.shapeup.adapters.output.integration.rank;

import br.com.shapeup.adapters.input.web.controller.response.rank.RankResponse;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.utils.StackObj;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.rank.RankOutput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RankAdapter implements RankOutput {
    private final UserJpaRepository userJpaRepository;


    @Override
    public List<RankResponse> getRank(User user, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        UUID userUUID = UUID.fromString(user.getId().getValue());

        Page<UserEntity> rank = userJpaRepository.getUserRank(userUUID, pageRequest);

        return rank
                .map(this::toRankResponse)
                .toList();
    }

    @Override
    public List<RankResponse> getGlobalRank(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<UserEntity> rank = userJpaRepository.findAllByOrderByXpDesc(pageRequest);

        return rank
                .map(this::toRankResponse)
                .toList();
    }

    private RankResponse toRankResponse(UserEntity userEntity) {
        return new RankResponse(
                userEntity.getName(),
                userEntity.getLastName(),
                userEntity.getUsername(),
                userEntity.getProfilePicture(),
                userEntity.getXp()
        );
    }

    private UserEntity toUserEntity(RankResponse rankResponse) {
        return new UserEntity(
                rankResponse.getName(),
                rankResponse.getLastName(),
                rankResponse.getUsername(),
                rankResponse.getProfilePicture(),
                rankResponse.getXp()
        );
    }

    @Override
    public List<RankResponse> getGlobalRankPilha() {
        List<UserEntity> rank = userJpaRepository.findTop10ByOrderByBirth();

        StackObj<UserEntity> pilha = bubbleSortAndPush(rank);

        List<RankResponse> rankResponse = new ArrayList<>();

        while (!pilha.isEmpty()) {
            UserEntity user = pilha.pop();
            rankResponse.add(toRankResponse(user));
        }

        return rankResponse;
    }

    public static StackObj<UserEntity> bubbleSortAndPush(List<UserEntity> usuarios) {
        int length = usuarios.size();

        StackObj<UserEntity> pilha = new StackObj<>(length);

        for (int i = 0; i < length - 1; i++)
        {
            for (int j = 0; j < length - 1 - i; j++)
            {
                if (usuarios.get(j).getXp() < usuarios.get(j + 1).getXp())
                {
                    UserEntity user = usuarios.get(j);
                    usuarios.set(j, usuarios.get(j + 1));
                    usuarios.set(j + 1, user);
                }
            }
            pilha.push(usuarios.get(length - 1 - i));
        }
        pilha.push(usuarios.get(0));

        return pilha;
    }
}
