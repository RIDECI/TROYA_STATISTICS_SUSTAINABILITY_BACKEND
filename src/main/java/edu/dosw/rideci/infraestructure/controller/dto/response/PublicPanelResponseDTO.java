package edu.dosw.rideci.infraestructure.controller.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicPanelResponseDTO {
    private CommunityStatsResponseDTO communityStatsResponseDTO;
    private int numberOfSavedTrees;
    private int averageCo2Saved;
}
