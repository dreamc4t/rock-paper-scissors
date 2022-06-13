package eriksund.rockpaperscissors.controllers;


import eriksund.rockpaperscissors.models.Game;
import eriksund.rockpaperscissors.models.Move;
import eriksund.rockpaperscissors.repositories.GamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
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

        Game game = new Game(g.getP1Name());
        gamesRepository.save(game);

        return ResponseEntity.ok("Nytt game startat av dig: " + game.getP1Name() + ". Game-ID:t är " + game.getId());
    }


    @PutMapping("/{id}/join")
    public ResponseEntity<?> joinGame(@PathVariable("id") String id, @RequestBody String p2Name) {

        //om match med ID:t finns, lägg till spelare2
        Optional<Game> gameData = gamesRepository.findById(id);
        Game game = gameData.get();
        game.setP2Name(p2Name);

        gamesRepository.save(game);

        return ResponseEntity.ok("Du har nu anslutit dig till spelet, " + game.getP2Name() + "! Game-ID:t är " + game.getId());

    }

    @PutMapping("/{id}/move")
    public ResponseEntity<?> makeAMove(@PathVariable("id") String id, @RequestBody Move mo) {

        Optional<Game> gameData = gamesRepository.findById(id);
        Game game = gameData.get();

        Move move = new Move(mo.getPlayerName(), mo.getMove().toLowerCase());

        if (game.getP1Name().equals(move.getPlayerName()) ) {
            game.setP1Move(move.getMove());
        }
        if (game.getP2Name().equals(move.getPlayerName()) ) {
            game.setP2Move(move.getMove());
        }


        runGame(game);

        gamesRepository.save(game);

        return ResponseEntity.ok("trying to make a move..." + move.getPlayerName() + " " +  game.getP1Name() + " " + game.getP2Name() + "\n" +  move.getMove()+ "\n" + mo.getMove());

    }
    
    private void runGame(Game g) {
        String p1move = g.getP1Move();
        String p2move = g.getP2Move();
        String p1Name = g.getP1Name();
        String p2Name = g.getP2Name();

        System.out.println(p1Name + " tog " + p1move);
        System.out.println(p2Name + " tog " + p2move);

        if (p1move.equals(p2move)) {
            System.out.println("Samma move var det här ja");
            g.setWinner("Oavgjort");
            return;
        }

        if (p1move.equals("rock") && p2move.equals("paper")) {
            g.setWinner(p2Name);
        }
        if (p1move.equals("rock") && p2move.equals("scissors")) {
            g.setWinner(p1Name);
        }









        }

}