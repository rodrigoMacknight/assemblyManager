package com.mk.assemblyManager.controller;

import com.mk.assemblyManager.dto.VoteRequest;
import com.mk.assemblyManager.service.AssemblyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

@RestController
@RequestMapping(value = "/assembly")
public class AssemblyController {

    private final AssemblyService vsService;

    public AssemblyController(AssemblyService vsService) {
        this.vsService = vsService;
    }

    @PatchMapping("/vote")
    public ResponseEntity<String> registerAssociateVote(@RequestBody VoteRequest vote) {

        var ret = vsService.register(vote);

        return ResponseEntity.ok(ret);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAssembly(@RequestHeader String assemblyName,
                                                 @RequestHeader @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss") LocalDateTime endSession) {
        String userResponse = null;
        HttpStatus httpStatus = null;

        if(vsService.createAssembly(assemblyName, endSession)!=null) {
            userResponse = "Assembleia iniciada";
            httpStatus = HttpStatus.OK;
        } else {
            userResponse = "Não foi possível criar a assembleia";
            httpStatus = HttpStatus.FORBIDDEN;
        }
        return new ResponseEntity<>(userResponse, httpStatus);

    }


    @GetMapping("/results")
    public ResponseEntity<String> getAssemblyResult(@RequestHeader Long assemblyId) {
        String response =  vsService.getAssemblyResult(assemblyId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
