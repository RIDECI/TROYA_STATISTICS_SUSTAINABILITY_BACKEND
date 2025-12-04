package edu.dosw.rideci.infraestructure.persistence.repository.mapper;

import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.infraestructure.persistence.entity.EmissionRecordDocument;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmissionRecordMapper {
    EmissionRecordDocument  toDocument(EmissionRecordDocument emissionRecordDocument);

    EmissionRecord toDomain(EmissionRecordDocument emissionRecordDocument);
}
