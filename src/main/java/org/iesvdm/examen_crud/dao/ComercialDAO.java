package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Cliente;
import org.iesvdm.examen_crud.model.Comercial;

import java.util.List;

public interface ComercialDAO {
    public List<Comercial> getAll();
    public void create(Comercial comercial);
}
