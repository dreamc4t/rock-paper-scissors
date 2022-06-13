package eriksund.rockpaperscissors.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "games")
public class Game {

    @Id
    private String id;

    private Player player1;
    private Player player2;
}
