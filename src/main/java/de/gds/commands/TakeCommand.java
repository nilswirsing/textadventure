package de.gds.commands;

import de.gds.AdventureModel;
import de.gds.Item;
import java.util.List;

public class TakeCommand implements Command {
    @Override
    public String run(AdventureModel model, String[] args) {
        StringBuilder output = new StringBuilder();
        if (args.length != 2) {
            return "Fehler: TAKE benötigt ein Argument für den Item";
        }

        String itemToTake = args[1].trim();
        List<Item> roomItems = model.getAdventurer().getRoom().getItems();
        List<Item> inventory = model.getAdventurer().getInventory();
        boolean found = false;

        for (int i = 0; i < roomItems.size(); i++) {
            Item takeItem = roomItems.get(i);
            if (takeItem.getName().equalsIgnoreCase(itemToTake)) {
                found = true;
                if (Boolean.TRUE.equals(takeItem.getTakeable())) {
                    inventory.add(takeItem);
                    roomItems.remove(i);
                    output.append(takeItem.getName() + " wurde aufgehoben.");
                } else {
                    output.append(takeItem.getName() + " kann nicht aufgehoben werden.");
                }
                break;
            }
        }

        if (!found) {
            output.append("Kein solches Item im Raum gefunden.");
        }
        return output.toString();
    }
    @Override
    public String getName() {
        return "take";
    }
}