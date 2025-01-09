<%@ page import="org.iesvdm.examen_crud.dao.ClienteDAOImpl" %>
<%@ page import="org.iesvdm.examen_crud.model.Cliente" %>
<%@ page import="org.iesvdm.examen_crud.dao.ClienteDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="org.iesvdm.examen_crud.dao.ComercialDAO" %>
<%@ page import="org.iesvdm.examen_crud.model.Comercial" %>
<%@ page import="org.iesvdm.examen_crud.dao.ComercialDAOImpl" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.sql.Date" %><%--
  Created by IntelliJ IDEA.
  User: antor
  Date: 08/01/2025
  Time: 17:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modificar Pedido</title>
</head>
<body>
<%
    ClienteDAO clienteDAO = new ClienteDAOImpl();
    List<Cliente> listaClientes = clienteDAO.getAll();
    Cliente cli;

    ComercialDAO comercialDAO = new ComercialDAOImpl();
    List<Comercial> listaComercial = comercialDAO.getAll();
    Comercial co;
%>
<h4>Ingrese los datos de su pedido:</h4>
<form method="post" action="EditarPedidosServlet">
    <input type="hidden" name="codigo" value="${pedido.id}" />
    Cantidad <input type="text" name="cantidad" value="${pedido.total}"/></br>
    Fecha <input type="date" name="fecha" value="${pedido.fecha}"/></br>
    Cliente <select name="selectCliente">
    <%
        Iterator i = listaClientes.iterator();
        while(i.hasNext()){
            cli = (Cliente)i.next();
    %>
    <option value="<%= cli.getId()%>"><%=cli.getNombre()%></option>
    <%
        }
    %></select><br>
    Comercial <select name="selectComercial">
    <%
        Iterator j = listaComercial.iterator();
        while(j.hasNext()){
            co = (Comercial)j.next();
    %>
    <option value="<%= co.getId()%>"><%=co.getNombre()%></option>
    <%
        }
    %></select><br>
    <button type="submit" value="Aceptar">Solicitar pedido</button>
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
