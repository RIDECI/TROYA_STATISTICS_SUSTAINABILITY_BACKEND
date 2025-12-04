package edu.dosw.rideci.domain.services;

import edu.dosw.rideci.domain.model.Co2Calculator;
import edu.dosw.rideci.domain.model.Co2CalculatorDefault;
import edu.dosw.rideci.domain.model.enums.UserType;

public class Co2SavingsCalculatorService {

    private final Co2Calculator calculator = new Co2CalculatorDefault();

    public double calculateSavedCO2(
            double distanceKm,
            UserType userRole,
            int occupantsCount
    ) {

        // solo los pasajeros ahorran CO2
        if (userRole != UserType.PASSENGER) {
            return 0;
        }

        double baseline = calculator.calculateCO2(distanceKm);

        if (baseline <= 0) return 0;

        if (occupantsCount <= 1) {
            return baseline;
        }

        double perPerson = baseline / occupantsCount;
        return baseline - perPerson;
    }
}

