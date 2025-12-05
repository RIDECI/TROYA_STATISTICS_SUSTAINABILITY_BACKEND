package edu.dosw.rideci.TROYA_STATISTICS_SUSTAINABILITY_BACKEND.unit.adapters;

import edu.dosw.rideci.infraestructure.persistence.PortUserStatsAdapter;
import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats.UserStatsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortUserStatsAdapterTest {

    private final UserStatsRepository repository = mock(UserStatsRepository.class);
    private final PortUserStatsAdapter adapter = new PortUserStatsAdapter(repository);

    @Test
    void addCo2Saved_shouldUpdateExistingDocument() {
        UserStatsDocument existing = new UserStatsDocument();
        existing.setUserId(20L);
        existing.setTotalCO2Saved(5.0);
        existing.setTotalTrips(3);

        when(repository.findById(20L)).thenReturn(Optional.of(existing));

        adapter.addCo2Saved(20L, 2.5);

        ArgumentCaptor<UserStatsDocument> captor = ArgumentCaptor.forClass(UserStatsDocument.class);
        verify(repository).save(captor.capture());

        UserStatsDocument saved = captor.getValue();
        assertEquals(20L, saved.getUserId());
        assertEquals(7.5, saved.getTotalCO2Saved());  // 5.0 + 2.5
        assertEquals(3, saved.getTotalTrips());
    }

    @Test
    void addCo2Saved_shouldCreateNewDocumentIfNotExists() {
        when(repository.findById(30L)).thenReturn(Optional.empty());

        adapter.addCo2Saved(30L, 4.0);

        ArgumentCaptor<UserStatsDocument> captor = ArgumentCaptor.forClass(UserStatsDocument.class);
        verify(repository).save(captor.capture());

        UserStatsDocument saved = captor.getValue();
        assertEquals(30L, saved.getUserId());
        assertEquals(4.0, saved.getTotalCO2Saved());
        assertEquals(0, saved.getTotalTrips());  // default
    }
}

