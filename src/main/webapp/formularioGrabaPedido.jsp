<%@ page import="org.iesvdm.examen_crud.dao.ClienteDAOImpl" %>
<%@ page import="org.iesvdm.examen_crud.dao.ClienteDAO" %>
<%@ page import="org.iesvdm.examen_crud.model.Cliente" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: administrador
  Date: 13/12/24
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nuevo Pedido</title>
</head>
<body>
<%
  ClienteDAO clienteDAO = new ClienteDAOImpl();
  List<Cliente> listaClientes = clienteDAO.getAll();
%>
  <h4>Ingrese los datos de su pedido:</h4>
  <form method="post" action="GrabarPedidosServlet">
    Cantidad <input type="text" name="cantidad"/></br>
    Fecha <input type="date" name="fecha"/></br>
    Cliente <select name="selectCliente">
    <%
      Iterator i = listaClientes.iterator();
      if(i.hasNext()){
        Cliente c = (Cliente)i.next();
        %>
        <option value="<%= c.getId()%>"><%=c.getNombre()%></option>
        <%
      }
    %></select><br>
    Comercial <input type="text" name="idComercial"/></br>
    <button type="submit" value="Aceptar"></button>
  </form>

  <%
    String error = (String) request.getAttribute("error");
    if (error != null) {
  %>
  <div style="color: red"><%=error%></div>
  <%
    }
  %>
</body>
</html>
