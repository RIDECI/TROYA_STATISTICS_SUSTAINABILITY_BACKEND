package edu.dosw.rideci.infraestructure.controller.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySustainabilityResponseDTO {
    private int year;
    private int month;
    private double totalTreesSaved;
    private double averageCo2Saved;
}
