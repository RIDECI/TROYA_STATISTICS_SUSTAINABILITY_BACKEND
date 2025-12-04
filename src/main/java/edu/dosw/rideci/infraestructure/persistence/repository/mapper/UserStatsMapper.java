package edu.dosw.rideci.infraestructure.persistence.repository.mapper;

import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.infraestructure.persistence.entity.UserStatsDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStatsMapper {
    UserStatsDocument toDocument(UserStats userStats);

    UserStats toDomain(UserStatsDocument userStatsDocument);


}
