package de.gds;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import de.gds.commands.TakeCommand;

public class AdventureModel {

    private Room[][] rooms;
    private Adventurer adventurer;
    private boolean techNoirDoorUnlocked = false;
    private Map<String, Command> commands;

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

    public AdventureModel(int hoehe, int breite) {
        this.rooms = loadRooms(hoehe, breite);
        this.adventurer = new Adventurer(this.rooms[0][0], null);
        loadCommands();
    }

    public Room[][] loadRooms(int hoehe, int breite) {
        Room[][] map = new Room[hoehe][breite];
        String delimiter = ";";

        Map<String, String> itemDescriptions = new HashMap<>();
        Map<String, Boolean> itemTakeability = new HashMap<>();

        readItems("src/main/resources/Items.csv", delimiter, itemDescriptions, itemTakeability);
        readRooms("src/main/resources/Raeume.csv", delimiter, breite, map, itemDescriptions, itemTakeability);

        return map;
    }

    private void readItems(String filePath, String delimiter,
            Map<String, String> descriptions,
            Map<String, Boolean> takeability) {
        try (BufferedReader itemReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = itemReader.readLine()) != null) {
                if (isSkippable(line))
                    continue;
                String[] parts = line.split(delimiter);
                if (parts.length >= 3) {
                    String name = parts[0].trim();
                    descriptions.put(name, parts[1].trim());
                    takeability.put(name, Boolean.valueOf(parts[2].trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readRooms(String filePath, String delimiter, int width, Room[][] map,
            Map<String, String> itemDescriptions,
            Map<String, Boolean> itemTakeability) {
        int row = 0;
        int col = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (isSkippable(line))
                    continue;
                Room raum = createRoomFromLine(line, delimiter, row, col, itemDescriptions, itemTakeability);
                map[row][col] = raum;
                col++;
                if (col >= width) {
                    col = 0;
                    row++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Room createRoomFromLine(String line, String delimiter, int row, int col,
            Map<String, String> itemDescriptions,
            Map<String, Boolean> itemTakeability) {
        String[] parts = line.split(delimiter);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Zeile in Raeume.csv unvollständig: " + Arrays.toString(parts));
        }
        String name = parts[0].trim();
        String desc = parts[1].trim();
        List<Item> items = createItems(parts[2], itemDescriptions, itemTakeability);
        Map<String, Boolean> exits = parseExits(parts[3]);
        return new Room(name, desc, items, exits, new Position(row, col));
    }

    private List<Item> createItems(String itemString,
            Map<String, String> itemDescriptions,
            Map<String, Boolean> itemTakeability) {
        List<Item> items = new ArrayList<>();
        for (String itemName : itemString.split(",")) {
            itemName = itemName.trim();
            if (!itemName.isEmpty() && itemDescriptions.containsKey(itemName)) {
                items.add(new Item(itemName, itemDescriptions.get(itemName), itemTakeability.get(itemName)));
            }
        }
        return items;
    }

    private Map<String, Boolean> parseExits(String exitsRaw) {
        Map<String, Boolean> exits = new HashMap<>();
        for (char c : exitsRaw.trim().toCharArray()) {
            exits.put(String.valueOf(c).toUpperCase(), true);
        }
        return exits;
    }

    private boolean isSkippable(String line) {
        return line.startsWith("#") || line.trim().isEmpty();
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
