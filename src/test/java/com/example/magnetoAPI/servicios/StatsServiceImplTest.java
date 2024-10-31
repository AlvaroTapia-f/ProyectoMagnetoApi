package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.dto.DnaStatsDto;
import com.example.magnetoAPI.repositorios.DnaRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

public class StatsServiceImplTest {

    @MockBean
    DnaRepository dnaRepositoryMock = Mockito.mock(DnaRepository.class);

    @InjectMocks
    protected StatsServiceImpl statsService;


    private DnaStatsDto dnaStatsDtoEsperado;

    @BeforeAll
    public static void beforeAll(){
        System.out.println("INICIO DE TEST STATS_SERVICE");
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo){
        System.out.println("INICIA: " + testInfo.getDisplayName());

        MockitoAnnotations.initMocks(this);
        when(dnaRepositoryMock.countByMutant(true)).thenReturn(5);
    }

    @AfterEach
    public void AfterEach(TestInfo testInfo){
        System.out.println("FINALIZA: " + testInfo.getDisplayName());
    }

    @AfterAll
    public static void afterAll(){
        System.out.println("TEST FINALIZADO");
    }

    @Test
    public void getStatsTestNormal(){

        dnaStatsDtoEsperado = new DnaStatsDto(5, 10, 0.5f);
        when(dnaRepositoryMock.countByMutant(false)).thenReturn(10);
        DnaStatsDto dnaStatsDto = statsService.getStats();
        Assertions.assertEquals(dnaStatsDtoEsperado, dnaStatsDto);

    }

    @Test
    public void getStatsTestDenominadorCero(){

        dnaStatsDtoEsperado = new DnaStatsDto(5, 0, 5.0f);
        when(dnaRepositoryMock.countByMutant(false)).thenReturn(0);
        DnaStatsDto dnaStatsDto = statsService.getStats();
        Assertions.assertEquals(dnaStatsDtoEsperado, dnaStatsDto);

    }


}
