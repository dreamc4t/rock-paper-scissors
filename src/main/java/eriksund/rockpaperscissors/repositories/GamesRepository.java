package eriksund.rockpaperscissors.repositories;

import eriksund.rockpaperscissors.models.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GamesRepository extends MongoRepository<Game, String> {
}
