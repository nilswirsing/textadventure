package de.gds;

import java.util.ArrayList;
import java.util.List;

public class Adventurer {
    private Room room;
    private ArrayList<Item> inventory;
    private String name;

    public Room getRoom() {
        return this.room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Adventurer(Room room, String name) {
        this.room = room;
        inventory = new ArrayList<>();
        this.name = name;
    }

}
