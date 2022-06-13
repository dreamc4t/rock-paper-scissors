package eriksund.rockpaperscissors.repositories;

import eriksund.rockpaperscissors.models.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlayerRepository extends MongoRepository<Player, String> {
}
