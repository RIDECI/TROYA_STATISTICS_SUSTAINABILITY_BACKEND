package edu.dosw.rideci.infraestructure.persistence;

import edu.dosw.rideci.application.port.out.PortCommunityStatsRepository;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import edu.dosw.rideci.infraestructure.persistence.repository.Stats.CommunityStats.CommunityStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Year;

@Component
@RequiredArgsConstructor
public class PortCommunityStatsAdapter implements PortCommunityStatsRepository {

    private final CommunityStatsRepository communityStatsRepository;

    @Override
    public void addCo2Saved(double amountKg) {
        int year = Year.now().getValue();

        CommunityStatsDocument doc = communityStatsRepository
                .findFirstByYearOrderByMonthDesc(year);

        if (doc == null) {
            doc = new CommunityStatsDocument();
            doc.setYear(year);
            doc.setMonth(1);
            doc.setTotalCO2Saved(0.0);
        }

        doc.setTotalCO2Saved(doc.getTotalCO2Saved() + amountKg);

        communityStatsRepository.save(doc);
    }
}

