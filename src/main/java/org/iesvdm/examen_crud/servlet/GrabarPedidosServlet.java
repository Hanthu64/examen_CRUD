package org.iesvdm.examen_crud.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.examen_crud.dao.PedidosDAO;
import org.iesvdm.examen_crud.dao.PedidosDAOImpl;
import org.iesvdm.examen_crud.model.Pedido;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.sql.Date;

@WebServlet(name = "GrabarPedidosServlet", value = "/GrabarPedidosServlet")
public class GrabarPedidosServlet extends HttpServlet {
    private PedidosDAO pedidosDAO = new PedidosDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/formularioGrabaPedido.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        Optional<Pedido> optionalPedido = UtilServlet.validaGrabarPedido(request);

        //SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalPedido.isPresent()) {

            //ACCEDO AL VALOR DE OPTIONAL DE SOCIO
            Pedido pedido = optionalPedido.get();

            Date diaDeHoy = new Date(System.currentTimeMillis());

            if(Double.parseDouble(request.getParameter("cantidad")) < 0){
                request.setAttribute("error","La cantidad del producto es negativa.");
                request.setAttribute("pedido", pedido);
                dispatcher = request.getRequestDispatcher("/formularioGrabaPedido.jsp");
            }else if(diaDeHoy.after(java.sql.Date.valueOf(request.getParameter("fecha")))){
                request.setAttribute("error","¡El pedido es para un día que ya pasó!");
                request.setAttribute("pedido", pedido);
                dispatcher = request.getRequestDispatcher("/formularioGrabaPedido.jsp");
            }else{
                //PERSITO EL SOCIO NUEVO EN BBDD
                this.pedidosDAO.create(pedido);

                List<Pedido> listado = this.pedidosDAO.getAll();
                request.setAttribute("listado", listado);
                request.setAttribute("id", pedido.getId());
                dispatcher = request.getRequestDispatcher("/WEB-INF/listadoPedidos.jsp");
            }
        } else {
            request.setAttribute("error", "Error de validación!");
            dispatcher = request.getRequestDispatcher("/formularioGrabaPedido.jsp");
        }


        //SIEMPRE PARA HACER EFECTIVA UNA REDIRECCIÓN INTERNA DEL SERVIDOR
        //TENEMOS QUE HACER FORWARD CON LOS OBJETOS request Y response
        dispatcher.forward(request,response);

    }
}
