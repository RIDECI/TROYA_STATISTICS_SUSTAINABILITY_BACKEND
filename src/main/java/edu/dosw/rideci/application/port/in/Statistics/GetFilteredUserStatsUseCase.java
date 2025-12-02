package edu.dosw.rideci.application.port.in.Statistics;

import edu.dosw.rideci.domain.model.UserStats;
import edu.dosw.rideci.domain.model.enums.UserStatField;

import java.util.*;

public interface GetFilteredUserStatsUseCase {
    Map<UserStatField, Object> filterStats(UserStats stats, List<UserStatField> fields);

}
