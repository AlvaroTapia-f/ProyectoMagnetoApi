package com.example.magnetoAPI.controladores;

import com.example.magnetoAPI.entidades.Base;
import com.example.magnetoAPI.servicios.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public abstract class BaseControllerImpl<E extends Base, S extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {

    @Autowired
    protected S servicio;

}
