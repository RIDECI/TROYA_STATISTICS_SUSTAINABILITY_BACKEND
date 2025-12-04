package edu.dosw.rideci.infraestructure.persistence.repository.mapper;

import edu.dosw.rideci.domain.model.DestinationStats;
import edu.dosw.rideci.infraestructure.persistence.entity.DestinationsStatsDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DestinationStatsMapper {
    DestinationsStatsDocument toDocument(DestinationStats destinationStats);

    DestinationStats toDomain(DestinationsStatsDocument destinationStatsDocument);
}
