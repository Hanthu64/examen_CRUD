package org.iesvdm.examen_crud.servlet;

import org.iesvdm.examen_crud.dao.PedidosDAO;
import org.iesvdm.examen_crud.dao.PedidosDAOImpl;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.examen_crud.model.Pedido;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ListarPedidosServlet", value = "/ListarPedidosServlet")
public class ListarPedidosServlet extends HttpServlet {
    private PedidosDAO pedidosDAO = new PedidosDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Double menor = 0.0;
        Double mayor = Double.MAX_VALUE;

        try{
            menor = Double.parseDouble(request.getParameter("menor"));
            mayor = Double.parseDouble(request.getParameter("mayor"));
        }catch(Exception e){}

        if(menor > mayor || mayor < 0 || menor < 0 || mayor.isNaN() || menor.isNaN()){
            request.setAttribute("error","Alguno de los valores puede ser inválido. Inténtalo de nuevo.");
        }else{
            request.setAttribute("menor", menor);
            request.setAttribute("mayor", mayor);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/listadoPedidos.jsp");

        List<Pedido> listado = this.pedidosDAO.getAll();
        request.setAttribute("listado", listado);
        dispatcher.forward(request, response);
    }
}
