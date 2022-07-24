package com.mk.assemblyManager.dto;

import lombok.Data;

@Data
public class VoteRequest {
    private Long associateId;
    private Long assemblyId;
    private Boolean vote;
}
