package edu.dosw.rideci.infraestructure.persistence.repository.Sustainability.EmissionRecords;

import edu.dosw.rideci.infraestructure.persistence.entity.EmissionRecordDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface EmissionRecordRepository extends MongoRepository<EmissionRecordDocument, String> {

    List<EmissionRecordDocument> findByUserId(Long userId);

    List<EmissionRecordDocument> findByDateBetween(LocalDate start, LocalDate end);

}
