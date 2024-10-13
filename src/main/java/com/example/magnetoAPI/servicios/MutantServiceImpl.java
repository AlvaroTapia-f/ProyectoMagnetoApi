package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.entidades.Mutant;
import com.example.magnetoAPI.repositorios.BaseRepository;
import com.example.magnetoAPI.repositorios.MutantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MutantServiceImpl extends BaseServiceImpl<Mutant, Long> implements MutantService {

    @Autowired
    private MutantRepository mutantRepository;

    public MutantServiceImpl(BaseRepository<Mutant, Long> baseRepository){
        super(baseRepository);
    }

    @Override
    public boolean isMutant(String[] dna) {
        if (!isValidDNA(dna)) {
            throw new IllegalArgumentException("La matriz de ADN no es válida. Debe ser una matriz NxN.");
        }

        int longDna = dna.length;
        int contador = 0;

        // Horizontal check
        for (String palabra : dna) {
            contador += contarPalabras(palabra);
        }

        // Vertical check
        for (int col = 0; col < longDna; col++) {
            StringBuilder columna = new StringBuilder();
            for (String palabra : dna) {
                columna.append(palabra.charAt(col));
            }
            contador += contarPalabras(columna.toString());
        }

        // Diagonal check (top-left to bottom-right)
        for (int i = 0; i < longDna; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j + i].charAt(j));
            }
            contador += contarPalabras(diagonal.toString());

            diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j].charAt(j + i));
            }
            contador += contarPalabras(diagonal.toString());
        }

        // Diagonal check (top-right to bottom-left)
        for (int i = 0; i < longDna; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j + i].charAt(longDna - j - 1));
            }
            contador += contarPalabras(diagonal.toString());

            diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j].charAt(longDna - j - 1 - i));
            }
            contador += contarPalabras(diagonal.toString());
        }

        return contador >= 1;
    }

    private int contarPalabras(String palabra) {
        int contador = 0;
        for (int i = 0; i < palabra.length() - 3; i++) {
            if (palabra.charAt(i) == palabra.charAt(i + 1) &&
                    palabra.charAt(i) == palabra.charAt(i + 2) &&
                    palabra.charAt(i) == palabra.charAt(i + 3)) {
                contador++;
            }
        }
        return contador;
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
