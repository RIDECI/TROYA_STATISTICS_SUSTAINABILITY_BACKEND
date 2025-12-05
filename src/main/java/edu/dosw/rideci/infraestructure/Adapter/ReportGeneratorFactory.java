package edu.dosw.rideci.infraestructure.Adapter;

import edu.dosw.rideci.application.port.out.ReportGenerator;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.exceptions.NotReportTypeFound;
import edu.dosw.rideci.infraestructure.Adapter.ReportAdapter.ExcelReportGenerator;
import edu.dosw.rideci.infraestructure.Adapter.ReportAdapter.PdfReportGenerator;
import lombok.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportGeneratorFactory {

    private final ExcelReportGenerator excel;
    private final PdfReportGenerator pdf;

    public ReportGenerator getGenerator(ReportFormat format) {
        return switch (format) {
            case EXCEL -> excel;
            case PDF -> pdf;
            default -> throw new NotReportTypeFound("Type not supported: " + format);
        };
    }
}
