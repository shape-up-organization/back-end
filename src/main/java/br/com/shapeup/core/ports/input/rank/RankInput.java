package br.com.shapeup.core.ports.input.rank;

import br.com.shapeup.adapters.input.web.controller.response.rank.RankResponse;
import java.util.List;

public interface RankInput {
    List<RankResponse> getRank(String email, int page, int size);

    List<RankResponse> getGlobalRank(int page, int size);

    List<RankResponse> getGlobalRankPilha();
}
