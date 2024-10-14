package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.entidades.Dna;

public interface DnaService extends BaseService<Dna, Long>{

    boolean analizarDna(String[] dna) throws Exception;
    boolean isMutant(String[] dna) throws Exception;

}