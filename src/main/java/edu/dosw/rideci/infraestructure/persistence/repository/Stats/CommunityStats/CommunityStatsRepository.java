package edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats;

import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface CommunityStatsRepository extends MongoRepository<CommunityStatsDocument, String> {

}