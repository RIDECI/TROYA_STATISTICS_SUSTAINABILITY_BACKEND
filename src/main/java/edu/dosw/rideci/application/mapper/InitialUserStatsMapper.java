package edu.dosw.rideci.application.mapper;

import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.UserSustainability;
import edu.dosw.rideci.infraestructure.controller.dto.response.FilteredUserStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.UserStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.UserSustainabilityResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InitialUserStatsMapper {
    UserStatsResponseDTO  toResponseDTO(UserStats userStats);

    UserSustainabilityResponseDTO toResponseSustainabilityDTO(UserSustainability userSustainability);
}
