package edu.dosw.rideci.infraestructure.controller.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSustainabilityResponseDTO {
    private int year;
    private double totalTreesSaved;
    private double totalCo2Saved;
    private double averageCo2Saved;
}
