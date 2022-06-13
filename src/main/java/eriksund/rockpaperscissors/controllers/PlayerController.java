package eriksund.rockpaperscissors.controllers;

import eriksund.rockpaperscissors.models.Player;
import eriksund.rockpaperscissors.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/all")
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @PostMapping("/new")
    public ResponseEntity<?> addPlayer(@RequestBody Player p) {

        Player player = new Player(p.getName(), p.getEmail());
        playerRepository.save(player);
        System.out.println("Added player with name " + p.getName());

        return ResponseEntity.ok("New player added to DB!");
    }
}
