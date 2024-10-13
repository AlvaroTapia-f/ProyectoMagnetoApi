package com.example.magnetoAPI.controladores;
import com.example.magnetoAPI.entidades.Base;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;

public interface BaseController<E extends Base, ID extends Serializable> extends Serializable {
}
