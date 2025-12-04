package edu.dosw.rideci.infraestructure.controller.dto.response;


import lombok.*;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilteredUserStatsResponseDTO {
    List<StatsResponseDTO> stats;
}
