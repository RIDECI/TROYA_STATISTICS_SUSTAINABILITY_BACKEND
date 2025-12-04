package edu.dosw.rideci.infraestructure.persistence.entity;


import edu.dosw.rideci.domain.model.enums.UserType;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Document(collection = "CommunityStats")
@CompoundIndexes({
        @CompoundIndex(name = "unique_year_month_idx", def = "{'year': 1, 'month': 1}", unique = true)
})

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatsDocument {
    @Id
    private String id;
    private int year;
    private int  month;
    private int totalSharedTrips;
    private Map<UserType, Integer> totalActiveUsers;
    private double TotalCO2Saved;
}
