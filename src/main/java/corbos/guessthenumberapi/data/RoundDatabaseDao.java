/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.guessthenumberapi.data;

import corbos.guessthenumberapi.data.GameDatabaseDao;
import corbos.guessthenumberapi.data.GameDatabaseDao.GameMapper;
import corbos.guessthenumberapi.models.Game;
import corbos.guessthenumberapi.models.Round;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author rachellemagaram
 */
@Repository
public class RoundDatabaseDao implements RoundDao {
    
    private final JdbcTemplate jdbcTemplate;
    
    @Autowired
    public RoundDatabaseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public Round addGuess(Round r) {
        final String sql1 = "INSERT INTO Round(`Time`,Guess,GuessResults,GameID) VALUES(?,?,?,?);";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp t = Timestamp.from(Instant.now());
        String guessResults = calculateGuessResults(r);
        
        jdbcTemplate.update((Connection conn) -> {
            PreparedStatement statement = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            
            statement.setTimestamp(1, t);
            statement.setString(2, r.getGuess());
            statement.setString(3,guessResults);
            statement.setInt(4, r.getGameID());
            return statement;
        }, keyHolder);
        
        r.setRoundID(keyHolder.getKey().intValue());
        r.setTimestamp(t.toString());
        r.setGuessResults(guessResults);
        
        boolean finished = (guessResults.equals("e:4:p:0"));
        if(finished) {
            final String sql2 = "UPDATE Game JOIN Round ON Round.GameID=Game.GameID SET Finished=1 WHERE Round.guessResults='e:4:p:0';";
            jdbcTemplate.update(sql2);
        }
        
        return r;
        
    }
    
    public String calculateGuessResults(Round r) {
        String sql = "SELECT * FROM Game WHERE GameID=?";
        Game g = jdbcTemplate.queryForObject(sql, new GameMapper(), r.getGameID());        
        String answer = g.getAnswer();
        int e = 0, p = 0;
        String guessResults = "";//final format: e:0:p:0
        for (int i=0;i<4;i++) {
            if(answer.charAt(i)==r.getGuess().charAt(i)) {
                e++;
            }
            else if(answer.contains(String.valueOf(r.getGuess().charAt(i)))) {
                p++;
            }
        }
        guessResults = "e:" + String.valueOf(e) + ":p:" + String.valueOf(p);
        return guessResults;
    }
    
    @Override 
    public List<Round> getAllRounds(int gameID) {
        final String sql = "SELECT * FROM Round WHERE Round.GameID=? ORDER BY `Time`;";
        return jdbcTemplate.query(sql, new RoundMapper(), gameID);
    }
    
    private static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round r = new Round();
            r.setRoundID(rs.getInt("RoundID"));
            r.setGuess(rs.getString("Guess"));
            r.setGuessResults(rs.getString("GuessResults"));
            r.setTimestamp(rs.getString("Time"));
            r.setGameID(rs.getInt("GameID"));
            return r;
        }
    }
    
}
