package edu.dosw.rideci.infraestructure.controller.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationStatsResponseDTO {
    private String name;
    private int count;
}
