package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.dto.DnaStatsDto;
import com.example.magnetoAPI.entidades.Dna;
import com.example.magnetoAPI.repositorios.BaseRepository;
import com.example.magnetoAPI.repositorios.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsServiceImpl extends BaseServiceImpl<Dna, Long> implements StatsService {

//  @Autowired
    private DnaRepository dnaRepository;

    @Autowired
    public StatsServiceImpl(BaseRepository<Dna, Long> baseRepository, DnaRepository dnaRepository){
        super(baseRepository);
        this.dnaRepository = dnaRepository;
    }

    @Override
    public DnaStatsDto getStats(){
        int countMutants = dnaRepository.countByMutant(true);
        int countHumans = dnaRepository.countByMutant(false);
        float ratio = 0;
        if (countHumans == 0){
            ratio = (float) countMutants;
        } else {
        ratio = (float) countMutants / countHumans;
        }

        return new DnaStatsDto(countMutants, countHumans, ratio);
    }

}
