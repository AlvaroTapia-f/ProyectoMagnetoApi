package com.example.magnetoAPI.repositorios;
import com.example.magnetoAPI.entidades.Mutant;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends BaseRepository<Mutant, Long> {
}