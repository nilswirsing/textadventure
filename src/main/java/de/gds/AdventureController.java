package de.gds;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import de.gds.repository.ItemRepository;
import de.gds.repository.LeaderboardRepository;
import de.gds.repository.RoomRepository;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/game")
@SessionAttributes("adventureModel")
public class AdventureController {
    private final RoomRepository roomRepository;
    private final LeaderboardRepository leaderboardRepository;

    @Autowired
    public AdventureController(RoomRepository roomRepository, ItemRepository itemRepository, LeaderboardRepository leaderboardRepository) {
        this.roomRepository = roomRepository;
        this.leaderboardRepository = leaderboardRepository;
    }

    @ModelAttribute("adventureModel")
    public AdventureModel adventureModel() {
        return new AdventureModel(3, 3, this.roomRepository);
    }

    private long startTime = 0;

    @GetMapping("")
    public String showGame(Model model) {
        de.gds.AdventureModel adventureModel = new de.gds.AdventureModel(3, 3, this.roomRepository);
        model.addAttribute("adventureModel", adventureModel);
        Room startRoom = adventureModel.getAdventurer().getRoom();
        Room currentRoom = adventureModel.getAdventurer().getRoom();
        String[] directions = { "N", "O", "S", "W" };
        StringBuilder exits = new StringBuilder();
        boolean first = true;
        for (String dir : directions) {
            Boolean exists = currentRoom.getExits().get(dir);
            if (exists != null && exists) {
                if (!first)
                    exits.append(", ");
                exits.append(dir);
                first = false;
            }
        }
        if (first) {
            exits.setLength(0);
            exits.append("keine");
        }
        startTime = 0;
        String output = "Name: " + startRoom.getName() + "\n" + startRoom.getDesc()
                + "\nAusgänge: " + exits
                + "\nWillkommen zum Text Adventure! Lege deinen Namen mit dem NAME-Befehl fest!";
        model.addAttribute("output", output);
        return "game";
    }

    @PostMapping("")
    public String processCommand(@RequestParam("command") String command,
            Model model,
            @ModelAttribute("adventureModel") AdventureModel adventureModel, SessionStatus status) {
        if (this.startTime == 0) {
            this.startTime = System.currentTimeMillis();
        }
        String output = adventureModel.input(command);
        Room currentRoom = adventureModel.getAdventurer().getRoom();
        String[] richtungen = { "N", "O", "S", "W" };
        StringBuilder exits = new StringBuilder();
        boolean first = true;
        for (String dir : richtungen) {
            Boolean exists = currentRoom.getExits().get(dir);
            if (exists != null && exists) {
                if (!first)
                    exits.append(", ");
                exits.append(dir);
                first = false;
            }
        }
        if (first) {
            exits.setLength(0);
            exits.append("keine");
        }
        if (output.contains("Du hast das Ziel erreicht!")) {
            long endTime = System.currentTimeMillis() - this.startTime;
            long s = (endTime / 1000) % 60;
            long m = (endTime / (1000 * 60)) % 60;
            long h = endTime / (1000 * 60 * 60);
            String time = String.format("%02d:%02d:%02d", h, m, s);
            String timeText = h + " Stunden, " + m + " Minuten, " + s + " Sekunden";
            String name = adventureModel.getAdventurer().getName();
            saveEntry(name, time);
            List<Entry> entries = loadEntries();
            List<Entry> top5 = new ArrayList<>();
            entries.sort(Comparator.comparing(Entry::getPlaytime));
            for (int i = 0; i < entries.size() && i < 5; i++) {
                top5.add(entries.get(i));
            }

            model.addAttribute("time", timeText);
            model.addAttribute("output", output);
            model.addAttribute("entries", top5);
            status.setComplete();
            return "ziel";
        }
        String outputText = "Name: " + currentRoom.getName() + "\n" +
                currentRoom.getDesc() + "\n" +
                "Ausgänge: " + exits + "\n" +
                output;
        model.addAttribute("output", outputText);
        return "game";
    }

    private void saveEntry(String name, String playtime) {
        leaderboardRepository.save(new de.gds.LeaderboardEntry(name, playtime));
    }

    private List<Entry> loadEntries() {
        List<de.gds.LeaderboardEntry> dbEntries = leaderboardRepository.findTop5();
        List<Entry> entries = new ArrayList<>();
        for (de.gds.LeaderboardEntry e : dbEntries) {
            entries.add(new Entry(e.getName(), e.getPlaytime()));
        }
        return entries;
    }
}
