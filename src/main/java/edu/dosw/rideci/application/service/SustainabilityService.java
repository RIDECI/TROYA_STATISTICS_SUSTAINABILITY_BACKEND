package edu.dosw.rideci.application.service;


import edu.dosw.rideci.application.port.in.Sustainability.GetCommunitySustainabilityUseCase;
import edu.dosw.rideci.application.port.in.Sustainability.GetUserSustainabilityUseCase;
import edu.dosw.rideci.application.port.out.PortSustainabilityRepository;
import edu.dosw.rideci.domain.model.CommunitySustainability;
import edu.dosw.rideci.domain.model.UserSustainability;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SustainabilityService implements GetCommunitySustainabilityUseCase, GetUserSustainabilityUseCase {

    private final PortSustainabilityRepository portSustainabilityRepository;

    @Override
    public CommunitySustainability getCommunitySustainability(int year) {
        return null;
    }

    @Override
    public UserSustainability getUserSustainability(Long userId) {
        return null;
    }


}
