package edu.dosw.rideci.application.port.in.Sustainability;

import edu.dosw.rideci.domain.model.UserSustainability;

public interface GetUserSustainabilityUseCase {
    UserSustainability getUserSustainability(Long userId);
}
