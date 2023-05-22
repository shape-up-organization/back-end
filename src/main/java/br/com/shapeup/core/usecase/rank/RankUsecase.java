package br.com.shapeup.core.usecase.rank;

import br.com.shapeup.adapters.input.web.controller.response.rank.RankResponse;
import br.com.shapeup.core.domain.user.User;
import br.com.shapeup.core.ports.input.rank.RankInput;
import br.com.shapeup.core.ports.output.rank.RankOutput;
import br.com.shapeup.core.ports.output.user.FindUserOutput;
import java.util.List;

public class RankUsecase implements RankInput {
    private final FindUserOutput findUserOutput;

    private final RankOutput rankOutput;

    public RankUsecase(FindUserOutput findUserOutput, RankOutput rankOutput) {
        this.findUserOutput = findUserOutput;
        this.rankOutput = rankOutput;
    }

    @Override
    public List<RankResponse> getRank(String email, int page, int size) {
        User user = findUserOutput.findByEmail(email);

        return rankOutput.getRank(user, page, size);
    }

    @Override
    public List<RankResponse> getGlobalRank(int page, int size) {
        return rankOutput.getGlobalRank(page, size);
    }

    @Override
    public List<RankResponse> getGlobalRankPilha() {
        return rankOutput.getGlobalRankPilha();
    }
}
