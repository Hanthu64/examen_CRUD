package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Pedido;

import java.util.List;
import java.util.Optional;

public interface PedidosDAO {
    public List<Pedido> getAll();
    public void create(Pedido pedido);
    public void update(Pedido pedido);
    public void delete(int id);
    public Optional<Pedido> find(int id);
}
