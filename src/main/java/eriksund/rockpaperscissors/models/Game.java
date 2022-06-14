package eriksund.rockpaperscissors.models;




import java.util.UUID;

public class Game {

    UUID id;
    private String p1Name;
    private String p2Name;
    private String p1Move;
    private String p2Move;
    private String winner;

    //redundant constructor för nu, men kan komma att användas längre fram, tex i tester
    public  Game() {
        //skapa ett UUID som ID för varje nytt game
        setId(UUID.randomUUID());
    }

    public Game(String p1Name) {
        this.p1Name = p1Name;
        setId(UUID.randomUUID());
    }


    /* Köra spelet. Då det känns svårt att skala upp sten sax påse
    nöjde jag mig med ett gäng if-satser istället för en mer
    avancerad logik */
    public String runGame() {

        // move+namn för givet game skrivs om för att ge bättre läsförståelse i resten av metoden
        String p1move = this.getP1Move();
        String p2move = this.getP2Move();
        String p1Name = this.getP1Name();
        String p2Name = this.getP2Name();


        if (p1move.equals(p2move)) {
//            System.out.println("Det blev oavgjort!");
            this.setWinner("Oavgjort");
            return String.format("Båda spelarna tog %s. Oavgjort!", p1move);
        }

        if (p1move.equals("rock") && p2move.equals("paper")) {
            this.setWinner(p2Name);
        }
        if (p1move.equals("rock") && p2move.equals("scissors")) {
            this.setWinner(p1Name);
        }
        if (p1move.equals("paper") && p2move.equals("scissors")) {
            this.setWinner(p2Name);
        }
        if (p1move.equals("paper") && p2move.equals("rock")) {
            this.setWinner(p1Name);
        }
        if (p1move.equals("scissors") && p2move.equals("rock")) {
            this.setWinner(p2Name);
        }
        if (p1move.equals("scissors") && p2move.equals("paper")) {
            this.setWinner(p1Name);
        }
        return String.format("%s gjorde %s och %s gjorde %s. Vinnaren är %s! Grattis", p1Name, p1move, p2Name, p2move, this.getWinner());
    }

    /* Getters och setters */
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
        if (p2Name.equals(this.getP1Name())) {
            this.p2Name = p2Name + "_1";
        }
        else {
            this.p2Name = p2Name;
        }
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








