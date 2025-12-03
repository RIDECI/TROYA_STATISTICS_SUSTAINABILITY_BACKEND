package edu.dosw.rideci.application.events.Listener;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static edu.dosw.rideci.infraestructure.config.RabbitConfig.STATISTICS_TRAVEL_QUEUE;


@Component
@Slf4j
public class TravelCompletedListener {

    @RabbitListener(queues = STATISTICS_TRAVEL_QUEUE)
    public void handleTravelCompleted(TravelCompletedEvent event) {
        log.info("Llego un viaje completado");
        log.info("travelId: {}", event.getTravelId());
        log.info("driverId: {}", event.getDriverId());
        log.info("pasajeros: {}", event.getPassengerList());
        log.info("estado: {}", event.getState());





    }
}
