package com.backend.agendamentotarefas.business.converter;

import com.backend.agendamentotarefas.business.dto.TarefasDTO;
import com.backend.agendamentotarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dataCriacao", target = "dataCriacao")
    @Mapping(source = "dataEvento", target = "dataEvento")

    TarefasEntity ParaTarefasEntity(TarefasDTO dto);

    TarefasDTO ParaTarefasDTO(TarefasEntity entity);

    List<TarefasEntity> paraListaTarefasEntity(List<TarefasDTO> dtos);

    List<TarefasDTO> paraListaTarefasDTO(List<TarefasEntity> entities);
}
