/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.guessthenumberapi.controllers;

import corbos.guessthenumberapi.data.RoundDao;
import corbos.guessthenumberapi.models.Round;
import java.util.List;
import org.springframework.http.HttpStatus;
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
public class RoundController {
    
    private final RoundDao dao;
    
    public RoundController(RoundDao dao) {
        this.dao = dao;
    }
    
    @GetMapping("/rounds/{gameID}")
    public List<Round> allRounds(@PathVariable int gameID) {
        return dao.getAllRounds(gameID);
    }
    
    @PostMapping("/guess")
    @ResponseStatus(HttpStatus.CREATED)
    public Round guess(@RequestBody Round round) {
        return dao.addGuess(round);
    }
    
}
