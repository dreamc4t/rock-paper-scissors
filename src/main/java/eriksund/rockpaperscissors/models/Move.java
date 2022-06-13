package eriksund.rockpaperscissors.models;

public class Move {
    public String playerName;
    public String move;

    public Move(String playerName, String move) {
        this.playerName = playerName;
        this.move = move;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }
}
