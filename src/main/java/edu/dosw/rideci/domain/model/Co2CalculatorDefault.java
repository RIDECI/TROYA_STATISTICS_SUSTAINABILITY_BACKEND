package edu.dosw.rideci.domain.model;

public class Co2CalculatorDefault implements Co2Calculator {

    // Valor promedio entre carro y moto
    private static final double CO2_PER_KM = 0.21; // kg por km

    @Override
    public double calculateCO2(double distanceKm) {
        return distanceKm * CO2_PER_KM;
    }
}
