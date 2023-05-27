package br.com.shapeup.adapters.input.web.controller;

import br.com.shapeup.adapters.input.web.controller.response.rank.RankResponse;
import br.com.shapeup.common.utils.TokenUtils;
import br.com.shapeup.core.ports.input.rank.RankInput;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shapeup/rank")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RankController {
    private final RankInput rankInput;
    @GetMapping("/friends")
    public ResponseEntity<List<RankResponse>> getRank(HttpServletRequest jwtToken,
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size
    ){
        String token = TokenUtils.getToken(jwtToken);
        String email = JwtService.extractEmailFromToken(token);

        List<RankResponse> rank = rankInput.getRank(email, page, size);

        if(rank.isEmpty())
            return ResponseEntity.status(204).build();


        return ResponseEntity.status(200).body(rank);
    }

    @GetMapping("/global")
    public ResponseEntity<List<RankResponse>> getGlobalRank(
                                                      @RequestParam("page") int page,
                                                      @RequestParam("size") int size
    ){
        List<RankResponse> rank = rankInput.getGlobalRank(page, size);

        if(rank.isEmpty())
            return ResponseEntity.status(204).build();


        return ResponseEntity.status(200).body(rank);
    }

    @GetMapping("/global/pilha")
    public ResponseEntity<List<RankResponse>> getGlobalRankPilha(
    ){
        List<RankResponse> rank = rankInput.getGlobalRankPilha();

        return ResponseEntity.status(200).body(rank);
    }
}
