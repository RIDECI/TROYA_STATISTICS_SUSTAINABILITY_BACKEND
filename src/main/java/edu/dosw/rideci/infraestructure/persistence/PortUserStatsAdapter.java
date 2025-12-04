package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.port.out.PortUserStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.UserStats.UserStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PortUserStatsAdapter implements PortUserStatsRepository {

    private final UserStatsRepository userStatsRepository;

    @Override
    public void addCo2Saved(Long userId, double amountKg) {
        UserStatsDocument doc = userStatsRepository.findById(userId)
                .orElseGet(() -> {
                    UserStatsDocument d = new UserStatsDocument();
                    d.setUserId(userId);
                    d.setTotalCO2Saved(0.0);
                    d.setTotalTrips(0);
                    return d;
                });

        doc.setTotalCO2Saved(doc.getTotalCO2Saved() + amountKg);
        userStatsRepository.save(doc);
    }
}
