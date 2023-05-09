package br.com.shapeup.adapters.output.integration.rank;

import br.com.shapeup.adapters.input.web.controller.response.rank.RankResponse;
import br.com.shapeup.adapters.output.repository.jpa.user.UserJpaRepository;
import br.com.shapeup.adapters.output.repository.model.user.UserEntity;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.rank.RankOutput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
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
}
