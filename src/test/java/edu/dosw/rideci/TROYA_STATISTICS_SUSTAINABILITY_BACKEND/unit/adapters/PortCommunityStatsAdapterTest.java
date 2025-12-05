package edu.dosw.rideci.TROYA_STATISTICS_SUSTAINABILITY_BACKEND.unit.adapters;

import edu.dosw.rideci.infraestructure.persistence.PortCommunityStatsAdapter;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats.CommunityStatsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortCommunityStatsAdapterTest {

    private final CommunityStatsRepository repo = mock(CommunityStatsRepository.class);
    private final PortCommunityStatsAdapter adapter = new PortCommunityStatsAdapter(repo);

    @Test
    void addCo2Saved_shouldUpdateExistingStats() {
        int currentYear = java.time.Year.now().getValue();

        CommunityStatsDocument existing = new CommunityStatsDocument();
        existing.setYear(currentYear);
        existing.setMonth(5);
        existing.setTotalCO2Saved(10.0);

        when(repo.findFirstByYearOrderByMonthDesc(currentYear)).thenReturn(existing);

        adapter.addCo2Saved(2.5);

        ArgumentCaptor<CommunityStatsDocument> captor =
                ArgumentCaptor.forClass(CommunityStatsDocument.class);

        verify(repo).save(captor.capture());
        CommunityStatsDocument saved = captor.getValue();

        assertEquals(currentYear, saved.getYear());
        assertEquals(5, saved.getMonth());
        assertEquals(12.5, saved.getTotalCO2Saved());  // 10 + 2.5
    }

    @Test
    void addCo2Saved_shouldCreateNewStatsIfNoRecordForYear() {
        int currentYear = java.time.Year.now().getValue();

        when(repo.findFirstByYearOrderByMonthDesc(currentYear)).thenReturn(null);

        adapter.addCo2Saved(3.0);

        ArgumentCaptor<CommunityStatsDocument> captor =
                ArgumentCaptor.forClass(CommunityStatsDocument.class);

        verify(repo).save(captor.capture());
        CommunityStatsDocument saved = captor.getValue();

        assertEquals(currentYear, saved.getYear());
        assertEquals(1, saved.getMonth());           // default creation rule
        assertEquals(3.0, saved.getTotalCO2Saved()); // 0 + 3
    }
}
