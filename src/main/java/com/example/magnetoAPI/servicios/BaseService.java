package com.example.magnetoAPI.servicios;

import com.example.magnetoAPI.entidades.Base;
import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends Base, ID extends Serializable> extends Serializable{

    public List<E> findAll() throws Exception;

    public E findById(ID id) throws Exception;

    public E save(E entidad) throws Exception;

    public E update(ID id, E entidad) throws Exception;

    public boolean delete(ID id) throws Exception;
}
