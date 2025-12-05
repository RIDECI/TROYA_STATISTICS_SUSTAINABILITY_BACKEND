package edu.dosw.rideci.infraestructure.controller.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponseDTO {
    private String name;
    private Object value;
}
