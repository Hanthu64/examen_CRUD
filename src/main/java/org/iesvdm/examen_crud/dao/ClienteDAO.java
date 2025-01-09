package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Cliente;
import org.iesvdm.examen_crud.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface ClienteDAO {
    public List<Cliente> getAll();
    public void create(Cliente cliente);
    public void update(Cliente cliente);
    public Optional<Cliente> find(int id);
}
