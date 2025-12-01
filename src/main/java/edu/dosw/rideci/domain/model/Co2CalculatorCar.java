package edu.dosw.rideci.domain.model;

import lombok.*;

@Getter
@Setter
//@Builder
@NoArgsConstructor
//@AllArgsConstructor
public class Co2CalculatorCar implements Co2Calculator {
    @Override
    public double calculateCO2(double distance) {
        return 0; // lógica se implementará en dominio/services
    }
}
