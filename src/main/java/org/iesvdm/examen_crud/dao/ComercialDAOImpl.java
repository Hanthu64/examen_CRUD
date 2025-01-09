package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Cliente;
import org.iesvdm.examen_crud.model.Comercial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComercialDAOImpl extends AbstractDAOImpl implements ComercialDAO {
    @Override
    public void create(Comercial comercial){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();


            //1 alternativas comentadas:
            //Ver también, AbstractDAOImpl.executeInsert ...
            //Columna fabricante.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente.
            ps = conn.prepareStatement("INSERT INTO cliente (id, nombre, apellido1, apellido2, comision) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setInt(idx++, comercial.getId());
            ps.setString(idx++, comercial.getNombre());
            ps.setString(idx++, comercial.getApellido1());
            ps.setString(idx++, comercial.getApellido2());
            ps.setFloat(idx++, comercial.getComision());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de socio con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                comercial.setId(rsGenKeys.getInt(1));

        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    @Override
    public List<Comercial> getAll(){
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Comercial> listComercial = new ArrayList<>();

        try{
            conn = connectDB();

            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM comercial");
            while(rs.next()){
                Comercial comercial = new Comercial();

                comercial.setId(rs.getInt("id"));
                comercial.setNombre(rs.getString("nombre"));
                comercial.setApellido1(rs.getString("apellido1"));
                comercial.setApellido2(rs.getString("apellido2"));
                comercial.setComision(rs.getFloat("comision"));
                listComercial.add(comercial);
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            closeDb(conn, s, rs);
        }
        return listComercial;
    }
}

