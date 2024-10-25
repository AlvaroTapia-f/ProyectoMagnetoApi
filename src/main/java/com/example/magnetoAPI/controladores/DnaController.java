package com.example.magnetoAPI.controladores;

import com.example.magnetoAPI.dto.DnaRequestDto;
import com.example.magnetoAPI.entidades.Dna;
import com.example.magnetoAPI.servicios.DnaServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/mutant")
public class DnaController extends BaseControllerImpl<Dna, DnaServiceImpl>{

    @PostMapping("")
    public ResponseEntity<?> isMutant(@RequestBody DnaRequestDto dnaRequest){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.analizarDna(dnaRequest.getDna()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\":\" El ADN ingresado no pertenece a un mutante \"}");
        }
    }

}
