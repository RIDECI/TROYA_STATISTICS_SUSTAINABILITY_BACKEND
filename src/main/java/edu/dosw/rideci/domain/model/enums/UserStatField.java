package edu.dosw.rideci.domain.model.enums;

import edu.dosw.rideci.domain.model.UserStats;

import java.util.function.Function;


public enum UserStatField {

    USER_ID(UserStats::getUserId),
    TOTAL_TRIPS(UserStats::getTotalTrips),
    TOTAL_DISTANCE(UserStats::getTotalDistance),
    TOTAL_CO2_SAVED(UserStats::getTotalCO2Saved),
    FREQUENT_DESTINATIONS(UserStats::getFrequentDestinations),
    FREQUENT_DEPARTURE_TIME(UserStats::getFrequentDepartureTime),
    TOTAL_MONEY_SPENT(UserStats::getTotalMoneySpent);

    private final Function<UserStats, Object> getter;

    UserStatField(Function<UserStats, Object> getter) {
        this.getter = getter;
    }

    public Object getValue(UserStats stats) {
        return getter.apply(stats);
    }
}
