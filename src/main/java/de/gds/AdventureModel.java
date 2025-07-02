package de.gds;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.gds.commands.Command;
import de.gds.commands.GoCommand;
import de.gds.commands.DropCommand;
import de.gds.commands.HelpCommand;
import de.gds.commands.InspectCommand;
import de.gds.commands.InventoryCommand;
import de.gds.commands.NameCommand;
import de.gds.commands.UseCommand;
import de.gds.repository.RoomRepository;
import de.gds.commands.TakeCommand;

public class AdventureModel {

    private Room[][] rooms;
    private Adventurer adventurer;
    private boolean techNoirDoorUnlocked = false;
    private Map<String, Command> commands;
    private final RoomRepository roomRepository;

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public boolean isTechNoirDoorUnlocked() {
        return techNoirDoorUnlocked;
    }

    public void setTechNoirDoorUnlocked(boolean techNoirDoorUnlocked) {
        this.techNoirDoorUnlocked = techNoirDoorUnlocked;
    }

    public Room[][] getRooms() {
        return rooms;
    }

    public AdventureModel(int hoehe, int breite, RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.rooms = loadRooms(hoehe, breite);
        this.adventurer = new Adventurer(this.rooms[0][0], null);
        loadCommands();
    }

    public Room[][] loadRooms(int hoehe, int breite) {
        Room[][] map = new Room[hoehe][breite];
        List<Room> allRooms = roomRepository.findAll();
        for (Room room : allRooms) {
            Position pos = room.getPosition();
            if (pos != null && pos.getX() < hoehe && pos.getY() < breite) {
                map[pos.getX()][pos.getY()] = room;
            }
        }
        return map;
    }

    public String input(String input) {
        String[] args = input.trim().split("\\s+");
        if (args.length == 0 || args[0].isEmpty()) {
            return "Bitte gib einen Befehl ein.";
        }
        String befehl = args[0].toLowerCase();
        if (getAdventurer().getName() == null && !befehl.startsWith("name")) {
            return "Du musst deinen Namen mithilfe des NAME-Befehls festlegen, bevor du dies tun kannst.";
        }

        Command command = commands.get(befehl);
        if (command != null) {
            return command.run(this, args);
        } else {
            return "Unbekannter Befehl: " + befehl;
        }
    }

    private void loadCommands() {
        commands = new HashMap<>();
        GoCommand goCmd = new GoCommand();
        String[] goAlias = { "go", "n", "s", "o", "w", "norden", "sueden", "süden", "osten", "westen", "north", "south",
                "east", "west", "up", "down", "hoch", "runter", "rechts", "links", "left", "right", "e", "w" };
        for (String alias : goAlias) {
            commands.put(alias, goCmd);
        }
        commands.put("take", new TakeCommand());
        commands.put("drop", new DropCommand());
        InventoryCommand invCmd = new InventoryCommand();
        commands.put("inventory", invCmd);
        commands.put("inv", invCmd);
        commands.put("inventar", invCmd);
        commands.put("inspect", new InspectCommand());
        commands.put("use", new UseCommand());
        HelpCommand helpCmd = new HelpCommand(commands);
        commands.put("help", helpCmd);
        commands.put("hilfe", helpCmd);
        commands.put("h", helpCmd);
        commands.put("name", new NameCommand());
    }
}
