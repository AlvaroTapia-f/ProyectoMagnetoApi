package com.example.magnetoAPI.controladores;

import com.example.magnetoAPI.entidades.Dna;
import com.example.magnetoAPI.servicios.StatsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/Stats")
public class StatsController extends BaseControllerImpl<Dna, StatsServiceImpl>{

    @GetMapping("")
    public ResponseEntity<?> getStats(){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.getStats());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\":\" No existe ningún ADN en la base de datos \"}");
        }
    }

}
