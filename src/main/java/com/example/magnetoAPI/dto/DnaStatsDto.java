package com.example.magnetoAPI.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class DnaStatsDto implements Serializable {

    private int count_mutant_dna;

    private int count_human_dna;

    private float ratio;

}