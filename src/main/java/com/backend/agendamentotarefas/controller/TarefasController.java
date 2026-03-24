package com.backend.agendamentotarefas.controller;

import com.backend.agendamentotarefas.business.TarefasService;
import com.backend.agendamentotarefas.business.dto.TarefasDTO;
import com.backend.agendamentotarefas.infrastructure.enums.StatusTarefas;
import com.backend.agendamentotarefas.infrastructure.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tarefas")
@RequiredArgsConstructor
public class TarefasController {

    private final TarefasService tarefasService;

    @PostMapping
    public ResponseEntity<TarefasDTO> gravarTarefa(@RequestBody TarefasDTO dto,
                                                   @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(tarefasService.gravarTarefa(token, dto));
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<TarefasDTO>> buscaListarTarefasPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime dataFinal) {

        return ResponseEntity.ok(tarefasService.buscarTarefasAgendadasPorPeriodo(dataInicial, dataFinal));

    }

    @GetMapping
    public ResponseEntity<List<TarefasDTO>> buscaTarefaPorEmail(@RequestHeader("Authorization") String token) {

        return ResponseEntity.ok(tarefasService.buscaTarefaPorEmail(token));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletaTarefaPorId(@RequestParam("id") String id) {
        try {
            tarefasService.deletaTarefaPorId(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao deletar tarefa por id, id inexistente " + id, e.getCause());
        }

        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<TarefasDTO> alteraStatusNotificacao(@RequestParam("status")StatusTarefas statusTarefas,
                                                              @RequestParam("id") String id ){

        return ResponseEntity.ok(tarefasService.alteraStatus(statusTarefas, id));
    }

    @PutMapping
    public ResponseEntity<TarefasDTO> updateTarefas(@RequestBody TarefasDTO dto,
                                                    @RequestParam("id") String id) {

        return ResponseEntity.ok(tarefasService.updateTarefas(dto, id));
    }

}
