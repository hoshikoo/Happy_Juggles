package happy_juggles.c4q.nyc.happy_juggles;

/**
 * JOSHELYN'S SPORT CARD
 *
 * Created by c4q-joshelynvivas on 7/1/15.
 */

public class Sports {
    public Sports(String country, int wins) {
        this.country = country;
        this.wins = wins;

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    private String country;
    private int wins;


    public Sports() {
    }

}