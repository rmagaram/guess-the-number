/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.guessthenumberapi.controllers;

import corbos.guessthenumberapi.data.GameDao;
import corbos.guessthenumberapi.models.Game;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rachellemagaram
 */
@RestController
@RequestMapping("/api/guessthenumber")
public class GameController {
    
    private final GameDao dao;
    
    public GameController(GameDao dao) {
        this.dao = dao;
    }
    
    @GetMapping("/game")
    public List<Game> allGames() {
        return dao.getAllGames();
    }
    
    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public Game beginGame(@RequestBody Game game) {
        return dao.addGame(game);
    }
    
    @GetMapping("/game/{gameID}")
    public ResponseEntity<Game> findByGameID(@PathVariable int gameID) {
        Game result = dao.findByGameID(gameID);
        if (result == null) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result);
    }
    
    
}
