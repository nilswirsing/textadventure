package de.gds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    private String name;
    private String desc;
    
    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER)
    private List<Item> items;

    @ElementCollection(fetch = FetchType.EAGER)
    private Map<String, Boolean> exits = new HashMap<>();
    
    @Embedded
    private Position position;
    
    public Room(String name, String desc, List<Item> items, Map<String, Boolean> exits,
            Position position) {
        this.name = name;
        this.desc = desc;
        this.items = items;
        this.exits = exits;
        this.position = position;
    }

    public Room() {

    }

    public Map<String, Boolean> getExits() {
        return exits;
    }

    public void setExits(Map<String, Boolean> exits) {
        this.exits = exits;
    }
    
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Boolean isExit(String direction) {
        Boolean exists = exits.get(direction);
        return exists != null && exists;
    }
}

