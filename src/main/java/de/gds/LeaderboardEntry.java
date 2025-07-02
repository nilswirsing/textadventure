package de.gds;

import javax.persistence.*;

@Entity
@Table(name = "leaderboard")
public class LeaderboardEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String playtime;

    public LeaderboardEntry() {

    }

    public LeaderboardEntry(String name, String playtime) {
        this.name = name;
        this.playtime = playtime;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }
}
