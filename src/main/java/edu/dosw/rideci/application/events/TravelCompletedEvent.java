package edu.dosw.rideci.application.events;

import java.time.LocalDateTime;
import java.util.Date;
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
public class TravelCompletedEvent {
    private long organizerId;
    private long driverId;
    private String travelType;
    private Date departureDateAndTime;
    private List<Long> passengerList;
}
