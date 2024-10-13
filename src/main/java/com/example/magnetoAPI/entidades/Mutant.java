package com.example.magnetoAPI.entidades;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;


@Entity
@Table(name = "mutante")
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Data
@Audited
public class Mutant extends Base {

    @Column(name = "dna")
    private String[] dna;
}
