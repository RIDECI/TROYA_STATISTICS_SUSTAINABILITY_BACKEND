package edu.dosw.rideci.application.events;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TravelCreatedEvent {

    private Long travelId;

    private Long organizerId;

    private Long driverId;

    private int availableSlots;

    private String status;

    private String travelType;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private List<Long> passengersId;

    private String conditions;

    //Tipo Location
    private String origin;
    //Tipo Location
    private String destiny;

}