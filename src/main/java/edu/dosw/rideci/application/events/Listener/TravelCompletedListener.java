package edu.dosw.rideci.application.events.Listener;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.port.in.Statistics.GenerateEmissionRecordUseCase;
import edu.dosw.rideci.application.port.in.Statistics.UpdateCommunityStatsUseCase;
import edu.dosw.rideci.application.port.in.Statistics.UpdateUserStatsUseCase;
import edu.dosw.rideci.domain.model.EmissionRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static edu.dosw.rideci.infraestructure.config.RabbitConfig.STATISTICS_TRAVEL_QUEUE;


@Component
@Slf4j
@RequiredArgsConstructor
public class TravelCompletedListener {

    private final UpdateCommunityStatsUseCase updateCommunityStatsUseCase;
    private final UpdateUserStatsUseCase updateUserStatsUseCase;
    private final GenerateEmissionRecordUseCase  generateEmissionRecordUseCase;


    @RabbitListener(queues = STATISTICS_TRAVEL_QUEUE)
    public void handleTravelCompleted(TravelCompletedEvent event) {
        log.info("Llego un viaje completado");
        log.info("driverId: {}", event.getDriverId());
        log.info("pasajeros: {}", event.getPassengerList());


        int co2Saved = 0;
        updateCommunityStatsUseCase.updateCommunityStats(co2Saved,event);

        for(Long passenger : event.getPassengerList()){
            EmissionRecord e = generateEmissionRecordUseCase.generateEmissionRecord(passenger,event);
            updateUserStatsUseCase.updateFromTravel(e,event);
        }

    }
}
