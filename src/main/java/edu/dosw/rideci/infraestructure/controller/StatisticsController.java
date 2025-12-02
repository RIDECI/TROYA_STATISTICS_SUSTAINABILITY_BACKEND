package edu.dosw.rideci.infraestructure.controller;


import edu.dosw.rideci.application.mapper.InitialCommunityStatsMapper;
import edu.dosw.rideci.application.mapper.InitialReportMapper;
import edu.dosw.rideci.application.port.in.Statistics.*;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import edu.dosw.rideci.infraestructure.controller.dto.request.ReportCriteriaRequestDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.CommunityStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.PublicPanelResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.UserStatsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final GenerateReportUseCase generateReportUseCase;

    private final GetDestinationPanelUserCase getDestinationPanelUserCase;

    private final GetFilteredUserStatsUseCase getFilteredUserStatsUseCase;

    private final GetCommunityStatslUseCase getCommunityStatslUseCase;

    private final GetUserPanelUseCase getUserPanelUseCase;

    private final InitialReportMapper initialReportMapper;

    private final InitialCommunityStatsMapper initialCommunityStatsMapper;

    @PostMapping("/report")
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportCriteriaRequestDTO reportCriteriaDTO) {
        ReportCriteria reportC = initialReportMapper.toDomain(reportCriteriaDTO);
        byte[] reportContent  = generateReportUseCase.generateReport(reportC);
        return ResponseEntity.status(HttpStatus.CREATED).body(reportContent);
    }

    @PostMapping("/report/PDF")
    public ResponseEntity<byte[]> generatePDFReport(@RequestBody ReportCriteriaRequestDTO reportCriteriaDTO,
                                                    @RequestParam ReportFormat reportFormat) {
        ReportCriteria reportC = initialReportMapper.toDomain(reportCriteriaDTO);
        byte[] reportContent  = generateReportUseCase.generatePDFReport(reportC);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(reportContent);
    }

    @PostMapping("/report/EXCEL")
    public ResponseEntity<byte[]> generateEXELReport(@RequestBody ReportCriteriaRequestDTO reportCriteriaDTO,
                                                    @RequestParam ReportFormat reportFormat) {
        ReportCriteria reportC = initialReportMapper.toDomain(reportCriteriaDTO);
        byte[] reportContent = generateReportUseCase.generateEXELReport(reportC);
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx")
                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )
                .body(reportContent);
    }


    @GetMapping("/comumnity-statistics")
    public ResponseEntity<CommunityStatsResponseDTO> getCommunityStats() {
        CommunityStatsResponseDTO responseDTO = initialCommunityStatsMapper.toResponseDTO(getCommunityStatslUseCase.getCommunityStats());
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/public-panel")
    public ResponseEntity<PublicPanelResponseDTO> getPublicPanel() {
        return null;
    }

    @GetMapping("/user-panel/{userId}")
    public ResponseEntity<UserStatsResponseDTO> getUserPanel(@PathVariable Long userId) {
        return null;
    }

    @GetMapping("/detail-panel/{userId}")
    public ResponseEntity<PublicPanelResponseDTO> getDetailPanel(
            @PathVariable Long userId,
            @RequestParam List<UserStatField> filteredStats){

        return null;
    }

}
