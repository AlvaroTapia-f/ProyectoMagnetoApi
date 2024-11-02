package controladores;

import com.example.magnetoAPI.MagnetoApiApplication;
import com.example.magnetoAPI.dto.DnaRequestDto;
import com.example.magnetoAPI.dto.DnaStatsDto;
import com.example.magnetoAPI.repositorios.DnaRepository;
import com.example.magnetoAPI.servicios.DnaServiceImpl;
import com.example.magnetoAPI.servicios.StatsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = MagnetoApiApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class StatsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DnaRepository dnaRepositoryMock;

    @MockBean
    StatsServiceImpl statsService;

    @Autowired
    private ObjectMapper objectMapper;

    DnaStatsDto dnaStatsDto;

    @BeforeAll
    public static void beforeAll(){
        System.out.println("INICIO DE TEST STATS_CONTROLLER");
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
    public void statsControllerTest_resultIsOk() throws Exception{

        dnaStatsDto = new DnaStatsDto(10, 5, 2.0f);

        given(statsService.getStats()).willReturn(dnaStatsDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/Stats")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
