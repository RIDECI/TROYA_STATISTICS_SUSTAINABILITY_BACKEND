package edu.dosw.rideci.domain.model;

import edu.dosw.rideci.domain.model.enums.UserType;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmissionRecord {
    private Long eId;
    private Long userId;
    private LocalDate date;
    private double totalCO2Saved;
    private UserType userRol;
}
