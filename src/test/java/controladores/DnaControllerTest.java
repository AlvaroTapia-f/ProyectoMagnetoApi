package controladores;

import com.example.magnetoAPI.MagnetoApiApplication;
import com.example.magnetoAPI.dto.DnaRequestDto;
import com.example.magnetoAPI.repositorios.DnaRepository;
import com.example.magnetoAPI.servicios.DnaServiceImpl;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(classes = MagnetoApiApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class DnaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DnaRepository dnaRepositoryMock;

    @MockBean
    DnaServiceImpl dnaService;

    @Autowired
    private ObjectMapper objectMapper;


    String[] dnaTrue = {"GGGGA", "ACGAA", "AGGTC", "GCGTA", "ACGAA"};
    String[] dnaFalse = {"GGGAA", "ACGAA", "AGTTC", "GCGTA", "ACGAA"};
    String[] invalidDna = {};

    DnaRequestDto dnaRequestDto;


    @BeforeAll
    public static void beforeAll(){
        System.out.println("INICIO DE TEST DNA_CONTROLLER");
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
    public void dnaControllerTest_resultIsOk() throws Exception{

        dnaRequestDto = new DnaRequestDto(dnaTrue);

        given(dnaService.analizarDna(dnaTrue)).willReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dnaRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void dnaControllerTest_resultForbidden() throws Exception{

        dnaRequestDto = new DnaRequestDto(dnaFalse);

        given(dnaService.analizarDna(dnaFalse)).willReturn(false);

        ResultActions resultActions = mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dnaRequestDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void dnaControllerTest_resultBadRequest() throws Exception{

        dnaRequestDto = new DnaRequestDto(invalidDna);

        given(dnaService.analizarDna(invalidDna)).willThrow(IllegalArgumentException.class);

        ResultActions resultActions = mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dnaRequestDto)));

        resultActions.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


}
