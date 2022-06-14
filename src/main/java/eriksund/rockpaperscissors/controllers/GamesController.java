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
        return ResponseEntity.ok(game);
    }
    /* Hämta en lista över alla games */
    @GetMapping("/all")
    public ResponseEntity<?> getAllGames() {

        return ResponseEntity.ok(listOfGames);
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
            return ResponseEntity.ok(setWinner);
        }


        return ResponseEntity.ok(String.format("%s gjorde sitt move - %s", move.getPlayerName(), move.getMove()));

    }



    /* Köra spelet. Spelets logik osv. Då det känns svårt att skala upp
    sten sax påse nöjde jag mig med ett gäng if-satser istället för en mer
    avancerad logik*/
    private String runGame(Game g) {

        //move+namn för givet game skrivs om för att ge bättre läsförståelse i resten av metoden
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
