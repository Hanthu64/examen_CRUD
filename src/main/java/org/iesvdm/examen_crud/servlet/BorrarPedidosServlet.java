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

@WebServlet(name = "BorrarPedidosServlet", value = "/BorrarPedidosServlet")
public class BorrarPedidosServlet extends HttpServlet {
    private PedidosDAO pedidoDAO = new PedidosDAOImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;

        String codigoStr = request.getParameter("codigo");
        Integer codigo = null;

        try{
            codigo = Integer.parseInt(codigoStr);
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }

        if(codigo != null){
            pedidoDAO.delete(codigo);
            List<Pedido> listado = this.pedidoDAO.getAll();
            request.setAttribute("listado", listado);

            dispatcher = request.getRequestDispatcher("/WEB-INF/listadoPedidos.jsp");
            dispatcher.forward(request, response);
        }else{
            response.sendRedirect("listadoPedidos.jsp?err-cod=1");
        }
    }
}
