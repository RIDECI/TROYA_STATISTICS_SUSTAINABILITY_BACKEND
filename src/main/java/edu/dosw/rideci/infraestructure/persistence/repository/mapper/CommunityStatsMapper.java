package edu.dosw.rideci.infraestructure.persistence.repository.mapper;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommunityStatsMapper {
    CommunityStatsDocument toDocument(CommunityStats communityStats);

    CommunityStats toDomain(CommunityStatsDocument communityStatsDocument);
}
