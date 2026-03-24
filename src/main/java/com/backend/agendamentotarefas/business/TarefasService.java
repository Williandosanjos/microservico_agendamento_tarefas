package com.backend.agendamentotarefas.business;

import com.backend.agendamentotarefas.business.converter.TarefasConverter;
import com.backend.agendamentotarefas.business.dto.TarefasDTO;
import com.backend.agendamentotarefas.infrastructure.entity.TarefasEntity;
import com.backend.agendamentotarefas.infrastructure.enums.StatusTarefas;
import com.backend.agendamentotarefas.infrastructure.repository.TarefasRepository;
import com.backend.agendamentotarefas.infrastructure.security.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class TarefasService {

    private final TarefasRepository tarefasRepository;
    private final TarefasConverter tarefasConverter;
    private final JwtUtil jwtUtil;

    public TarefasDTO gravarTarefas(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatus(StatusTarefas.PENDENTE);
        TarefasEntity entity = tarefasConverter.ParaTarefasEntity(dto);

        return tarefasConverter.ParaTarefasDTO(tarefasRepository.save(entity));
    }

    public List<TarefasDTO> buscarTarefasAgendadasPorPeriodo(LocalDateTime dataInicial, LocalDateTime dataFinal) {

        return tarefasConverter.paraListaTarefasDTO(tarefasRepository.findByDataEventoBetween(dataInicial,dataFinal));
    }

    public List<TarefasDTO> buscaTarefaPorEmail(String token) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        List<TarefasEntity> listaTarefas = tarefasRepository.findByEmailUsuario(email);

        return tarefasConverter.paraListaTarefasDTO(listaTarefas);
    }
}
