package de.gds.commands;

import de.gds.AdventureModel;

public interface Command {
    String run(AdventureModel model, String[] args);
    String getName();
}
