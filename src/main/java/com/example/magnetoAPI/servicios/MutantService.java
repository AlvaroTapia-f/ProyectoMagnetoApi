package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.entidades.Mutant;

public interface MutantService extends BaseService<Mutant, Long>{

    boolean isMutant(String[] dna) throws Exception;

}