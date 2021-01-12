/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corbos.guessthenumberapi.models;

/**
 *
 * @author rachellemagaram
 */
public class Round {
    
    private int roundID;
    private int gameID;
    private String timestamp; //change to Timestamp class?
    private String guess;
    private String guessResults;

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }
    
    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getGuessResults() {
        return guessResults;
    }

    public void setGuessResults(String guessResults) {
        this.guessResults = guessResults;
    }
    
}
