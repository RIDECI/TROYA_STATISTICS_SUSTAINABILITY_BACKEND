package edu.dosw.rideci.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DestinationStats {
    private String name;
    private int count;
}
