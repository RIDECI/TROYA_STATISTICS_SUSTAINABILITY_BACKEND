package edu.dosw.rideci.infraestructure.controller.dto.response;

import edu.dosw.rideci.domain.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStatsResponseDTO {
    private String id;
    private double totalCo2Saved;
    private int totalSharedTrips;
    private Map<UserType, Integer> totalActiveUsers;
}
