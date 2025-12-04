package edu.dosw.rideci.application.service;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.port.in.Statistics.GenerateEmissionRecordUseCase;
import edu.dosw.rideci.application.port.in.Statistics.UpdateCommunityStatsUseCase;
import edu.dosw.rideci.application.port.in.Statistics.UpdateUserStatsUseCase;
import edu.dosw.rideci.application.port.out.PortCommunityStatsRepository;
import edu.dosw.rideci.application.port.out.PortEmissionRecordRepository;
import edu.dosw.rideci.application.port.out.PortUserStatsRepository;
import edu.dosw.rideci.domain.model.Co2Calculator;
import edu.dosw.rideci.domain.model.Co2CalculatorCar;
import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.domain.model.enums.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class EmissionService
        implements GenerateEmissionRecordUseCase, UpdateCommunityStatsUseCase, UpdateUserStatsUseCase {

    private final PortEmissionRecordRepository emissionRecordRepo;
    private final PortUserStatsRepository userStatsRepo;
    private final PortCommunityStatsRepository communityRepo;

    private final Co2Calculator co2Calculator = new Co2CalculatorCar();


    // 1 Crear EmissionRecord
    @Override
    public EmissionRecord generateEmissionRecord(long passengerId, TravelCompletedEvent event) {

        double distance = event.getDistanceKm();
        double avoidedCo2 = co2Calculator.calculateCO2(distance);

        EmissionRecord record = EmissionRecord.builder()
                .userId(passengerId)
                .date(LocalDate.now())
                .distance(distance)
                .totalCO2Saved(avoidedCo2)
                .userRol(UserType.PASSENGER)
                .build();

        return emissionRecordRepo.save(record);
    }

    // 2 Actualizar UserStats

    @Override
    public void updateFromTravel(EmissionRecord record, TravelCompletedEvent event) {
        userStatsRepo.addCo2Saved(record.getUserId(), record.getTotalCO2Saved());
    }


    // 3 Actualizar CommunityStats

    @Override
    public void updateCommunityStats(double co2Saved, TravelCompletedEvent event) {

        // recalcular CO2 aquí igual
        double distance = event.getDistanceKm();
        double avoidedCo2 = co2Calculator.calculateCO2(distance);

        // Se suma solo una vez por viaje
        communityRepo.addCo2Saved(avoidedCo2);
    }
}
