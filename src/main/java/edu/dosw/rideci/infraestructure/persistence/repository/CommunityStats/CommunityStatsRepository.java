package edu.dosw.rideci.infraestructure.persistence.repository.CommunityStats;

import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityStatsRepository extends MongoRepository<CommunityStatsDocument, String> {

}