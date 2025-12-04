package edu.dosw.rideci.domain.model;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunitySustainability {
    private int year;
    private int month;
    private double totalTreesSaved;
    private double averageCo2Saved;
}
