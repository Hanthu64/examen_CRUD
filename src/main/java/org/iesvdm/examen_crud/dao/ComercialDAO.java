package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Cliente;

import java.util.List;

public interface ComercialDAO {
    public List<Cliente> getAll();
    public void create(Cliente cliente);
}
