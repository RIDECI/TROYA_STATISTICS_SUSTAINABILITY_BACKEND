package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.port.out.PortEmissionRecordRepository;
import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.infraestructure.persistence.entity.EmissionRecordDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Sustainability.EmissionRecords.EmissionRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortEmissionRecordAdapter implements PortEmissionRecordRepository {

    private final EmissionRecordRepository repo;

    @Override
    public EmissionRecord save(EmissionRecord record) {

        EmissionRecordDocument doc = new EmissionRecordDocument();
        doc.setUserId(record.getUserId());
        doc.setDate(record.getDate());
        doc.setDistance(record.getDistance());
        doc.setTotalCO2Saved(record.getTotalCO2Saved());
        doc.setUserRol(record.getUserRol());

        var saved = repo.save(doc);

        return EmissionRecord.builder()
                .eId(saved.getEId())
                .userId(saved.getUserId())
                .date(saved.getDate())
                .distance(saved.getDistance())
                .totalCO2Saved(saved.getTotalCO2Saved())
                .userRol(saved.getUserRol())
                .build();
    }
}

