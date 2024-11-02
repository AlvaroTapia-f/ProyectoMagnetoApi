package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.entidades.Base;
import com.example.magnetoAPI.repositorios.BaseRepository;
import java.io.Serializable;

public abstract class BaseServiceImpl<E extends Base, ID extends Serializable> implements BaseService<E, ID> {


    protected BaseRepository<E, ID> baseRepository;

    public BaseServiceImpl(BaseRepository<E, ID> baseRepository){
        this.baseRepository = baseRepository;
    }

}
