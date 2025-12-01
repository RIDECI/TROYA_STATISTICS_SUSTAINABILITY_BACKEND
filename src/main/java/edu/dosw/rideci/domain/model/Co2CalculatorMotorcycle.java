package edu.dosw.rideci.domain.model;

import lombok.*;

@Getter
@Setter
//@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class Co2CalculatorMotorcycle implements Co2Calculator {

    @Override
    public double calculateCO2(double distance) {
        return 0;
    }
}

