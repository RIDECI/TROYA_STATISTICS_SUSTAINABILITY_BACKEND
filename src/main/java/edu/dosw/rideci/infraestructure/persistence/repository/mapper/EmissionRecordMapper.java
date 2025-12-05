package edu.dosw.rideci.infraestructure.persistence.repository.mapper;

import edu.dosw.rideci.domain.model.EmissionRecord;
import edu.dosw.rideci.infraestructure.persistence.entity.EmissionRecordDocument;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmissionRecordMapper {
    EmissionRecordDocument  toDocument(EmissionRecordDocument emissionRecordDocument);

    List<EmissionRecordDocument> toDocumentList(List<EmissionRecordDocument> emissionRecordDocuments);


    EmissionRecord toDomain(EmissionRecordDocument emissionRecordDocument);

    List<EmissionRecord> toDomainList(List<EmissionRecordDocument> emissionRecordDocuments);
}
