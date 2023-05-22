package br.com.shapeup.core.ports.output.rank;

import br.com.shapeup.adapters.input.web.controller.response.rank.RankResponse;
import br.com.shapeup.core.domain.user.User;
import java.util.List;

public interface RankOutput {
    List<RankResponse> getRank(User user, int page, int size);

    List<RankResponse> getGlobalRank(int page, int size);

    List<RankResponse> getGlobalRankPilha();
}
