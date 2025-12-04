package edu.dosw.rideci.application.mapper;

import edu.dosw.rideci.domain.model.CommunityStats;
import edu.dosw.rideci.domain.model.CommunitySustainability;
import edu.dosw.rideci.infraestructure.controller.dto.response.CommunityStatsResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.CommunitySustainabilityResponseDTO;
import edu.dosw.rideci.infraestructure.controller.dto.response.PublicPanelResponseDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InitialCommunityStatsMapper {
    CommunityStatsResponseDTO toResponseDTO(CommunityStats communityStats);

    List<CommunityStatsResponseDTO> toListResponseDTO(List<CommunityStats> communityStatsList);

    CommunitySustainabilityResponseDTO toResponseSustainabilityDTO(CommunitySustainability communitySustainability);
}
