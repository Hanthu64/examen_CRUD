package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Cliente;
import org.iesvdm.examen_crud.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAOImpl extends AbstractDAOImpl implements ClienteDAO {
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
            if (rows == 0){
                System.out.println("INSERT de socio con 0 filas insertadas.");
            }


            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next()){
                cliente.setId(rsGenKeys.getInt(1));
            }

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
                cliente.setCiudad(rs.getString("ciudad"));
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

    @Override
    public void update(Cliente cliente){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();


            //1 alternativas comentadas:
            //Ver también, AbstractDAOImpl.executeInsert ...
            //Columna fabricante.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente.
            ps = conn.prepareStatement("UPDATE cliente SET nombre = ?, apellido1 = ?, apellido2 = ?, ciudad = ?, categoria = ? WHERE id = ?");

            int idx = 1;
            ps.setString(idx++, cliente.getNombre());
            ps.setString(idx++, cliente.getApellido1());
            ps.setString(idx++, cliente.getApellido2());
            ps.setString(idx++, cliente.getCiudad());
            ps.setInt(idx++, cliente.getCategoria());
            ps.setInt(idx++, cliente.getId());


            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("UPDATE de cliente con 0 filas insertadas.");
            }
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public Optional<Cliente> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Cliente> listCliente = new ArrayList<>();

        Cliente cliente = null;
        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT * FROM cliente WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if(rs.next()){
                cliente = new Cliente();

                cliente.setId(rs.getInt("id"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido1(rs.getString("apellido1"));
                cliente.setApellido2(rs.getString("apellido2"));
                cliente.setCiudad(rs.getString("ciudad"));
                cliente.setCategoria(rs.getInt("categoria"));
            }

            return Optional.of(cliente);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);

        }
        return Optional.empty();
    }
}

