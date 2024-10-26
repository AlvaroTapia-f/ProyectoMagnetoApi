package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.dto.DnaRequestDto;
import com.example.magnetoAPI.dto.DnaStatsDto;
import com.example.magnetoAPI.entidades.Dna;
import com.example.magnetoAPI.repositorios.BaseRepository;
import com.example.magnetoAPI.repositorios.DnaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

        //Verificamos si existe el ADN ingresado
        Optional<Dna> existsDna = dnaRepository.findByDna(dna);
        if (existsDna.isPresent()){
            return existsDna.get().isMutant();
        }

        //Obtener todas las secuencias posibles
//        ArrayList<String> palabras = getPalabras(dna);

        boolean isMutant = isMutant(dna);
        Dna dnaEntity = Dna.builder()
                .dna(dna)
                .mutant(isMutant)
                .build();
        dnaRepository.save(dnaEntity);

        if (isMutant){
        return isMutant;
        } else {
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public boolean isMutant(String[] dna) {
        int longDna = dna.length;
        int contador = 0;

        // Verificacion horizontal
        contador += verificarHorizontal(dna, contador);

        // Verificación vertical
        contador += verificarVertical(dna, longDna, contador);
        // Diagonal check (top-left to bottom-right)
        for (int i = 0; i < longDna; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j].charAt(i+j));
            }
            if (diagonal.length() >= 4){
            contador += verificarSecuencias(diagonal.toString());
            }

            diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j+i].charAt(j));
            }
            if (diagonal.length() >= 4){
            contador += verificarSecuencias(diagonal.toString());
            }

        }

        // Diagonal check (top-right to bottom-left)
        for (int i = 0; i < longDna; i++) {
            StringBuilder diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j + i].charAt(longDna - j - 1));
            }
            if (diagonal.length() >= 4){
            contador += verificarSecuencias(diagonal.toString());
            }

            diagonal = new StringBuilder();
            for (int j = 0; j < longDna - i; j++) {
                diagonal.append(dna[j].charAt(longDna - j - 1 - i));
            }
            if (diagonal.length() >= 4){
            contador += verificarSecuencias(diagonal.toString());
            }
        }
        return contador > 0;
    }

    //Probar rendimiento con el metodo de getPalabras vs metodo actual
    //Actualmente funcionando, eficiencia mejorada. Dto implementado

    /*
    private ArrayList<String> getPalabras(String[] dna){
        ArrayList<String> palabras = new ArrayList<>();

        //Agregar filas
        for (String p : dna){
            palabras.add(p);
        }

        //Agregar columnas
        for (int columna = 0 ; columna < dna.length ; columna++){
            StringBuffer strColumnas = new StringBuffer(dna.length);
            for (int fila = 0 ; fila < dna.length ; fila++){
                strColumnas.append(dna[fila].charAt(columna));
            }
            palabras.add(strColumnas.toString());
        }

        //Agregar diagonales
        for (int i = 0 ; i < dna.length  ; i++){
            StringBuffer strDiagonal1 = new StringBuffer(dna.length);
            StringBuffer strDiagonal2 = new StringBuffer(dna.length);
            for (int j = 0; j < dna.length - i; j++){
                strDiagonal1.append(dna[j].charAt(i+j));
                if (i != 0){
                    strDiagonal2.append(dna[j+i].charAt(j));
                }
            }
            if (strDiagonal1.length() >= 4){
                palabras.add(strDiagonal1.toString());
            }

            if (strDiagonal2.length() >= 4){
                palabras.add(strDiagonal2.toString());
            }
        }

        for (String word : palabras){
            System.out.println(word);
        }

        return palabras;

    }
*/

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
        for (int i = 0; i < palabra.length() - 2; i += 2) {
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
