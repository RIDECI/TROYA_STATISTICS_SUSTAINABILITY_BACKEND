package edu.dosw.rideci.adapter;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.port.out.ReportGenerator;
import edu.dosw.rideci.domain.model.*;
import edu.dosw.rideci.domain.model.enums.ReportFormat;
import edu.dosw.rideci.domain.model.enums.ReportPeriod;
import edu.dosw.rideci.domain.model.enums.UserStatField;
import edu.dosw.rideci.domain.model.enums.UserType;
import edu.dosw.rideci.infraestructure.Adapter.ReportGeneratorFactory;
import edu.dosw.rideci.infraestructure.StatsInfraService;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.DestinationsStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.portStatisticsRepositoryAdapter;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats.CommunityStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.DestinationsStats.DestinationStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats.UserStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.CommunityStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.DestinationStatsMapper;
import edu.dosw.rideci.infraestructure.persistence.repository.mapper.UserStatsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PortStadisticsAdapterTest {

    @Mock private CommunityStatsRepository communityStatsRepository;
    @Mock private UserStatsRepository userStatsRepository;
    @Mock private DestinationStatsRepository destinationStatsRepository;
    @Mock private CommunityStatsMapper communityStatsMapper;
    @Mock private UserStatsMapper userStatsMapper;
    @Mock private DestinationStatsMapper destinationStatsMapper;
    @Mock private StatsInfraService statsInfraService;
    @Mock private ReportGeneratorFactory reportGeneratorFactory;
    @Mock private ReportGenerator reportGenerator;

    @InjectMocks
    private portStatisticsRepositoryAdapter adapter;

    private CommunityStats communityStats;
    private UserStats userStats;
    private DestinationStats destinationStats;
    private ReportCriteria criteria;

    @BeforeEach
    void setup() {
	communityStats = CommunityStats.builder()
		.id("c1")
		.year(2024)
		.month(1)
		.totalSharedTrips(3)
		.totalCo2Saved(15.0)
		.build();

	userStats = UserStats.builder()
		.userId(5L)
		.totalTrips(2)
		.totalDistance(50.0)
		.totalCO2Saved(7.5)
		.frequentDepartureTime(Map.of("08:00", 1))
		.userTypeCount(Map.of(UserType.DRIVER.name(), 1))
		.build();

	destinationStats = DestinationStats.builder()
		.name("Campus")
		.count(4)
		.build();

	criteria = ReportCriteria.builder()
		.userId(5L)
		.period(ReportPeriod.MONTHLY)
		.format(ReportFormat.PDF)
		.build();
    }

    @Test
    void getGeneralPanel_returnsStatsFromInfra() {
	when(statsInfraService.getCommunityStatsByYear(2024)).thenReturn(List.of(communityStats));

	List<CommunityStats> result = adapter.getGeneralPanel(2024);

	assertEquals(1, result.size());
	assertSame(communityStats, result.get(0));
	verify(statsInfraService).getCommunityStatsByYear(2024);
    }

    @Test
    void getUserStats_returnsFromInfra() {
	when(statsInfraService.getUserStats(5L)).thenReturn(userStats);

	UserStats result = adapter.getUserStats(5L);

	assertSame(userStats, result);
	verify(statsInfraService).getUserStats(5L);
    }

    @Test
    void getFilterStats_mapsRequestedFields() {
	UserStatsDocument doc = UserStatsDocument.builder()
		.userId(5L)
		.totalTrips(3)
		.totalDistance(80.0)
		.totalCO2Saved(11.0)
		.build();
	when(userStatsRepository.findByUserId(5L)).thenReturn(doc);
	when(userStatsMapper.toDomain(doc)).thenReturn(userStats);

	Map<UserStatField, Object> result = adapter.getFilterStats(5L, List.of(UserStatField.TOTAL_TRIPS, UserStatField.TOTAL_CO2_SAVED));

	assertEquals(2, result.size());
	assertEquals(userStats.getTotalTrips(), result.get(UserStatField.TOTAL_TRIPS));
	assertEquals(userStats.getTotalCO2Saved(), result.get(UserStatField.TOTAL_CO2_SAVED));
	verify(userStatsRepository).findByUserId(5L);
	verify(userStatsMapper).toDomain(doc);
    }

    @Test
    void getDestinationStats_mapsDomain() {
	DestinationsStatsDocument doc = DestinationsStatsDocument.builder()
		.name("Campus")
		.count(4)
		.build();
	when(destinationStatsRepository.findByName("Campus")).thenReturn(doc);
	when(destinationStatsMapper.toDomain(doc)).thenReturn(destinationStats);

	DestinationStats result = adapter.getDestinationStats("Campus");

	assertSame(destinationStats, result);
	verify(destinationStatsRepository).findByName("Campus");
	verify(destinationStatsMapper).toDomain(doc);
    }

    @Test
    void generateReport_buildsAndDelegatesToGenerator() throws Exception {
	when(statsInfraService.getUserStats(criteria.getUserId())).thenReturn(userStats);
	when(statsInfraService.getDestinationStats(criteria.getUserId(), criteria.getPeriod())).thenReturn(List.of(destinationStats));
	when(statsInfraService.getEmissionRecords(criteria)).thenReturn(List.of(EmissionRecord.builder()
		.userId(criteria.getUserId())
		.date(LocalDate.now())
		.totalCO2Saved(1.2)
		.userRol(UserType.DRIVER)
		.build()));

	when(reportGeneratorFactory.getGenerator(ReportFormat.PDF)).thenReturn(reportGenerator);
	byte[] expected = "report-bytes".getBytes();
	when(reportGenerator.generate(any(Report.class))).thenReturn(expected);

	byte[] result = adapter.generateReport(criteria, ReportFormat.PDF);

	assertArrayEquals(expected, result);
	verify(reportGeneratorFactory).getGenerator(ReportFormat.PDF);
	verify(reportGenerator).generate(any(Report.class));
    }

    @Test
    void updateCommunityStats_incrementsTrips() {
	CommunityStatsDocument doc = CommunityStatsDocument.builder()
		.id("c1")
		.year(Year.now().getValue())
		.month(1)
		.totalSharedTrips(3)
		.build();
	when(communityStatsRepository.findFirstByYearOrderByMonthDesc(anyInt())).thenReturn(doc);

	adapter.updateCommunityStats(0, TravelCompletedEvent.builder().build());

	assertEquals(4, doc.getTotalSharedTrips());
	verify(communityStatsRepository).save(doc);
    }

    @Test
    void updateUserStats_updatesStatsAndSaves() {
	EmissionRecord emission = EmissionRecord.builder()
		.userId(7L)
		.totalCO2Saved(2.5)
		.userRol(UserType.DRIVER)
		.build();

	UserStatsDocument doc = UserStatsDocument.builder()
		.userId(7L)
		.totalTrips(1)
		.totalCO2Saved(5.0)
		.frequentDepartureTime(new java.util.HashMap<>(Map.of("08:00", 1)))
		.userTypeCount(new java.util.HashMap<>(Map.of(UserType.DRIVER.name(), 2)))
		.build();

	Date departure = new Date();
	TravelCompletedEvent event = TravelCompletedEvent.builder()
		.departureDateAndTime(departure)
		.build();

	when(userStatsRepository.findById(7L)).thenReturn(Optional.of(doc));

	adapter.updateUserStats(emission, event);

	ArgumentCaptor<UserStatsDocument> captor = ArgumentCaptor.forClass(UserStatsDocument.class);
	verify(userStatsRepository).save(captor.capture());
	UserStatsDocument saved = captor.getValue();

	assertEquals(2, saved.getTotalTrips());
	assertEquals(7.5, saved.getTotalCO2Saved());
	assertTrue(saved.getFrequentDepartureTime().values().stream().mapToInt(Integer::intValue).sum() >= 2);
	assertEquals(3, saved.getUserTypeCount().get(UserType.DRIVER.name()));
    }
}
