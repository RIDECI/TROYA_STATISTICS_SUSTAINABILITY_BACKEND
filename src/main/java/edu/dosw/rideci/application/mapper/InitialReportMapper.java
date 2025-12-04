package edu.dosw.rideci.application.mapper;


import edu.dosw.rideci.domain.model.ReportCriteria;
import edu.dosw.rideci.infraestructure.controller.dto.request.ReportCriteriaRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InitialReportMapper {
    ReportCriteria toDomain(ReportCriteriaRequestDTO reportCriteria);
}
