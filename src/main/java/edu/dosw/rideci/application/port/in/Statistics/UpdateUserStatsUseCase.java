package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.domain.model.EmissionRecord;

public interface UpdateUserStatsUseCase {
    void updateFromTravel(EmissionRecord emissionRecord, TravelCompletedEvent event);
    //void updateFromGeo(GeoTravelCompletedEvent gEvent)
    //void updateFromPayment(PaymentCompletedEvent pEvent)
}
