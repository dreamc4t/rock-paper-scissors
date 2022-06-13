package eriksund.rockpaperscissors.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "games")
public class Game {

    enum Moves {
        ROCK,
        PAPER,
        SCISSORS
    }

    @Id
    private String id;
    private String player1name;
    private String player2name;



    public Game() {
    }



    public Game(String player1name) {
        this.player1name = player1name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer1name() {
        return player1name;
    }

    public void setPlayer1name(String player1name) {
        this.player1name = player1name;
    }

    public String getPlayer2name() {
        return player2name;
    }

    public void setPlayer2name(String player2name) {
        this.player2name = player2name;
    }
}
