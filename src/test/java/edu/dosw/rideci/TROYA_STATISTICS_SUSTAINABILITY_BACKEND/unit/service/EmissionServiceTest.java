package edu.dosw.rideci.TROYA_STATISTICS_SUSTAINABILITY_BACKEND.unit.service;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.port.out.PortCommunityStatsRepository;
import edu.dosw.rideci.application.port.out.PortEmissionRecordRepository;
import edu.dosw.rideci.application.port.out.PortUserStatsRepository;
import edu.dosw.rideci.application.service.EmissionService;
import edu.dosw.rideci.domain.model.Co2CalculatorCar;
import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.enums.UserType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EmissionServiceTest {

    private final PortEmissionRecordRepository emissionRepo = mock(PortEmissionRecordRepository.class);
    private final PortUserStatsRepository userStatsRepo = mock(PortUserStatsRepository.class);
    private final PortCommunityStatsRepository communityRepo = mock(PortCommunityStatsRepository.class);

    private final Co2CalculatorCar calculator = new Co2CalculatorCar();

    private final EmissionService service =
            new EmissionService(emissionRepo, userStatsRepo, communityRepo);

    @Test
    void generateEmissionRecord_shouldCalculateAndSaveCo2Record() {
        TravelCompletedEvent event = new TravelCompletedEvent();
        event.setDistanceKm(10.0);
        event.setPassengerList(Collections.emptyList());
        event.setDriverId(1L);

        double expectedCO2 = calculator.calculateCO2(10.0);

        EmissionRecord savedRecord = EmissionRecord.builder()
                .eId(123L)
                .userId(5L)
                .date(LocalDate.now())
                .distance(10.0)
                .totalCO2Saved(expectedCO2)
                .userRol(UserType.PASSENGER)
                .build();

        when(emissionRepo.save(any())).thenReturn(savedRecord);

        EmissionRecord result = service.generateEmissionRecord(5L, event);

        assertEquals(5L, result.getUserId());
        assertEquals(10.0, result.getDistance());
        assertEquals(expectedCO2, result.getTotalCO2Saved());
        assertEquals(UserType.PASSENGER, result.getUserRol());

        verify(emissionRepo).save(any());
    }

    @Test
    void updateFromTravel_shouldUpdateUserStats() {
        EmissionRecord record = EmissionRecord.builder()
                .userId(20L)
                .totalCO2Saved(3.5)
                .build();

        TravelCompletedEvent event = new TravelCompletedEvent();

        service.updateFromTravel(record, event);

        verify(userStatsRepo).addCo2Saved(20L, 3.5);
    }


    @Test
    void updateCommunityStats_shouldAddCalculatedCo2ToCommunity() {
        TravelCompletedEvent event = new TravelCompletedEvent();
        event.setDistanceKm(15.0);

        double expectedCO2 = calculator.calculateCO2(15.0);

        service.updateCommunityStats(0, event);

        verify(communityRepo).addCo2Saved(expectedCO2);
    }
}
