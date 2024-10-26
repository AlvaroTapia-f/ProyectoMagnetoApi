package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.dto.DnaStatsDto;
import com.example.magnetoAPI.entidades.Dna;

public interface StatsService extends BaseService<Dna, Long>{

    DnaStatsDto getStats() throws Exception;

}