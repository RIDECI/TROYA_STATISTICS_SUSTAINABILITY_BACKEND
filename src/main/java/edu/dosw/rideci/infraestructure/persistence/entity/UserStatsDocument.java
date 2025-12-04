package edu.dosw.rideci.infraestructure.persistence.entity;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Map;

@Document(collection = "UserStats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDocument {
    @Id
    private Long userId;
    private int totalTrips;
    private double totalDistance;
    private double totalCO2Saved;

    private Map<String, Integer> frequentDestinations;
    private Map<String, Integer> frequentDepartureTime;
    private Map<String, Integer> userTypeCount;

    private double totalMoneySpent;
}
