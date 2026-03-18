package com.backend.agendamentotarefas.business.converter;

import com.backend.agendamentotarefas.business.dto.TarefasDTO;
import com.backend.agendamentotarefas.infrastructure.entity.TarefasEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarefasConverter {

    TarefasEntity ParaTarefasEntity(TarefasDTO dto);

    TarefasDTO ParaTarefasDTO(TarefasEntity entity);
}
