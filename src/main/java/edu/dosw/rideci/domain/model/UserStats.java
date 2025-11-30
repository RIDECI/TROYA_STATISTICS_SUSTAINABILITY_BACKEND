package edu.dosw.rideci.domain.model;

import lombok.*;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStats {
    private Long userId;
    private int totalTrips;
    private double totalDistance;
    private double totalCO2Saved;

    private Map<String, Integer> frequentDestinations;
    private Map<String, Integer> frequentDepartureTime;

    private double totalMoneySpent;
}
