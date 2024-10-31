package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.entidades.Dna;
import com.example.magnetoAPI.repositorios.BaseRepository;
import com.example.magnetoAPI.repositorios.DnaRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DnaServiceImplTest {

    @Autowired
    private DnaRepository dnaRepository;

    private final DnaServiceImpl dnaService = new DnaServiceImpl(dnaRepository);

    String[] matrizInvalida1 = {"ACGT",
                                "ACGT",
                                "ACGTT",
                                "ACGT"};

    String[] matrizInvalida2 = {"ACGT",
                                "ACGT",
                                "ACGT",
                                "ACGT",
                                "ACGT"};

    String[] matrizInvalida3 = {"ACGF",
                                "ACGT",
                                "ACGT",
                                "ACGT"};

    String[] matrizInvalida4 = {};

    String[] matrizInvalida5 = null;

    String[] matrizValida1 = {"ACGT",
                              "ACGT",
                              "ACGT",
                              "ACGT"};

    String[] matrizValida2 = {"GGGGA",
                              "ACGAA",
                              "AGGTC",
                              "GCGTA",
                              "ACGAA"};

    String[] matrizValida3 = {"ACGAA",
                              "GAGTA",
                              "ACATG",
                              "ACGAA",
                              "ACGAA"};

    String[] matrizValida4 = {"ACGAC",
                              "GCGCA",
                              "ACCTG",
                              "ACGAA",
                              "ACGTA"};


    @BeforeAll
    public static void beforeAll(){
        System.out.println("INICIO DE TEST DNA_SERVICE");
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo){
        System.out.println("INICIA: " + testInfo.getDisplayName());
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("TEST FINALIZADO");
    }

    @AfterEach
    public void AfterEach(TestInfo testInfo){
        System.out.println("FINALIZA: " + testInfo.getDisplayName());
    }


    @Test
    @Order(2)
    public void analizarDnaTest(){

        //Testear el resultado de una matriz válida, que es mutante por columnas
        Assertions.assertTrue(dnaService.isMutant(matrizValida1));

        //Testear el resultado de una matriz válida que es mutante por filas
        Assertions.assertTrue(dnaService.isMutant(matrizValida2));

        //Testear el resultado de una matriz que es mutante por diagonales
        Assertions.assertTrue(dnaService.isMutant(matrizValida3));
        Assertions.assertTrue(dnaService.isMutant(matrizValida4));


    }

    @Test
    @Order(1)
    public void isValidDNATest(){

        //Testear las matrices ingresadas inválidas
        Assertions.assertFalse(dnaService.isValidDNA(matrizInvalida1));
        Assertions.assertFalse(dnaService.isValidDNA(matrizInvalida2));
        Assertions.assertFalse(dnaService.isValidDNA(matrizInvalida3));
        Assertions.assertFalse(dnaService.isValidDNA(matrizInvalida4));
        Assertions.assertFalse(dnaService.isValidDNA(matrizInvalida5));


        //Testear las matrices válidas
        Assertions.assertTrue(dnaService.isValidDNA(matrizValida1));
        Assertions.assertTrue(dnaService.isValidDNA(matrizValida2));
        Assertions.assertTrue(dnaService.isValidDNA(matrizValida3));
        Assertions.assertTrue(dnaService.isValidDNA(matrizValida4));

    }

    public void isValidTest(){

    }

}
