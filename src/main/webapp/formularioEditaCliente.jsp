<%--
  Created by IntelliJ IDEA.
  User: antor
  Date: 09/01/2025
  Time: 13:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h4>Ingrese los datos de su pedido:</h4>
<form method="post" action="EditarClienteServlet">
    <input type="hidden" name="codigo" value="${cliente.id}" />
    Nombre <input type="text" name="nombre" value="${cliente.nombre}"/></br>
    Apellido1 <input type="text" name="apellido1" value="${cliente.apellido1}"/></br>
    Apellido2 <input type="text" name="apellido2" value="${cliente.apellido2}"/></br>
    Ciudad <input type="text" name="ciudad" value="${cliente.ciudad}"/></br>
    Categoria <input type="text" name="categoria" value="${cliente.categoria}"/></br>

    <button type="submit" value="Aceptar">Modificar cliente</button>
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
