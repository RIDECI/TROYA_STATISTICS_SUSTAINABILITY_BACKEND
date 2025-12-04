package edu.dosw.rideci.infraestructure.persistence.entity;

import edu.dosw.rideci.domain.model.enums.UserType;
import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Document(collection = "TravelEmissionRecord")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmissionRecordDocument {
    @Id
    private Long eId;
    private Long userId;
    private LocalDate date;
    private double TotalCO2Saved;
    private UserType userRol;
    private double distance;
}
