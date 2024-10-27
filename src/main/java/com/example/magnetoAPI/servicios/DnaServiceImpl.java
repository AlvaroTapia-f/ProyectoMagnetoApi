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

        //Verificamos si existe el ADN ingresado
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

        if (isMutant){
        return isMutant;
        } else {
            throw new IllegalArgumentException("");
        }

    }

    @Override
    public boolean isMutant(String[] dna) {
        int contador = 0;


        // Verificacion horizontal
        contador += verificarHorizontal(dna, contador);
        if (contador > 0){
            return true;
        }

        // Verificacion vertical
        contador += verificarVertical(dna, contador);
        if (contador > 0){
            return true;
        }

        //Obtener todas las diagonales posibles
        ArrayList<String> diagonales = getDiagonales(dna);

        //Verificacion diagonal
        contador += verificarDiagonales(diagonales, contador);


        return contador > 0;
    }

    private ArrayList<String> getDiagonales(String[] dna){
        int longDna = dna.length;
        ArrayList<String> palabras = new ArrayList<>();

        //Agregar diagonales arriba-izquierda abajo-derecha
        for (int i = 0 ; i < longDna  ; i++){
            StringBuffer strDiagonal1 = new StringBuffer(longDna);
            StringBuffer strDiagonal2 = new StringBuffer(longDna);
            for (int j = 0; j < longDna - i; j++){
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

        //Agregar diagonales arriba-derecha abajo-izquierda
        for (int i = 0; i < longDna; i++) {
            StringBuffer strDiagonal1 = new StringBuffer(longDna);
            StringBuffer strDiagonal2 = new StringBuffer(longDna);
            for (int j = 0; j < longDna - i; j++) {
                strDiagonal1.append(dna[j].charAt(longDna - j - 1 - i));
                if (i != 0){
                    strDiagonal2.append(dna[j + i].charAt(longDna - j - 1));

                }
            }
            if (strDiagonal1.length() >= 4){
                palabras.add(strDiagonal1.toString());
            }
            if (strDiagonal2.length() >= 4){
                palabras.add(strDiagonal2.toString());
            }
        }

        return palabras;

    }


    //Verificación horizontal
    private int verificarHorizontal(String[] dna, int contador){
        for (String palabra : dna) {
            contador += verificarSecuencias(palabra);
            if (contador > 0){
                return contador;
            }
        }
        return contador;
    }

    // Verificación vertical
    private int verificarVertical(String[] dna, int contador){

        int longDna = dna.length;

        for (int columna = 0 ; columna < longDna ; columna++){
            StringBuffer strColumnas = new StringBuffer(longDna);
            for (int fila = 0 ; fila < longDna ; fila++){
                strColumnas.append(dna[fila].charAt(columna));
            }
            contador += verificarSecuencias(strColumnas.toString());
            if (contador > 0){
                return contador;
            }
        }
        return contador;
    }

    private int verificarDiagonales(ArrayList<String> dna, int contador){
        for (String palabra : dna) {
            contador += verificarSecuencias(palabra);
            if (contador > 0){
                return contador;
            }
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

