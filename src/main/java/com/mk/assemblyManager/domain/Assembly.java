package com.mk.assemblyManager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
public class Assembly {
    @Id @GeneratedValue
    Long id;

    Integer yes;

    Integer no;

    LocalDateTime sessionEndTime;

    String rulingName;

    public Assembly(String rulingName, LocalDateTime sessionEndTime) {
        this.rulingName = rulingName;
        this.sessionEndTime = sessionEndTime;
    }

    public Assembly() {}


}
