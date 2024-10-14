package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.entidades.Dna;
import com.example.magnetoAPI.repositorios.BaseRepository;
import com.example.magnetoAPI.repositorios.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DnaServiceImpl extends BaseServiceImpl<Dna, Long> implements DnaService {

    @Autowired
    private DnaRepository dnaRepository;

    public DnaServiceImpl(BaseRepository<Dna, Long> baseRepository){
        super(baseRepository);
    }

    @Override
    public boolean analizarDna(String[] dna){
        if (!isValidDNA(dna)) {
            throw new IllegalArgumentException("La matriz de ADN no es válida. Debe ser una matriz NxN.");
        }

        //Verificamos si existe en ADN ingresado
        Optional<Dna> existsDna = dnaRepository.findByDna(dna);
        if (existsDna.isPresent()){
            return existsDna.get().isMutant();
        }
        
        boolean isMutant = isMutant(dna);
        Dna dnaEntity = Dna.builder()
                .dna(dna)
                .mutant(isMutant)
                .build();
        dnaRepository.save(dnaEntity);

        return isMutant(dna);
    }

    @Override
    public boolean isMutant(String[] dna) {
        int longDna = dna.length;
        int contador = 0;

        // Verificacion horizontal
        contador = verificarHorizontal(dna, contador);

        // Verificación vertical
        contador = verificarVertical(dna, longDna, contador);

        // Diagonal check (top-left to bottom-right)
        for (int i = 0; i < longDna; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j + i].charAt(j));
            }
            contador += verificarSecuencias(diagonal.toString());

            diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j].charAt(j + i));
            }
            contador += verificarSecuencias(diagonal.toString());
        }

        // Diagonal check (top-right to bottom-left)
        for (int i = 0; i < longDna; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j + i].charAt(longDna - j - 1));
            }
            contador += verificarSecuencias(diagonal.toString());

            diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j].charAt(longDna - j - 1 - i));
            }
            contador += verificarSecuencias(diagonal.toString());
        }

        return contador >= 1;
    }

    //Verificación horizontal
    private int verificarHorizontal(String[] dna, int contador){
        for (String palabra : dna) {
            contador += verificarSecuencias(palabra);
        }
        return contador;
    }

    // Verificación vertical
    private int verificarVertical(String[] dna, int longDna, int contador){
        for (int col = 0; col < longDna; col++) {
            StringBuilder columna = new StringBuilder();
            for (String palabra : dna) {
                columna.append(palabra.charAt(col));
            }
            contador += verificarSecuencias(columna.toString());
        }
        return contador;
    }

    private int verificarSecuencias(String palabra) {
        int contadorSecuencia = 0;
        for (int i = 0; i < palabra.length() - 3; i += 2) {
            if (palabra.charAt(i) == palabra.charAt(i + 2)) {
                char actual = palabra.charAt(i);
                if((i != 0 &&  actual == palabra.charAt(i-1) && actual == palabra.charAt(i+1)) ||
                        (actual == palabra.charAt(i+1) && actual == palabra.charAt(i+3))){
                    contadorSecuencia++;
                }
            }
        }
        return contadorSecuencia;
    }

    private boolean isValidDNA(String[] dna) {
        if (dna == null || dna.length == 0) {
            return false;
        }

        int longDna = dna.length;
        for (String row : dna) {
            if (row.length() != longDna) {
                return false;
            }
            for (char c : row.toCharArray()) {
                if (c != 'A' && c != 'T' && c != 'C' && c != 'G') {
                    return false;
                }
            }
        }
        return true;
    }
}
