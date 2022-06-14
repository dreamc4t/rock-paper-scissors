package eriksund.rockpaperscissors.controllers;


import eriksund.rockpaperscissors.models.Game;
import eriksund.rockpaperscissors.models.Move;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/games")
public class GamesController {

    /* Skapa en lista över games, ArrayList pga dynamisk */
    ArrayList<Game> listOfGames= new ArrayList<Game>();

    /* Hämta ett game via dess ID */
    @GetMapping("/{id}")
    public ResponseEntity<?> getGameById(@PathVariable("id") String id) {
        //UUID för stort för att ha i @PathVariable, därför måste det konverteras från sträng till UUID
        UUID uuid = UUID.fromString(id);
        //om match med ID:t finns, lägg till spelare2
        Game game = listOfGames.stream().filter(listOfGames ->uuid.equals(listOfGames.getId())).findAny().orElse(null);

        //Ej kunna se spelets tillstånd förrän en vinnare är utsedd (för att ej kunna se den andres drag)
        if (game.getWinner() != null) {
            return ResponseEntity.ok(game);
        }
        else {
            return ResponseEntity.ok("Både måste göra sina drag innan ni kan se spelet, annars ser ni vad motståndaren tagit för drag!");
        }

    }
    /* Hämta en lista över alla games */
    @GetMapping("/history")
    public ResponseEntity<?> getAllFinishedGames() {

        //endast avklarade games visas, för att inte kunna sneaka in och se pågående och vad motståndaren har tagit!
        List<Game> tempListOfGames = listOfGames.stream()
                .filter(game -> game.getWinner() != null).toList();

        //return ResponseEntity.ok(listOfGames);
        return ResponseEntity.ok(tempListOfGames);
    }

    /* Skapa ett nytt game, med ens eget namn som body
    * Alt lägga till så att man kan göra ett move i skapandet av gamet också
    * aka overloada constructorn */
    @PostMapping("/new")
    public ResponseEntity<?> newGame(@RequestBody Game g) {

        Game game = new Game(g.getP1Name());
        listOfGames.add(game);

        return ResponseEntity.ok(String.format("Nytt game startat av dig, %s. Game-ID:t är %s", game.getP1Name(), game.getId()));
    }

    /* Joina ett game utifrån ID, skicka med ens namn som body
    är det samma namn på båda spelarna läggs _1 till efter namnet (och syns i Response)
    * alt även här lägga till så att man kan skicka med sitt move direkt */
    @PutMapping("/join/{id}")
    public ResponseEntity<?> joinGame(@PathVariable("id") String id, @RequestBody String p2Name) {
        //UUID för stort för att ha i @PathVariable, därför måste det konverteras från sträng till UUID
        UUID uuid = UUID.fromString(id);
        //om match med ID:t finns, lägg till spelare2
        Game game = listOfGames.stream().filter(listOfGames ->uuid.equals(listOfGames.getId())).findAny().orElse(null);
        game.setP2Name(p2Name);

        return ResponseEntity.ok("Du har nu anslutit dig till spelet, " + game.getP2Name() + "! Game-ID:t är " + game.getId());

    }

    /* Göra sitt move. Rätt game nås via ID, sedan skickar man med namn + move */
    @PutMapping("/move/{id}")
    public ResponseEntity<?> makeAMove(@PathVariable("id") String id, @RequestBody Move mo) {
        UUID uuid = UUID.fromString(id);
        Game game = listOfGames.stream().filter(listOfGames ->uuid.equals(listOfGames.getId())).findAny().orElse(null);

        //skapar move utifrån modellen move
        Move move = new Move(mo.getPlayerName(), mo.getMove().toLowerCase());

        //if else för att se om movet som skickas med i body är 'rock', 'paper' eller 'scissors'
        if (move.getMove().equals("rock")|| move.getMove().equals("paper") || move.getMove().equals("scissors")) {
            if (game.getP1Name().equals(move.getPlayerName()) ) {
                game.setP1Move(move.getMove());
            }
            if (game.getP2Name().equals(move.getPlayerName()) ) {

                game.setP2Move(move.getMove());
            }


            //om båda spelare gjort move - se vem som vinner
            if (game.getP1Move() != null && game.getP2Move() != null) {
                String setWinner = game.runGame();
                return ResponseEntity.ok(setWinner);
            }


            return ResponseEntity.ok(String.format("%s gjorde sitt move - %s", move.getPlayerName(), move.getMove()));
        }
        else {
            return ResponseEntity.ok("Endast 'rock', 'paper' eller 'scissors' är tillåtna moves!");
        }

    }

}
