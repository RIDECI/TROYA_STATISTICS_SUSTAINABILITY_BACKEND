package edu.dosw.rideci.application.mapper;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.CommunitySustainability;
import edu.dosw.rideci.infraestructure.controller.dto.response.CommunityStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.CommunitySustainabilityResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.PublicPanelResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InitialCommunityStatsMapper {
    CommunityStatsResponseDTO toResponseDTO(CommunityStats communityStats);

    CommunitySustainabilityResponseDTO toResponseSustainabilityDTO(CommunitySustainability communitySustainability);
}
