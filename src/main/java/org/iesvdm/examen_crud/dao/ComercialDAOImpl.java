package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComercialDAOImpl extends AbstractDAOImpl implements ClienteDAO {
    @Override
    public void create(Cliente cliente){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();


            //1 alternativas comentadas:
            //Ver también, AbstractDAOImpl.executeInsert ...
            //Columna fabricante.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente.
            ps = conn.prepareStatement("INSERT INTO cliente (id, nombre, apellido1, apellido2, ciudad, categoria) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setInt(idx++, cliente.getId());
            ps.setString(idx++, cliente.getNombre());
            ps.setString(idx++, cliente.getApellido1());
            ps.setString(idx++, cliente.getApellido2());
            ps.setString(idx++, cliente.getCiudad());
            ps.setInt(idx++, cliente.getCategoria());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de socio con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                cliente.setId(rsGenKeys.getInt(1));

        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }

    @Override
    public List<Cliente> getAll(){
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Cliente> listCliente = new ArrayList<>();

        try{
            conn = connectDB();

            s = conn.createStatement();

            rs = s.executeQuery("SELECT * FROM cliente");
            while(rs.next()){
                Cliente cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido1(rs.getString("apellido1"));
                cliente.setApellido2(rs.getString("apellido2"));
                cliente.setCiudad(rs.getString("id_comercial"));
                cliente.setCategoria(rs.getInt("categoria"));
                listCliente.add(cliente);
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            closeDb(conn, s, rs);
        }
        return listCliente;
    }
}

