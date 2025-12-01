package edu.dosw.rideci.domain.model;

import edu.dosw.rideci.domain.model.enums.UserType;
import lombok.*;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityStats {
    private String id;
    private double totalCo2Saved;
    private int totalSharedTrips;
    private Map<UserType, Integer> totalActiveUsers;
}
