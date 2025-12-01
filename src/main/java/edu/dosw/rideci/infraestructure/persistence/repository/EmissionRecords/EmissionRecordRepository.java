package edu.dosw.rideci.infraestructure.persistence.repository.EmissionRecords;

import edu.dosw.rideci.infraestructure.persistence.entity.EmissionRecordDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmissionRecordRepository extends MongoRepository<EmissionRecordDocument, String> {

    List<EmissionRecordDocument> findByUserId(Long userId);

    List<EmissionRecordDocument> findByDateBetween(LocalDate start, LocalDate end);

}
