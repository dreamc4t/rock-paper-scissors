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
    private String p1Name;
    private String p2Name;

    private String p1Move;
    private String p2Move;

    private String winner;



    public  Game() {
    }

    public Game(String p1Name) {
        this.p1Name = p1Name;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getP1Name() {
        return p1Name;
    }

    public void setP1Name(String p1Name) {
        this.p1Name = p1Name;
    }

    public String getP2Name() {
        return p2Name;
    }

    public void setP2Name(String p2Name) {
        this.p2Name = p2Name;
    }

    public String getP1Move() {
        return p1Move;
    }

    public void setP1Move(String p1Move) {
        this.p1Move = p1Move;
    }

    public String getP2Move() {
        return p2Move;
    }

    public void setP2Move(String p2Move) {
        this.p2Move = p2Move;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}








