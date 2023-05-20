package br.com.shapeup.adapters.output.integration.rank;

import br.com.shapeup.adapters.input.web.controller.response.rank.RankResponse;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.common.utils.StackObj;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.output.rank.RankOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

    @Override
    public List<RankResponse> getGlobalRankPilha() {
        List<UserEntity> rank = userJpaRepository.findTop10ByOrderByBirth();

        StackObj<UserEntity> stack = bubbleSortAndPush(rank);

        List<RankResponse> rankResponse = new ArrayList<>();

        while (!stack.isEmpty()) {
            UserEntity user = stack.pop();
            rankResponse.add(toRankResponse(user));
        }

        return rankResponse;
    }

    public static StackObj<UserEntity> bubbleSortAndPush(List<UserEntity> users) {
        int userSize = users.size();

        StackObj<UserEntity> stack = new StackObj<>(userSize);

        for (int i = 0; i < userSize - 1; i++)
        {
            for (int j = 0; j < userSize - 1 - i; j++)
            {
                if (users.get(j).getXp() < users.get(j + 1).getXp())
                {
                    UserEntity user = users.get(j);
                    users.set(j, users.get(j + 1));
                    users.set(j + 1, user);
                }
            }
            stack.push(users.get(userSize - 1 - i));
        }
        stack.push(users.get(0));

        return stack;
    }
}
