package de.gds.repository;

import de.gds.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {
    List<LeaderboardEntry> findTop5ByOrderByPlaytimeAsc();

    default List<LeaderboardEntry> findTop5() {
        return findTop5ByOrderByPlaytimeAsc();
    }
}
