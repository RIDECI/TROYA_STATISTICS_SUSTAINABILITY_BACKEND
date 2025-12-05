package edu.dosw.rideci.TROYA_STATISTICS_SUSTAINABILITY_BACKEND.unit.adapters;

import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.enums.UserType;
import edu.dosw.rideci.infraestructure.persistence.PortEmissionRecordAdapter;
import edu.dosw.rideci.infraestructure.persistence.entity.EmissionRecordDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Sustainability.EmissionRecords.EmissionRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortEmissionRecordAdapterTest {

    private final EmissionRecordRepository repo = mock(EmissionRecordRepository.class);
    private final PortEmissionRecordAdapter adapter = new PortEmissionRecordAdapter(repo);

    @Test
    void save_shouldMapDomainToDocument_andReturnMappedBackDomain() {

        EmissionRecord record = EmissionRecord.builder()
                .userId(10L)
                .date(LocalDate.of(2025, 1, 1))
                .distance(12.5)
                .totalCO2Saved(3.7)
                .userRol(UserType.PASSENGER)
                .build();

        EmissionRecordDocument saved = new EmissionRecordDocument();
        saved.setEId(12345L);                       // Long id
        saved.setUserId(10L);
        saved.setDate(record.getDate());
        saved.setDistance(12.5);
        saved.setTotalCO2Saved(3.7);
        saved.setUserRol(UserType.PASSENGER);      // enum

        when(repo.save(any())).thenReturn(saved);

        EmissionRecord result = adapter.save(record);

        ArgumentCaptor<EmissionRecordDocument> docCaptor =
                ArgumentCaptor.forClass(EmissionRecordDocument.class);

        verify(repo).save(docCaptor.capture());

        EmissionRecordDocument doc = docCaptor.getValue();
        assertEquals(10L, doc.getUserId());
        assertEquals(record.getDate(), doc.getDate());
        assertEquals(12.5, doc.getDistance());
        assertEquals(3.7, doc.getTotalCO2Saved());
        assertEquals(UserType.PASSENGER, doc.getUserRol());

        assertEquals(12345L, result.getEId());
        assertEquals(10L, result.getUserId());
        assertEquals(3.7, result.getTotalCO2Saved());
        assertEquals(UserType.PASSENGER, result.getUserRol());
    }
}

