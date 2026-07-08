package com.matheus_eg.help_desk_api.service;

import com.matheus_eg.help_desk_api.database.model.ChamadoModel;
import com.matheus_eg.help_desk_api.database.model.PrioridadeEnum;
import com.matheus_eg.help_desk_api.database.model.SolicitanteModel;
import com.matheus_eg.help_desk_api.database.model.StatusEnum;
import com.matheus_eg.help_desk_api.dto.ChamadoDTO;
import com.matheus_eg.help_desk_api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChamadoService {

    private final static List<ChamadoModel> chamados = new ArrayList<>();

    static{
        chamados.add(ChamadoModel.builder()
                        .id(1)
                        .status(StatusEnum.ABERTO)
                        .titulo("Esqueci senha")
                        .prioridade(PrioridadeEnum.ALTA)
                        .solicitante(SolicitanteModel.builder()
                                .nome("Jamerson")
                                .email("jamersonCapa12@gmail.com")
                                .build())
                        .build());
        chamados.add(ChamadoModel.builder()
                .id(2)
                .status(StatusEnum.EM_ATENDIMENTO)
                .titulo("Minha impressora não funciona")
                .prioridade(PrioridadeEnum.BAIXA)
                .solicitante(SolicitanteModel.builder()
                        .nome("Cleiton")
                        .email("cleitongrau14@gmail.com")
                        .build())
                .build());
        chamados.add(ChamadoModel.builder()
                .id(3)
                .status(StatusEnum.EM_ATENDIMENTO)
                .titulo("Não consigo acessar meu e-mail")
                .prioridade(PrioridadeEnum.MEDIA)
                .solicitante(SolicitanteModel.builder()
                        .nome("Joelma")
                        .email("joelma_calips42@gmail.com")
                        .build())
                .build());
        chamados.add(ChamadoModel.builder()
                .id(4)
                .status(StatusEnum.FINALIZADO)
                .titulo("O sistema está apresentando erro")
                .prioridade(PrioridadeEnum.ALTA)
                .solicitante(SolicitanteModel.builder()
                        .nome("Dilma")
                        .email("roussefVai34@gmail.com")
                        .build())
                .build());
    }

    public List<ChamadoModel> findAll(){
        return new ArrayList<ChamadoModel>(chamados);
    }

    public ChamadoModel createdChamado(ChamadoDTO chamadoDTO) {

        Integer newId = chamados.stream()
                .mapToInt(ChamadoModel::getId)
                .max()
                .orElse(0) + 1;

        ChamadoModel newChamado = ChamadoModel.builder()
                .id(newId)
                .titulo(chamadoDTO.getTitulo())
                .status(chamadoDTO.getStatus())
                .prioridade(chamadoDTO.getPrioridade())
                .solicitante(chamadoDTO.getSolicitante())
                .build();

        chamados.add(newChamado);
        return newChamado;
    }

    public ChamadoModel updatePrioridadeChamado(Integer id, PrioridadeEnum prioridade) throws NotFoundException {

        ChamadoModel chamado = chamados.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Usuario com id = "+id+" não encontrado"));

        chamado.setPrioridade(prioridade);
        return chamado;
    }

    public ChamadoModel updateStatusChamado(Integer id, StatusEnum status) throws NotFoundException {

        ChamadoModel chamado = chamados.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Usuario com id = "+id+" não encontrado"));

        chamado.setStatus(status);
        return chamado;
    }

    public void deleteChamado(Integer id) {
        chamados.removeIf(c -> c.getId().equals(id));
    }
}
