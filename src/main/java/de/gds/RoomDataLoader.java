package de.gds;

import de.gds.repository.RoomRepository;
import de.gds.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoomDataLoader implements CommandLineRunner {
    private final RoomRepository roomRepository;
    private final ItemRepository itemRepository;

    public RoomDataLoader(RoomRepository roomRepository, ItemRepository itemRepository) {
        this.roomRepository = roomRepository;
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        AdventureModel model = new AdventureModel(3, 3);
        Room[][] rooms = model.getRooms();
        for (Room[] row : rooms) {
            for (Room room : row) {
                if (room != null) {
                    roomRepository.save(room);
                    if (room.getItems() != null) {
                        for (Item item : room.getItems()) {
                            item.setRoom(room);
                            itemRepository.save(item);
                        }
                    }
                }
            }
        }
    }
}
