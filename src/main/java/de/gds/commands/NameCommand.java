package de.gds.commands;

import de.gds.AdventureModel;

public class NameCommand implements Command {

    @Override
    public String run(AdventureModel model, String[] args) {
        if (args.length <= 1) {
            return "Fehler: NAME benötigt ein Argument für den Namen, z.B. \"NAME Nils\"";
        } else {
            String rawName = String.join(" ", java.util.List.of(args).subList(1, args.length));
            model.getAdventurer().setName(rawName.replaceAll("[^a-zA-Z0-9äÄöÖüÜß ]", ""));
            return "Dein Name wurde auf \"" + rawName.replaceAll("[^a-zA-Z0-9äÄöÖüÜß ]", "") + "\" festgelegt.";
        }
    }

    @Override
    public String getName() {
        return "name";
    }

}
