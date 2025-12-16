package edu.dosw.rideci.adapter;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.infraestructure.StatsInfraService;
import edu.dosw.rideci.infraestructure.persistence.portSustainabilityRepositoryAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PortSustainabilityAdapterTest {

	@Mock
	private StatsInfraService statsInfraService;

	@InjectMocks
	private portSustainabilityRepositoryAdapter adapter;

	private UserStats userStats;

	private List<CommunityStats> communityStats;

	@BeforeEach
	void setup() {
		userStats = UserStats.builder()
				.userId(1L)
				.totalTrips(5)
				.totalDistance(42.0)
				.totalCO2Saved(10.5)
				.build();

		communityStats = List.of(CommunityStats.builder()
				.id("c1")
				.year(2024)
				.month(1)
				.totalSharedTrips(7)
				.totalCo2Saved(20.0)
				.totalActiveUsers(null)
				.build());
	}

	@Test
	void getUserStats_delegatesToInfraService() {
		long userId = 1L;
		when(statsInfraService.getUserStats(userId)).thenReturn(userStats);

		UserStats result = adapter.getUserStats(userId);

		assertSame(userStats, result);
		verify(statsInfraService).getUserStats(userId);
	}

	@Test
	void getCommunityStats_delegatesToInfraService() {
		int year = 2024;
		when(statsInfraService.getCommunityStatsByYear(year)).thenReturn(communityStats);

		List<CommunityStats> result = adapter.getCommunityStats(year);

		assertEquals(communityStats, result);
		verify(statsInfraService).getCommunityStatsByYear(year);
	}
}
