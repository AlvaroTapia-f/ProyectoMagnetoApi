package com.example.magnetoAPI.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;


@Entity
@Table(name = "mutante")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@SuperBuilder
@Audited
public class Dna extends Base {

    @Column(name = "dna")
    private String[] dna;

    @Column(name = "mutante")
    private boolean mutant;
}
