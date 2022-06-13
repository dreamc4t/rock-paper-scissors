package eriksund.rockpaperscissors.controllers;


import eriksund.rockpaperscissors.models.Game;
import eriksund.rockpaperscissors.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/games")
public class GamesController {

    @Autowired
    GamesRepository gamesRepository;

    @GetMapping("/all")
    public List<Game> getAllGames() {
        return gamesRepository.findAll();
    }

    @PostMapping("/new")
    public ResponseEntity<?> newGame(@RequestBody Game g) {

        Game game = new Game(g.getPlayer1name());
        gamesRepository.save(game);

        return ResponseEntity.ok("Nytt game startat av dig: " + game.getPlayer1name() + ". Game-ID:t är " + game.getId());
    }


    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinGame(@PathVariable("id") String id, @RequestBody String player2Name) {

        //om match med ID:t finns, lägg till spelare2
        Optional<Game> gameData = gamesRepository.findById(id);
        Game game = gameData.get();
        game.setPlayer2name(player2Name);

        gamesRepository.save(game);

        return ResponseEntity.ok("Du har nu anslutit dig till spelet, " + game.getPlayer2name() + "! Game-ID:t är " + game.getId());

    }



    @PostMapping("/{id}/move")
    public ResponseEntity<?> move(@PathVariable String id, @RequestBody Move m) {

        return ResponseEntity.ok("SPELARE HAR GJORT ETT MOVE ");
    }



}
