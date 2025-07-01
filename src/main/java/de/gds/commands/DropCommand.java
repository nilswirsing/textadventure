
package de.gds.commands;

import de.gds.AdventureModel;
import de.gds.Item;
import java.util.Iterator;
import java.util.List;

public class DropCommand implements Command {
    @Override
    public String run(AdventureModel model, String[] args) {
        if (args.length != 2) {
            return "Fehler: DROP benötigt ein Argument für das Item";
        }

        String itemToDrop = args[1].trim();
        List<Item> inventory = model.getAdventurer().getInventory();
        List<Item> roomItems = model.getAdventurer().getRoom().getItems();

        Iterator<Item> iter = inventory.iterator();
        while (iter.hasNext()) {
            Item dropItem = iter.next();
            if (dropItem.getName().equalsIgnoreCase(itemToDrop)) {
                iter.remove();
                roomItems.add(dropItem);
                return dropItem.getName() + " wurde abgelegt.";
            }
        }
        return "Du hast \"" + itemToDrop + "\" nicht im Inventar.";
    }
    @Override
    public String getName() {
        return "drop";
    }
}