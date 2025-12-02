package edu.dosw.rideci.application.service;


import edu.dosw.rideci.application.port.in.Sustainability.GetPublicSustainabilityPanelUseCase;
import edu.dosw.rideci.application.port.in.Sustainability.GetUserCo2ReductionUseCase;
import org.springframework.stereotype.Service;

@Service
public class SustainabilityService implements GetPublicSustainabilityPanelUseCase, GetUserCo2ReductionUseCase {
}
