package de.gds.commands;
import de.gds.AdventureModel;

import java.util.HashSet;
import java.util.Map;

public class HelpCommand implements Command {
    private final Map<String, Command> commands;

    public HelpCommand(Map<String, Command> commands) {
        this.commands = commands;
    }

    @Override
    public String run(AdventureModel model, String[] args) {
        HashSet<String> duplicate = new HashSet<>();
        StringBuilder output = new StringBuilder("Verfügbare Befehle:\n");
        for (Command command : commands.values()) {
            if (!duplicate.contains(command.getName())) {
                duplicate.add(command.getName());
                output.append("- ").append(command.getName()).append("\n");
            }
        }
        return output.toString();
    }

    @Override
    public String getName() {
        return "help";
    }
}
