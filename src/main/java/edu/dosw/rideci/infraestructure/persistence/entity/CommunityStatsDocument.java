package edu.dosw.rideci.infraestructure.persistence.entity;


import edu.dosw.rideci.domain.model.enums.UserType;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Document(collection = "CommunityStats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatsDocument {
    @Id
    private String id;
    private double totalCo2Saved;
    private int totalSharedTrips;
    private Map<UserType, Integer> totalActiveUsers;
}
