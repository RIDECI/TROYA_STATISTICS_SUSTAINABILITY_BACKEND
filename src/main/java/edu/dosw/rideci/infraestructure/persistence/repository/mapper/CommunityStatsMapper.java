package edu.dosw.rideci.infraestructure.persistence.repository.mapper;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.infraestructure.persistence.entity.CommunityStatsDocument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommunityStatsMapper {
    CommunityStatsDocument toDocument(CommunityStats communityStats);

    List<CommunityStatsDocument> toDocumentList(List<CommunityStats> communityStatsList);

    List<CommunityStats> toDomainList(List<CommunityStatsDocument> communityStatsDocumentList);

    CommunityStats toDomain(CommunityStatsDocument communityStatsDocument);
}
