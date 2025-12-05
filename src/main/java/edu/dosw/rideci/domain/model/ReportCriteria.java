package edu.dosw.rideci.domain.model;

import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.ReportPeriod;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCriteria {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReportPeriod period;
    private ReportFormat format;
}
