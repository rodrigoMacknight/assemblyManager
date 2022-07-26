package com.mk.assemblyManager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Associate {

    @Id
    private Long id;
    @ElementCollection
    private List<Long> assemblyIds;
    private String cpf;

}
