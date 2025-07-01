package de.gds.commands;

import de.gds.AdventureModel;
import de.gds.Item;

public class UseCommand implements Command {
    @Override
    public String run(AdventureModel model, String[] args) {
        if (args.length != 2) {
            return "Fehler: USE benötigt den Namen des Items.";
        }

        String itemName = args[1].trim();
        Item item = findItem(model, itemName);

        if (item == null) {
            return "Du hast \"" + itemName + "\" nicht im Inventar.";
        }

        return useItem(model, item);
    }

    private Item findItem(AdventureModel model, String itemName) {
        for (Item item : model.getAdventurer().getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }

        for (Item item : model.getAdventurer().getRoom().getItems()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }

        return null;
    }

    private String useItem(AdventureModel model, Item item) {
        String room = model.getAdventurer().getRoom().getName();
        String name = item.getName().toLowerCase();

        if (name.equals("schluessel")) {
            return useKey(model, item, room);
        } else if (name.equals("buch")) {
            return "Du schlägst das alte Buch auf. Auf den Seiten ist eine rätselhafte Schrift zu sehen...";
        } else if (name.equals("taucherbrille")) {
            return room.equals("Coral Lagoon")
                    ? "Du setzt die Brille auf und blickst unter Wasser. Du siehst Fische zwischen den Korallen schwimmen, aber nichts Wertvolles."
                    : "Du setzt die Taucherbrille auf. An Land bringt sie dir leider nicht so viel.";
        } else if (name.equals("teleskop")) {
            return "Du wirfst einen Blick durch das Teleskop. Zwischen zwei Sternbildern erkennst du Umrisse von... etwas seltsamen...";
        }

        return "Du kannst " + item.getName() + " hier nicht sinnvoll benutzen.";
    }

    private String useKey(AdventureModel model, Item item, String room) {
        if (!room.equals("Tech Noir Chamber")) {
            return "Hier gibt es kein Schlüsselloch, in das der Schlüssel passt.";
        }

        if (model.isTechNoirDoorUnlocked()) {
            return "Der Durchgang ist bereits offen.";
        }

        model.setTechNoirDoorUnlocked(true);
        model.getAdventurer().getInventory().remove(item);
        model.getAdventurer().getRoom().getItems().remove(item);
        return "Du steckst den Schlüssel in ein verborgenes Schloss an der Südwand. Ein lautes Klicken hallt durch den Raum. Der Weg ist frei.";
    }

    @Override
    public String getName() {
        return "use";
    }
}