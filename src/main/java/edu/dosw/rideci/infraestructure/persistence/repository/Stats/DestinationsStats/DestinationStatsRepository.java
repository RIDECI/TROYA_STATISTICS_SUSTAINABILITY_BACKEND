package edu.dosw.rideci.infraestructure.persistence.repository.Stats.DestinationsStats;

import edu.dosw.rideci.infraestructure.persistence.entity.DestinationsStatsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface DestinationStatsRepository extends MongoRepository<DestinationsStatsDocument, String> {
        DestinationsStatsDocument findByName(String name);
}
