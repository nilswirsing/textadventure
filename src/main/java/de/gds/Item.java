package de.gds;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Item {
    @Id
    private String name;
    private String desc;
    private Boolean takeable;

    @ManyToOne
    private Room room;

    public Item() {
        
    }

    public Item(String name, String desc, Boolean takeable) {
        this.name = name;
        this.desc = desc;
        this.takeable = takeable;
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

    public Boolean getTakeable() {
        return takeable;
    }

    public void setTakeable(Boolean takeable) {
        this.takeable = takeable;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
