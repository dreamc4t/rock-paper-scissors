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

    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable("id") String id) {

        Optional<Game> gameData = gamesRepository.findById(id);
        return ResponseEntity.ok(gameData);
    }

    @PostMapping("/new")
    public ResponseEntity<?> newGame(@RequestBody Game g) {

        Game game = new Game(g.getP1Name());
        gamesRepository.save(game);

        return ResponseEntity.ok(String.format("Nytt game startat av dig, %s. Game-ID:t är %s", game.getP1Name(), game.getId()));
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


        //om båda spelare gjort move - se vem som vinner
        if (game.getP1Move() != null && game.getP2Move() != null) {
            String setWinner = runGame(game);
            gamesRepository.save(game);
            return ResponseEntity.ok(setWinner);
        }

        gamesRepository.save(game);

        return ResponseEntity.ok(String.format("%s gjorde sitt move - %s", move.getPlayerName(), move.getMove()));

    }




    private String runGame(Game g) {
        String p1move = g.getP1Move();
        String p2move = g.getP2Move();
        String p1Name = g.getP1Name();
        String p2Name = g.getP2Name();

//        System.out.println(p1Name + " tog " + p1move);
//        System.out.println(p2Name + " tog " + p2move);

        if (p1move.equals(p2move)) {
//            System.out.println("Det blev oavgjort!");
            g.setWinner("Oavgjort");
            return String.format("Båda spelarna tog %s. Oavgjort!", p1move);
        }

        if (p1move.equals("rock") && p2move.equals("paper")) {
            g.setWinner(p2Name);
        }
        if (p1move.equals("rock") && p2move.equals("scissors")) {
            g.setWinner(p1Name);
        }
        if (p1move.equals("paper") && p2move.equals("scissors")) {
            g.setWinner(p2Name);
        }
        if (p1move.equals("paper") && p2move.equals("rock")) {
            g.setWinner(p1Name);
        }
        if (p1move.equals("scissors") && p2move.equals("rock")) {
            g.setWinner(p2Name);
        }
        if (p1move.equals("scissors") && p2move.equals("paper")) {
            g.setWinner(p1Name);
        }
//        if (g.getWinner().equals(p1Name)) {
//            System.out.println(p1Name + " vann!");
//        }
//        if (g.getWinner().equals(p2Name)) {
//            System.out.println(p2Name + " vann!");
//        }
        return String.format("%s gjorde %s och %s gjorde %s. Vinnaren är %s! Grattis", p1Name, p1move, p2Name, p2move, g.getWinner());
    }

}
