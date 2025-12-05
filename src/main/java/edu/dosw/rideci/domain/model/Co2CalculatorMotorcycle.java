package edu.dosw.rideci.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Co2CalculatorMotorcycle implements Co2Calculator {

    private static final double FACTOR_MOTO_KG_PER_KM = 0.103; // 103 g/km

    @Override
    public double calculateCO2(double distance) {
        if (distance <= 0) return 0;
        return FACTOR_MOTO_KG_PER_KM * distance;
    }
}


