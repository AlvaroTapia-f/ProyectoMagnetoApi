package com.example.magnetoAPI.controladores;

import com.example.magnetoAPI.entidades.Mutant;
import com.example.magnetoAPI.servicios.MutantServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "magnetoApi/v1/mutants")
public class MutantController extends BaseControllerImpl<Mutant, MutantServiceImpl>{

    @PostMapping("/mutant")
    public ResponseEntity<?> isMutant(@RequestBody String[] dna ){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(servicio.isMutant(dna));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\":\" El ADN ingresado no pertenece a un mutante \"}");
        }
    }

}
