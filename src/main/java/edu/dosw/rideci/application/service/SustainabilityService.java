package edu.dosw.rideci.application.service;


import edu.dosw.rideci.application.port.in.Sustainability.GetCommunitySustainabilityUseCase;
import edu.dosw.rideci.application.port.in.Sustainability.GetUserSustainabilityUseCase;
import edu.dosw.rideci.application.port.out.PortSustainabilityRepository;
import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.CommunitySustainability;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.UserSustainability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class SustainabilityService implements GetCommunitySustainabilityUseCase, GetUserSustainabilityUseCase {

    private final PortSustainabilityRepository portSustainabilityRepository;

    @Override
    public CommunitySustainability getCommunitySustainability(int year){
       List<CommunityStats> cStats =  portSustainabilityRepository.getCommunityStats(year);
        if (cStats == null || cStats.isEmpty()) {
            return null;
        }

        CommunityStats lastStat = cStats.get(cStats.size() - 1);

        double totalTreesSaved =  calculateEquivalentTrees(lastStat.getTotalCo2Saved());

        int totalUsers = 0;
        if (lastStat.getTotalActiveUsers() != null) {
            totalUsers = lastStat.getTotalActiveUsers()
                    .values()
                    .stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        double averageCo2Saved = totalUsers > 0
                ? calculateAverageCo2(lastStat.getTotalCo2Saved(), totalUsers)
                : 0;


        return CommunitySustainability.builder()
                .year(year)
                .month(lastStat.getMonth())
                .totalTreesSaved(totalTreesSaved)
                .averageCo2Saved(averageCo2Saved)
                .build();

    }

    @Override
    public UserSustainability getUserSustainability(Long userId) {
        UserStats  userStats = portSustainabilityRepository.getUserStats(userId);

        double totalTreesSaved = calculateEquivalentTrees(userStats.getTotalCO2Saved());

        double totalCo2Saved = userStats.getTotalCO2Saved();

        double averageCo2Saved = calculateAverageCo2(userStats.getTotalCO2Saved(), userStats.getTotalTrips());

        return UserSustainability.builder()
                .year(LocalDate.now().getYear())
                .totalTreesSaved(totalTreesSaved)
                .totalCo2Saved(totalCo2Saved)
                .averageCo2Saved(averageCo2Saved)
                .build();

    }



    private double calculateEquivalentTrees(double co2){
        double treesCapture = 1.75;
        return co2/1.75;
    }

    private double calculateAverageCo2(double co2, int trips){
        return (double) co2 /trips;
    }

    private double calculateAverageCo2Users(double co2, int users){
        return (double) co2 /users;
    }

}
