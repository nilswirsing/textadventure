package de.gds;

import java.util.HashMap;
import java.util.Map;

public class TextAdventure {

    private TextAdventure() {
        throw new IllegalStateException();
    }
    private static Map<String, String> synonym = new HashMap<>();

    public static Map<String, String> getSynonyms() {
        return synonym;
    }

    static {
        synonym.put("n", "N");
        synonym.put("norden", "N");
        synonym.put("north", "N");
        synonym.put("up", "N");
        synonym.put("hoch", "N");
        synonym.put("o", "O");
        synonym.put("osten", "O");
        synonym.put("east", "O");
        synonym.put("e", "O");
        synonym.put("right", "O");
        synonym.put("rechts", "O");
        synonym.put("s", "S");
        synonym.put("sueden", "S");
        synonym.put("süden", "S");
        synonym.put("south", "S");
        synonym.put("down", "S");
        synonym.put("runter", "S");
        synonym.put("w", "W");
        synonym.put("westen", "W");
        synonym.put("west", "W");
        synonym.put("left", "W");
        synonym.put("links", "W");
    }
}
