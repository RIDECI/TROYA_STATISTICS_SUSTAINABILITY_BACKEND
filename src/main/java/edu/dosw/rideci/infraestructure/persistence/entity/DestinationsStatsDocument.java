package edu.dosw.rideci.infraestructure.persistence.entity;



import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Document(collection = "DestinationStats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationsStatsDocument {
    @Id
    private String name;
    private int count;
}
