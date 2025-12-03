package edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats;

import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface UserStatsRepository extends MongoRepository<UserStatsDocument, Long> {

}