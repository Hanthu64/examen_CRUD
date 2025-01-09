package org.iesvdm.examen_crud.dao;

import org.iesvdm.examen_crud.model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.iesvdm.examen_crud.dao.AbstractDAOImpl.closeDb;

public class PedidosDAOImpl extends AbstractDAOImpl implements PedidosDAO {
    @Override
    public void create(Pedido pedido){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSet rsGenKeys = null;

        try {
            conn = connectDB();


            //1 alternativas comentadas:
            //Ver también, AbstractDAOImpl.executeInsert ...
            //Columna fabricante.codigo es clave primaria auto_increment, por ese motivo se omite de la sentencia SQL INSERT siguiente.
            ps = conn.prepareStatement("INSERT INTO pedido (total, fecha, id_cliente, id_comercial) VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            int idx = 1;
            ps.setDouble(idx++, pedido.getTotal());
            ps.setDate(idx++, pedido.getFecha());
            ps.setInt(idx++, pedido.getId_cliente());
            ps.setInt(idx++, pedido.getId_comercial());

            int rows = ps.executeUpdate();
            if (rows == 0)
                System.out.println("INSERT de socio con 0 filas insertadas.");

            rsGenKeys = ps.getGeneratedKeys();
            if (rsGenKeys.next())
                pedido.setId(rsGenKeys.getInt(1));

        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public void update(Pedido pedido){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();


            ps = conn.prepareStatement("UPDATE pedido SET total = ?, fecha = ?, id_cliente = ?, id_comercial = ? WHERE id = ?");

            int idx = 1;
            ps.setDouble(idx++, pedido.getTotal());
            ps.setDate(idx++, pedido.getFecha());
            ps.setInt(idx++, pedido.getId_cliente());
            ps.setInt(idx++, pedido.getId_comercial());
            ps.setInt(idx++, pedido.getId());


            int rows = ps.executeUpdate();
            if (rows == 0) {
                System.out.println("UPDATE de pedido con 0 filas insertadas.");
            }
        } catch (SQLException | ClassNotFoundException  e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public List<Pedido> getAll(){
        Connection conn = null;
        Statement s = null;
        ResultSet rs = null;

        List<Pedido> listPedido = new ArrayList<>();

        try{
            conn = connectDB();

            s = conn.createStatement();

            rs = s.executeQuery("SELECT p.*, co.nombre, c.nombre FROM pedido AS p " +
                    "LEFT JOIN comercial AS co ON p.id_comercial = co.id " +
                    "LEFT JOIN cliente AS c ON p.id_cliente = c.id ");
            while(rs.next()){
                Pedido pedido = new Pedido();

                pedido.setId(rs.getInt("id"));
                pedido.setTotal(rs.getDouble("total"));
                pedido.setFecha(rs.getDate("fecha"));
                pedido.setId_cliente(rs.getInt("id_cliente"));
                pedido.setId_comercial(rs.getInt("id_comercial"));
                pedido.setNombreCliente(rs.getString("c.nombre"));
                pedido.setNombreComercial(rs.getString("co.nombre"));
                listPedido.add(pedido);
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            closeDb(conn, s, rs);
        }
        return listPedido;
    }

    @Override
    public void delete(int id) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = connectDB();

            ps = conn.prepareStatement("DELETE FROM pedido WHERE id = ?");
            int idx = 1;
            ps.setInt(idx, id);

            int rows = ps.executeUpdate();

            if (rows == 0)
                System.out.println("Delete de pedido con 0 registros eliminados.");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);
        }

    }
    @Override
    public Optional<Pedido> find(int id) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Pedido> listPedido = new ArrayList<>();

        Pedido pedido = null;
        try {
            conn = connectDB();

            ps = conn.prepareStatement("SELECT p.*, co.nombre, c.nombre FROM pedido AS p " +
                    "LEFT JOIN comercial AS co ON p.id_comercial = co.id " +
                    "LEFT JOIN cliente AS c ON p.id_cliente = c.id WHERE p.id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if(rs.next()){
                pedido = new Pedido();

                pedido.setId(rs.getInt("p.id"));
                pedido.setTotal(rs.getDouble("p.total"));
                pedido.setFecha(rs.getDate("p.fecha"));
                pedido.setId_cliente(rs.getInt("p.id_cliente"));
                pedido.setId_comercial(rs.getInt("p.id_comercial"));
                pedido.setNombreCliente(rs.getString("c.nombre"));
                pedido.setNombreComercial(rs.getString("co.nombre"));
            }

            return Optional.of(pedido);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            closeDb(conn, ps, rs);

        }
        return Optional.empty();
    }
}
