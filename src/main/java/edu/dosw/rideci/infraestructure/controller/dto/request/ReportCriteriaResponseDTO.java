package edu.dosw.rideci.infraestructure.controller.dto.request;

import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.ReportPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportCriteriaResponseDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private ReportPeriod period;
    private ReportFormat format;
}
