package edu.dosw.rideci.domain.model;

import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    private ReportCriteria criteria;
    private List<EmissionRecord> records;

    private UserStats userStats;
    private CommunityStats communityStats;

    private List<DestinationStats> destinationStats;
}
