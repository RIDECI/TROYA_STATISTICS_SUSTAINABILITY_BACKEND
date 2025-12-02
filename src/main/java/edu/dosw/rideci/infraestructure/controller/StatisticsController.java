package edu.dosw.rideci.infraestructure.controller;


import edu.dosw.rideci.application.port.in.Statistics.*;
import edu.dosw.rideci.infraestructure.controller.dto.request.ReportCriteriaResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.DestinationStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.PublicPanelResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.UserStatsResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final GenerateReportUseCase generateReportUseCase;

    private final GetCollectiveSavingsUseCase getCollectiveSavingsUseCase;

    private final GetDestinationPanelUserCase getDestinationPanelUserCase;

    private final GetParticipationUseCase  getParticipationUseCase;

    private final GetPublicPanelUseCase getPublicPanelUseCase;

    private final GetUserPanelUseCase getUserPanelUseCase;


    @GetMapping("/report")
    public ResponseEntity<Byte[]> generateReport(@RequestBody ReportCriteriaResponseDTO reportCriteriaDTO) {
        return  null;
    }


    @GetMapping("/public-panel")
    public ResponseEntity<PublicPanelResponseDTO> getPublicPanel() {
        return null;
    }

    @GetMapping("/destinations")
    public ResponseEntity<DestinationStatsResponseDTO> getDestinationPanel(@PathVariable String name) {
        return null;
    }

    @GetMapping("/user-panel")
    public ResponseEntity<UserStatsResponseDTO> getUserPanel(@PathVariable Long userId) {
        return null;
    }

    @GetMapping("/detail-panel")
    public ResponseEntity<PublicPanelResponseDTO> getDetailPanel(@PathVariable Long userId) {
        return null;
    }



}
