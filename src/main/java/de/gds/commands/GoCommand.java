package de.gds.commands;

import de.gds.AdventureModel;
import de.gds.Room;
import de.gds.Position;

public class GoCommand implements Command {
    @Override
    public String run(AdventureModel model, String[] args) {
        String directionInput;
        if (args.length == 1 && args[0].equalsIgnoreCase("go")) {
                return "Fehler: GO benötigt ein Richtungsargument, z.B. \"GO Norden\" oder \"GO s\"!";
            }
        if (args[0].equalsIgnoreCase("go")) {
            directionInput = args[1];
        } else {
            directionInput = args[0];
        }

        String direction = de.gds.TextAdventure.getSynonyms().get(directionInput.toLowerCase().trim());

        if (direction == null) {
            return directionInput + " ist keine gültige Richtung!\n";
        }

        Room currentRoom = model.getAdventurer().getRoom();
        if (Boolean.FALSE.equals(model.getAdventurer().getRoom().isExit(direction))) {
            return "Dort kannst du nicht hingehen!\n";
        }

        if (currentRoom.getName().equals("Tech Noir Chamber") && direction.equals("S") && !model.isTechNoirDoorUnlocked()) {
                return "Die Tür ist verschlossen! Du kannst sie nicht öffnen.\n";
            }
        

        if (currentRoom.getName().equals("Rusty Garage") && direction.equals("O")) {
            return "Du gehst nach Osten...\nHerzlichen Glückwunsch! Du hast das Ziel erreicht!\n";
        }

        Position currentPosition = currentRoom.getPosition();
        int newRow = currentPosition.getX();
        int newCol = currentPosition.getY();
        switch (direction) {
            case "N": newRow -= 1; break;
            case "O": newCol += 1; break;
            case "S": newRow += 1; break;
            case "W": newCol -= 1; break;
            default:
                return "Unbekannte Richtung!\n";
        }

        Room[][] rooms = model.getRooms();
        model.getAdventurer().setRoom(rooms[newRow][newCol]);
        return "Du gehst nach " + direction + ".\n";
    }

    @Override
    public String getName() {
        return "go";
    }
}
