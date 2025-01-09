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
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "EditarPedidosServlet", value = "/EditarPedidosServlet")
public class EditarPedidosServlet extends HttpServlet {
    private PedidosDAO pedidoDAO = new PedidosDAOImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String codigoStr = request.getParameter("codigo");//Conseguimos el código
        Integer codigo = null;

        //Y lo parseamos a Integer con un try catch para controlar errores
        try{
            codigo = Integer.parseInt(codigoStr);
        }catch (NumberFormatException nfe){
            nfe.printStackTrace();
        }

        //Si el codigo no es null...
        if(codigo != null){
            Optional<Pedido> auxPedido = pedidoDAO.find(codigo); //Nuestro socio ahora mismo es una variable Optional, la cual retiramos usando el código.
            if (auxPedido.isPresent()) {//Si hay algo dentro de la variable opcional...
                Pedido pedido = auxPedido.get();//Pasamos nuestra variabl1e Optional a una de pedido, por lo que ya podemos trabajar con ella
                request.setAttribute("pedido", pedido);//Añadimos el atributo
                RequestDispatcher dispatcher = request.getRequestDispatcher("formularioEditaPedido.jsp");//Y lo mandamos al formulario
                dispatcher.forward(request, response);
            } else {//Si no hay nada en auxPedido, lo controlamos con un error.
                response.sendRedirect("ListarPedidosServlet?err-cod=2");
            }
        }else{//Si el código es null, lo controlamos con un error
            response.sendRedirect("ListarPedidosServlet?err-cod=1");
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        Optional<Pedido> optionalPedido = UtilServlet.validaGrabarPedido(request);

        //SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalPedido.isPresent()) {

            //ACCEDO AL VALOR DE OPTIONAL DE SOCIO
            Pedido pedido = optionalPedido.get();
            pedido.setId(Integer.parseInt(request.getParameter("codigo")));

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
                this.pedidoDAO.update(pedido);

                List<Pedido> listado = this.pedidoDAO.getAll();
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

