package com.example.magnetoAPI.dto;

import lombok.*;

import java.io.Serializable;

//Dto que ingresa al post Request, para luego transferir el dna a la entidad.
@Data
@NoArgsConstructor
public class DnaRequestDto implements Serializable {

    private String[] dna;

}
