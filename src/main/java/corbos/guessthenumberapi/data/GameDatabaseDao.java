/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.guessthenumberapi.data;

import corbos.guessthenumberapi.models.Game;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rachellemagaram
 */
@Repository
public class GameDatabaseDao implements GameDao{
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public GameDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    public String generateAnswer() {
        String answer = "";
        Random r = new Random();
        while(answer.length()<4) {
            int digit = r.nextInt(10);
            String digitS = String.valueOf(digit);
            if(!answer.contains(digitS)) {
                answer+=digitS;
            }
       }
        return answer;
    }
    
    @Override
    public Game addGame(Game game) {
        final String sql = "INSERT INTO Game(Answer, Finished) VALUES(?,false);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            
            statement.setString(1, generateAnswer());
            statement.setBoolean(2, false);//all games initially are unfinished
            //move this to sql string at the start of the top?
            return statement;
        }, keyHolder);
        game.setGameID(keyHolder.getKey().intValue());
        return game;
    }
    
    
    @Override
    public List<Game> getAllGames() {
        final String sql = "SELECT * FROM Game;";
        List<Game> games = jdbcTemplate.query(sql, new GameDatabaseDao.GameMapper());
        
        //check finished per game
        for(int i=0;i<games.size();i++) {
            if(games.get(i).getFinished()==false) {
                games.get(i).setAnswer(null); //hide answer
            }
        }
        return games;
    }
    
    @Override
    public Game findByGameID(int gameID) {
        final String sql ="SELECT * FROM Game WHERE GameID = ?;";
        Game g = jdbcTemplate.queryForObject(sql, new GameDatabaseDao.GameMapper(), gameID);
        if(g.getFinished()==false) {
            g.setAnswer(null);
        }

        return g;
    }
    
    @Override
    public boolean update(Game game) { //should only update answer
        game.setFinished(true);
        final String sql = "UPDATE Game SET " + 
                "Finished=? "+
                "WHERE GameID=?;";
        return jdbcTemplate.update(sql,game.getFinished(),game.getGameID())>0;
        
    }
    
    public static final class GameMapper implements RowMapper<Game> {
        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game g = new Game();
            g.setGameID(rs.getInt("GameID"));
            g.setAnswer(rs.getString("Answer"));
            g.setFinished(rs.getBoolean("Finished"));
            return g;
        }
    }
    
    
}
