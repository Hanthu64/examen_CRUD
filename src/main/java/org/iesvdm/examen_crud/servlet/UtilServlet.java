package org.iesvdm.examen_crud.servlet;

import jakarta.servlet.http.HttpServletRequest;
import org.iesvdm.examen_crud.model.Cliente;
import org.iesvdm.examen_crud.model.Pedido;

import java.sql.Date;
import java.util.Optional;

public class UtilServlet {
    public static Optional<Pedido> validaGrabarPedido(HttpServletRequest request) {

        //CÓDIGO DE VALIDACIÓN
        double total = -1;
        Date fecha = null;
        int id_cliente = -1;
        int id_comercial = -1;
        try {
            total = Double.parseDouble(request.getParameter("cantidad"));

            fecha = Date.valueOf(request.getParameter("fecha"));

            id_cliente = Integer.parseInt(request.getParameter("selectCliente"));

            id_comercial = Integer.parseInt(request.getParameter("selectComercial"));


            return Optional.of(new Pedido(0, total, fecha, id_cliente, id_comercial, null, null));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //FIN CÓDIGO DE VALIDACIÓN
        return Optional.empty();

    }

    public static Optional<Cliente> validaGrabarCliente(HttpServletRequest request) {

        //CÓDIGO DE VALIDACIÓN
        String nombre = "";
        String apellido1 = "";
        String apellido2 = "";
        String ciudad = "";
        int categoria = -1;
        try {
            nombre = request.getParameter("nombre");

            apellido1 = request.getParameter("apellido1");

            apellido2 = request.getParameter("apellido2");

            ciudad = request.getParameter("ciudad");

            categoria = Integer.parseInt(request.getParameter("categoria"));


            return Optional.of(new Cliente(0, nombre, apellido1, apellido2, ciudad, categoria));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //FIN CÓDIGO DE VALIDACIÓN
        return Optional.empty();

    }
}