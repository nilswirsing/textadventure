package de.gds.commands;

import de.gds.AdventureModel;
import de.gds.Item;
import java.util.List;

public class InventoryCommand implements Command {
    @Override
    public String run(AdventureModel model, String[] args) {
        StringBuilder output = new StringBuilder("Items: ");
        List<Item> inv = model.getAdventurer().getInventory();
        if (inv.isEmpty()) {
            output.append("keine");
        }
        for (Item Item : inv) {
            if (inv.lastIndexOf(Item) + 1 == inv.size()) {
                output.append(Item.getName());
            } else {
                output.append(Item.getName() + ", ");
            }
        }
        return output.toString();
    }
    @Override
    public String getName() {
        return "inventory";
    }
}