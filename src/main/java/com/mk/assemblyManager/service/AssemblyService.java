package com.mk.assemblyManager.service;

import com.mk.assemblyManager.client.CpfValidatorClient;
import com.mk.assemblyManager.domain.Assembly;
import com.mk.assemblyManager.domain.Associate;
import com.mk.assemblyManager.dto.VoteRequest;
import com.mk.assemblyManager.repository.AssemblyRepository;
import com.mk.assemblyManager.repository.AssociateRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


import static com.mk.assemblyManager.dto.ApiResponses.*;

@Service
public class AssemblyService {


    public static final String PAUTA_NÃO_FINALIZADA = "Pauta não finalizada";
    private final AssemblyRepository assemblyRepository;
    private final AssociateRepository associateRepository;

    private final CpfValidatorClient cpfValidatorClient;

    public AssemblyService(AssemblyRepository vsRepository, AssociateRepository associateRepository,
                           CpfValidatorClient cpfValidatorClient) {
        this.assemblyRepository = vsRepository;
        this.associateRepository = associateRepository;
        this.cpfValidatorClient = cpfValidatorClient;
    }

    public Assembly createAssembly(String assemblyName, LocalDateTime endSession) {

        if (assemblyRepository.findByRulingName(assemblyName).size()==0) {
            //if running locally remove plus3Hours
            Assembly a = assemblyRepository.save(new Assembly(assemblyName, endSession.plusHours(3)));
            return a;
        }
        return null;
    }


    public String register(VoteRequest vote) {
        var associateEntity = associateRepository.findById(vote.getAssociateId());

        var assemblyEntity = assemblyRepository.findById(vote.getAssemblyId());

        if (associateEntity.isEmpty()) {
            return ASSOCIADO_NÃO_ENCONTRADO;
        }

        if (assemblyEntity.isEmpty()) {
            return PAUTA_NÃO_ENCONTRADA;
        }

        Associate associate = associateEntity.get();
        if (associate.getAssemblyIds().contains(vote.getAssemblyId())) {
            return ASSOCIADO_JÁ_VOTOU;
        }

        if (!cpfValidatorClient.clientCanVote(associate.getCpf())) {
            return CLIENT_CANT_VOTE;
        }

        Assembly assembly = assemblyEntity.get();

        if (assembly.getSessionEndTime().isBefore(LocalDateTime.now())) {
            return PAUTA_FINALIZADA;
        }

        doDemocracy(vote, assembly);

        if (associate.getAssemblyIds()==null) {
            associate.setAssemblyIds(List.of(assembly.getId()));
        } else {
            associate.getAssemblyIds().add(vote.getAssemblyId());
        }
        assemblyRepository.save(assembly);
        associateRepository.save(associate);

        return VOTO_REGISTRADO;

    }




    private void doDemocracy(VoteRequest vote, Assembly assembly) {
        if (vote.getVote()) {
            voteYes(assembly);
        } else {
            voteNo(assembly);
        }

    }

    private void voteNo(Assembly assembly) {
        if (assembly.getNo()==null) {
            assembly.setNo(1);
        } else {
            assembly.setNo(assembly.getNo()+1);
        }
    }

    private void voteYes(Assembly assembly) {
        if (assembly.getYes()==null) {
            assembly.setYes(1);
        } else {
            assembly.setYes(assembly.getYes()+1);
        }
    }

    public String getAssemblyResult(Long assemblyId) {
        var assemblyEntity = assemblyRepository.findById(assemblyId);

        if (assemblyEntity.isEmpty()) {
            return PAUTA_NÃO_ENCONTRADA;
        }
        var ass = assemblyEntity.get();
        if (ass.getSessionEndTime().isAfter(LocalDateTime.now())) {
            return PAUTA_NÃO_FINALIZADA;
        }

        if (ass.getNo() == null) {
            ass.setNo(0);
        }
        if (ass.getYes() == null) {
            ass.setYes(0);
        }
        StringBuilder sb = new StringBuilder();
        if (ass.getYes()>ass.getNo()) {
            sb.append("A pauta ");
            sb.append(ass.getRulingName());
            sb.append(" teve resultado positivo por ");
            sb.append(ass.getYes());
            sb.append(" votos a ");
            sb.append(ass.getNo());
        } else {
            sb.append("A pauta ");
            sb.append(ass.getRulingName());
            sb.append(" teve resultado negativo por ");
            sb.append(ass.getNo());
            sb.append(" votos a ");
            sb.append(ass.getYes());
        }
        return sb.toString();
    }
}
