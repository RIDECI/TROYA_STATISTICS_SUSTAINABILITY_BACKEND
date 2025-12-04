package edu.dosw.rideci.domain.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSustainability {
    private int year;
    private double totalTreesSaved;
    private double totalCo2Saved;
    private double averageCo2Saved;
}
