package de.gds;

public class Entry {

    private final String name;
    private final String playtime;

    public Entry(String name, String playtime) {
        this.name = name;
        this.playtime = playtime;
    }

    public String getName() {
        return name;
    }

    public String getPlaytime() {
        return playtime;
    }
}
