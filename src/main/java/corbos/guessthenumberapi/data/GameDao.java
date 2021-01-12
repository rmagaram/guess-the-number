/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.guessthenumberapi.data;
import corbos.guessthenumberapi.models.Game;
import java.util.List;

/**
 *
 * @author rachellemagaram
 */
public interface GameDao {
    
    Game addGame(Game game);

    List<Game> getAllGames();

    Game findByGameID(int gameID);
    
    boolean update(Game game);
    
}
