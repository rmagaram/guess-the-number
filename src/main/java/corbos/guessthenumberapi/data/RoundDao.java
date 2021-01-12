/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.guessthenumberapi.data;
import corbos.guessthenumberapi.models.Game;
import corbos.guessthenumberapi.models.Round;
import java.util.List;

/**
 *
 * @author rachellemagaram
 */
public interface RoundDao {
    
    Round addGuess(Round r);

    List<Round> getAllRounds(int gameID);
    
    
}
