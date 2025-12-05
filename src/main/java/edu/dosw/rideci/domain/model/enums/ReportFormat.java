package edu.dosw.rideci.domain.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportFormat {
    PDF("application/pdf", ".pdf"),
    EXCEL("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx");

    private final String contentType;
    private final String fileExtension;

    public String generateFilename(Long userId, ReportPeriod period) {
        return String.format("reporte_%d_%s%s", userId, period, fileExtension);
    }
}
