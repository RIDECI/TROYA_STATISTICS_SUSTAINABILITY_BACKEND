package edu.dosw.rideci.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.dosw.rideci.application.mapper.InitialCommunityStatsMapper;
import edu.dosw.rideci.application.mapper.InitialReportMapper;
import edu.dosw.rideci.application.mapper.InitialUserStatsMapper;
import edu.dosw.rideci.application.port.in.Sustainability.GetCommunitySustainabilityUseCase;
import edu.dosw.rideci.application.port.in.Sustainability.GetUserSustainabilityUseCase;
import edu.dosw.rideci.application.port.in.Statistics.GenerateReportUseCase;
import edu.dosw.rideci.application.port.in.Statistics.GetCommunityStatslUseCase;
import edu.dosw.rideci.application.port.in.Statistics.GetDestinationPanelUserCase;
import edu.dosw.rideci.application.port.in.Statistics.GetFilteredUserStatsUseCase;
import edu.dosw.rideci.application.port.in.Statistics.GetUserPanelUseCase;
import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.CommunitySustainability;
import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.UserSustainability;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.ReportPeriod;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import edu.dosw.rideci.domain.model.enums.UserType;
import edu.dosw.rideci.infraestructure.controller.StatisticsController;
import edu.dosw.rideci.infraestructure.controller.dto.request.ReportCriteriaRequestDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.CommunityStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.CommunitySustainabilityResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.UserStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.UserSustainabilityResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class StadisticsControllerTest {

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@Mock
	private GenerateReportUseCase generateReportUseCase;

	@Mock
	private GetDestinationPanelUserCase getDestinationPanelUserCase;

	@Mock
	private GetFilteredUserStatsUseCase getFilteredUserStatsUseCase;

	@Mock
	private GetCommunityStatslUseCase getCommunityStatslUseCase;

	@Mock
	private GetUserPanelUseCase getUserPanelUseCase;

	@Mock
	private GetCommunitySustainabilityUseCase getCommunitySustainabilityUseCase;

	@Mock
	private GetUserSustainabilityUseCase getUserSustainabilityUseCase;

	@Mock
	private InitialReportMapper initialReportMapper;

	@Mock
	private InitialCommunityStatsMapper initialCommunityStatsMapper;

	@Mock
	private InitialUserStatsMapper initialUserStatsMapper;

	@InjectMocks
	private StatisticsController statisticsController;

	@BeforeEach
	void setup() {
		objectMapper = new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
	}


    @Test
    void generateReport_shouldReturnCreatedWithFile() throws Exception {
	ReportCriteriaRequestDTO requestDTO = ReportCriteriaRequestDTO.builder()
		.userId(10L)
		.startDate(LocalDate.of(2024, 1, 1))
		.endDate(LocalDate.of(2024, 1, 31))
		.period(ReportPeriod.MONTHLY)
		.format(ReportFormat.PDF)
		.build();

	ReportCriteria mappedCriteria = ReportCriteria.builder()
		.userId(requestDTO.getUserId())
		.startDate(requestDTO.getStartDate())
		.endDate(requestDTO.getEndDate())
		.period(requestDTO.getPeriod())
		.format(requestDTO.getFormat())
		.build();

	byte[] reportContent = "pdf-content".getBytes(StandardCharsets.UTF_8);

	when(initialReportMapper.toDomain(requestDTO)).thenReturn(mappedCriteria);
	when(generateReportUseCase.generateReport(mappedCriteria, ReportFormat.PDF)).thenReturn(reportContent);

	mockMvc.perform(post("/statistics/report/{format}", ReportFormat.PDF)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(requestDTO)))
		.andExpect(status().isCreated())
		.andExpect(header().string(HttpHeaders.CONTENT_TYPE, ReportFormat.PDF.getContentType()))
		.andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_10_MONTHLY.pdf"))
		.andExpect(content().bytes(reportContent));
    }


    @Test
    void getCommunityStats_shouldReturnList() throws Exception {
	int year = 2024;

	List<CommunityStats> stats = List.of(CommunityStats.builder()
		.id("community-1")
		.year(year)
		.month(5)
		.totalCo2Saved(120.5)
		.totalSharedTrips(42)
		.totalActiveUsers(Map.of(UserType.PASSENGER, 15))
		.build());

	List<CommunityStatsResponseDTO> responseDTO = List.of(CommunityStatsResponseDTO.builder()
		.id("community-1")
		.totalCo2Saved(120.5)
		.totalSharedTrips(42)
		.totalActiveUsers(Map.of(UserType.PASSENGER, 15))
		.build());

	when(getCommunityStatslUseCase.getCommunityStats(year)).thenReturn(stats);
	when(initialCommunityStatsMapper.toListResponseDTO(stats)).thenReturn(responseDTO);

	mockMvc.perform(get("/statistics/comumnity-statistics/{year}", year))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].id").value("community-1"))
		.andExpect(jsonPath("$[0].totalCo2Saved").value(120.5))
		.andExpect(jsonPath("$[0].totalSharedTrips").value(42))
		.andExpect(jsonPath("$[0].totalActiveUsers.PASSENGER").value(15));
    }


    @Test
    void getCommunitySustainability_shouldReturnDto() throws Exception {
	int year = 2023;

	CommunitySustainability sustainability = CommunitySustainability.builder()
		.year(year)
		.month(7)
		.totalTreesSaved(15.5)
		.averageCo2Saved(3.2)
		.build();

	CommunitySustainabilityResponseDTO responseDTO = CommunitySustainabilityResponseDTO.builder()
		.year(year)
		.month(7)
		.totalTreesSaved(15.5)
		.averageCo2Saved(3.2)
		.build();

	when(getCommunitySustainabilityUseCase.getCommunitySustainability(year)).thenReturn(sustainability);
	when(initialCommunityStatsMapper.toResponseSustainabilityDTO(sustainability)).thenReturn(responseDTO);

	mockMvc.perform(get("/statistics/community-sustainability/{year}", year))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.year").value(year))
		.andExpect(jsonPath("$.month").value(7))
		.andExpect(jsonPath("$.totalTreesSaved").value(15.5))
		.andExpect(jsonPath("$.averageCo2Saved").value(3.2));
    }


    @Test
    void getUserStats_shouldReturnDto() throws Exception {
	long userId = 55L;

	UserStats domain = UserStats.builder()
		.userId(userId)
		.totalTrips(12)
		.totalDistance(145.5)
		.totalCO2Saved(23.4)
		.frequentDestinations(Map.of("Campus", 5))
		.frequentDepartureTime(Map.of("08:00", 3))
		.totalMoneySpent(42.0)
		.build();

	UserStatsResponseDTO dto = UserStatsResponseDTO.builder()
		.userId(userId)
		.totalTrips(12)
		.totalDistance(145.5)
		.totalCO2Saved(23.4)
		.frequentDestinations(Map.of("Campus", 5))
		.frequentDepartureTime(Map.of("08:00", 3))
		.totalMoneySpent(42.0)
		.build();

	when(getUserPanelUseCase.getUserStats(userId)).thenReturn(domain);
	when(initialUserStatsMapper.toResponseDTO(domain)).thenReturn(dto);

	mockMvc.perform(get("/statistics/user-statistics/{userId}", userId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.userId").value(userId))
		.andExpect(jsonPath("$.totalTrips").value(12))
		.andExpect(jsonPath("$.totalDistance").value(145.5))
		.andExpect(jsonPath("$.totalCO2Saved").value(23.4))
		.andExpect(jsonPath("$.frequentDestinations.Campus").value(5))
		.andExpect(jsonPath("$.totalMoneySpent").value(42.0));
    }


    @Test
    void getUserSustainability_shouldReturnDto() throws Exception {
	long userId = 77L;

	UserSustainability sustainability = UserSustainability.builder()
		.year(2022)
		.totalTreesSaved(9.5)
		.totalCo2Saved(30.0)
		.averageCo2Saved(2.5)
		.build();

	UserSustainabilityResponseDTO dto = UserSustainabilityResponseDTO.builder()
		.year(2022)
		.totalTreesSaved(9.5)
		.totalCo2Saved(30.0)
		.averageCo2Saved(2.5)
		.build();

	when(getUserSustainabilityUseCase.getUserSustainability(userId)).thenReturn(sustainability);
	when(initialUserStatsMapper.toResponseSustainabilityDTO(sustainability)).thenReturn(dto);

	mockMvc.perform(get("/statistics/user-sustainability/{userId}", userId))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.year").value(2022))
		.andExpect(jsonPath("$.totalTreesSaved").value(9.5))
		.andExpect(jsonPath("$.totalCo2Saved").value(30.0))
		.andExpect(jsonPath("$.averageCo2Saved").value(2.5));
    }


    @Test
    void getDetailPanel_shouldReturnFilteredStatsMap() throws Exception {
	long userId = 99L;

	Map<UserStatField, Object> filtered = Map.of(
		UserStatField.TOTAL_TRIPS, 8,
		UserStatField.TOTAL_DISTANCE, 120.75
	);

	when(getFilteredUserStatsUseCase.filterStats(eq(userId), anyList())).thenReturn(filtered);

	mockMvc.perform(get("/statistics/detail-panel/{userId}", userId)
			.param("filteredStats", UserStatField.TOTAL_TRIPS.name(), UserStatField.TOTAL_DISTANCE.name()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.TOTAL_TRIPS").value(8))
		.andExpect(jsonPath("$.TOTAL_DISTANCE").value(120.75));
    }
}
