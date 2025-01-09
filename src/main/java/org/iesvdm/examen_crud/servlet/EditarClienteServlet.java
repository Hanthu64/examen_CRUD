package org.iesvdm.examen_crud.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesvdm.examen_crud.dao.ClienteDAO;
import org.iesvdm.examen_crud.dao.ClienteDAOImpl;
import org.iesvdm.examen_crud.model.Cliente;
import org.iesvdm.examen_crud.model.Pedido;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet(name = "EditarClienteServlet", value = "/EditarClienteServlet")
public class EditarClienteServlet extends HttpServlet {
    private ClienteDAO clienteDAO = new ClienteDAOImpl();

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
            Optional<Cliente> auxCliente = clienteDAO.find(codigo); //Nuestro socio ahora mismo es una variable Optional, la cual retiramos usando el código.
            if (auxCliente.isPresent()) {//Si hay algo dentro de la variable opcional...
                Cliente cliente = auxCliente.get();//Pasamos nuestra variabl1e Optional a una de cliente, por lo que ya podemos trabajar con ella
                request.setAttribute("cliente", cliente);//Añadimos el atributo
                RequestDispatcher dispatcher = request.getRequestDispatcher("formularioEditaCliente.jsp");//Y lo mandamos al formulario
                dispatcher.forward(request, response);
            } else {//Si no hay nada en auxCliente, lo controlamos con un error.
                response.sendRedirect("ListarPedidosServlet?err-cod=2");
            }
        }else{//Si el código es null, lo controlamos con un error
            response.sendRedirect("ListarPedidosServlet?err-cod=1");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = null;
        Optional<Cliente> optionalCliente = UtilServlet.validaGrabarCliente(request);

        //SI OPTIONAL CON SOCIO PRESENTE <--> VALIDA OK
        if (optionalCliente.isPresent()) {

            //ACCEDO AL VALOR DE OPTIONAL DE SOCIO
            Cliente cliente = optionalCliente.get();
            cliente.setId(Integer.parseInt(request.getParameter("codigo")));

            if(cliente.getNombre().isEmpty() || cliente.getApellido1().isEmpty() || cliente.getApellido2().isEmpty()){
                request.setAttribute("error", "Nombre o apellidos están vacíos.");
                request.setAttribute("cliente", cliente);
                dispatcher = request.getRequestDispatcher("/formularioEditaCliente.jsp");
            }else if(cliente.getCategoria() > 300 || cliente.getCategoria() <= 0){
                request.setAttribute("error", "La categoría introducida no existe.");
                request.setAttribute("cliente", cliente);
                dispatcher = request.getRequestDispatcher("/formularioEditaCliente.jsp");
            }else{
                this.clienteDAO.update(cliente);
                dispatcher = request.getRequestDispatcher("/WEB-INF/listadoPedidos.jsp");
            }
        } else {
            request.setAttribute("error", "Error de validación!");
            dispatcher = request.getRequestDispatcher("/formularioEditaCliente.jsp");
        }


        //SIEMPRE PARA HACER EFECTIVA UNA REDIRECCIÓN INTERNA DEL SERVIDOR
        //TENEMOS QUE HACER FORWARD CON LOS OBJETOS request Y response
        dispatcher.forward(request,response);

    }
}
