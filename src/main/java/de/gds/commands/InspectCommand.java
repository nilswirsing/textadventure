package de.gds.commands;

import de.gds.AdventureModel;
import de.gds.Item;
import java.util.List;

public class InspectCommand implements Command {
    @Override
    public String run(AdventureModel model, String[] args) {
        StringBuilder output = new StringBuilder();

        switch (args.length) {
            case 1:
                inspectRoom(output, model);
                break;
            case 2:
                inspectItem(model, args[1].trim(), output);
                break;
            default:
                output.append("Fehler: INSPECT funktioniert nur mit keinem oder einem Argument!");
        }

        return output.toString();
    }

    private void inspectItem(AdventureModel model, String itemName, StringBuilder output) {
        if (describeItem(model.getAdventurer().getRoom().getItems(), itemName, output, "")) {
            return;
        }
        if (describeItem(model.getAdventurer().getInventory(), itemName, output, " (in deinem Inventar)")) {
            return;
        }
        output.append("Kein Item namens \"").append(itemName).append("\" gefunden.");
    }

    private boolean describeItem(List<Item> items, String itemName, StringBuilder output, String suffix) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                output.append(item.getName()).append(suffix).append(": ").append(item.getDesc());
                return true;
            }
        }
        return false;
    }

    private void inspectRoom(StringBuilder string, AdventureModel model) {
        List<Item> items = model.getAdventurer().getRoom().getItems();
        if (items != null && !items.isEmpty()) {
            string.append("Items im Raum: " + items.get(0).getName());
            for (int i = 1; i < items.size(); i++) {
                string.append(", ").append(items.get(i).getName());
            }
        } else {
            string.setLength(0);
            string.append("Items im Raum: keine");
        }
    }

    @Override
    public String getName() {
        return "inspect";
    }
}