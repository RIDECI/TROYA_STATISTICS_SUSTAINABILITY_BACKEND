package edu.dosw.rideci.infraestructure.controller;


import edu.dosw.rideci.application.mapper.InitialCommunityStatsMapper;
import edu.dosw.rideci.application.mapper.InitialReportMapper;
import edu.dosw.rideci.application.mapper.InitialUserStatsMapper;
import edu.dosw.rideci.application.port.in.Statistics.*;
import edu.dosw.rideci.application.port.in.Sustainability.GetCommunitySustainabilityUseCase;
import edu.dosw.rideci.application.port.in.Sustainability.GetUserSustainabilityUseCase;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import edu.dosw.rideci.infraestructure.controller.dto.request.ReportCriteriaRequestDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final GenerateReportUseCase generateReportUseCase;

    private final GetDestinationPanelUserCase getDestinationPanelUserCase;

    private final GetFilteredUserStatsUseCase getFilteredUserStatsUseCase;

    private final GetCommunityStatslUseCase getCommunityStatslUseCase;

    private final GetUserPanelUseCase getUserPanelUseCase;

    private final GetCommunitySustainabilityUseCase getCommunitySustainabilityUseCase;

    private final GetUserSustainabilityUseCase getUserSustainabilityUseCase;

    private final InitialReportMapper initialReportMapper;

    private final InitialCommunityStatsMapper initialCommunityStatsMapper;

    private final InitialUserStatsMapper initialUserStatsMapper;



    @PostMapping("/report/{format}")
    public ResponseEntity<byte[]> generateReport(@RequestBody ReportCriteriaRequestDTO reportCriteriaDTO,
                                                 @PathVariable ReportFormat format) {
        ReportCriteria reportC = initialReportMapper.toDomain(reportCriteriaDTO);
        byte[] reportContent = generateReportUseCase.generateReport(reportC, format);

        String filename = format.generateFilename(reportC.getUserId(), reportC.getPeriod());

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.CONTENT_TYPE, format.getContentType())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .body(reportContent);
    }




    @GetMapping("/comumnity-statistics/{year}")
    public ResponseEntity<List<CommunityStatsResponseDTO>> getCommunityStats(@PathVariable int year) {
        List<CommunityStatsResponseDTO> responseDTO = initialCommunityStatsMapper.toListResponseDTO(getCommunityStatslUseCase.getCommunityStats(year));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/community-sustainability/{year}")
    public ResponseEntity<CommunitySustainabilityResponseDTO> getCommunitySustainability(@PathVariable int year) {
        CommunitySustainabilityResponseDTO responseDTO =  initialCommunityStatsMapper.toResponseSustainabilityDTO(getCommunitySustainabilityUseCase.getCommunitySustainability(year));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/user-statistics/{userId}")
    public ResponseEntity<UserStatsResponseDTO> getUserStats(@PathVariable Long userId) {
        UserStatsResponseDTO responseDTO = initialUserStatsMapper.toResponseDTO(getUserPanelUseCase.getUserStats(userId));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @GetMapping("/user-sustainability/{userId}")
    public ResponseEntity<UserSustainabilityResponseDTO> getUserSustainability(@PathVariable Long userId) {
        UserSustainabilityResponseDTO responseDTO = initialUserStatsMapper.toResponseSustainabilityDTO(getUserSustainabilityUseCase.getUserSustainability(userId));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @GetMapping("/detail-panel/{userId}")
    public ResponseEntity<Map<UserStatField, Object>> getDetailPanel(
            @PathVariable Long userId,
            @RequestParam List<UserStatField> filteredStats){
        Map<UserStatField,Object> response = getFilteredUserStatsUseCase.filterStats(userId, filteredStats);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
