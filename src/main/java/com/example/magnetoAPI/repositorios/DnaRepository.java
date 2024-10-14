package com.example.magnetoAPI.repositorios;
import com.example.magnetoAPI.entidades.Dna;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRepository extends BaseRepository<Dna, Long> {
    Optional<Dna> findByDna(String[] dna);
}