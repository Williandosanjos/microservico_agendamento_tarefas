package com.backend.agendamentotarefas.business;

import com.backend.agendamentotarefas.business.dto.TarefasDTO;
import com.backend.agendamentotarefas.business.mapper.TarefaUpdateConverter;
import com.backend.agendamentotarefas.business.mapper.TarefasConverter;
import com.backend.agendamentotarefas.infrastructure.entity.TarefasEntity;
import com.backend.agendamentotarefas.infrastructure.enums.StatusTarefas;
import com.backend.agendamentotarefas.infrastructure.exceptions.ResourceNotFoundException;
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
    private final TarefaUpdateConverter tarefasUpdateConverter;

    public TarefasDTO gravarTarefa(String token, TarefasDTO dto) {
        String email = jwtUtil.extrairEmailToken(token.substring(7));
        dto.setEmailUsuario(email);
        dto.setDataCriacao(LocalDateTime.now());
        dto.setStatusTarefas(StatusTarefas.PENDENTE);
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

    public void deletaTarefaPorId(String id) {
        tarefasRepository.deleteById(id);
    }

    public TarefasDTO alteraStatus(StatusTarefas statusTarefas, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Tarefa não encontrada " + id));
            entity.setStatusTarefas(statusTarefas);

            return tarefasConverter.ParaTarefasDTO(tarefasRepository.save(entity));

        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }

    public TarefasDTO updateTarefas(TarefasDTO dto, String id) {
        try {
            TarefasEntity entity = tarefasRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Tarefa não encontrada " + id));
            tarefasUpdateConverter.updateTarefas(dto, entity);
            return tarefasConverter.ParaTarefasDTO(tarefasRepository.save(entity) );
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao alterar status da tarefa " + e.getCause());
        }
    }

}
