package edu.dosw.rideci.infraestructure.persistence.repository.mapper;

import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.infraestructure.persistence.entity.DestinationsStatsDocument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DestinationStatsMapper {
    DestinationsStatsDocument toDocument(DestinationStats destinationStats);

    DestinationStats toDomain(DestinationsStatsDocument destinationStatsDocument);

    List<DestinationStats> toDomainList(List<DestinationsStatsDocument> destinationStatsList);
}
