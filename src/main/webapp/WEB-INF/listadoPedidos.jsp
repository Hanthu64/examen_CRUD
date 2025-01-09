<%@ page import="org.iesvdm.examen_crud.model.Pedido" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.xml.transform.Result" %><%--
  Created by IntelliJ IDEA.
  User: administrador
  Date: 13/12/24
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Pedidos</title>
</head>
<body>
<h1>Listado de pedidos</h1>
<input type="button" onclick="location.href='formularioGrabaPedido.jsp'" value="Crear"/>
<br>
<%
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/ventas","root", "root");

    Statement s = conexion.createStatement();
    ResultSet clienteXcomercial = s.executeQuery(
            "SELECT  co.nombre, COUNT(DISTINCT p.id_cliente) AS numero_clientes\n" +
                    "FROM pedido AS p\n" +
                    "LEFT JOIN comercial AS co ON p.id_comercial = co.id\n" +
                    "GROUP BY co.nombre;"
    );
    %>
<h2>Resumen clientes por comercial</h2>
<table>
    <tr>
        <td>Comercial</td>
        <td># Clientes</td>
    </tr>
    <%
        while (clienteXcomercial.next()) {
    %>
    <tr>
        <td><%=clienteXcomercial.getString("co.nombre")%></td>
        <td><%=clienteXcomercial.getString("numero_clientes")%></td>
    </tr>
    <% }
        clienteXcomercial.close();
    %>
</table>
<br>
<br>
<h4>Buscador por rango de cantidad</h4>
<form method="get" action="ListarPedidosServlet">
    De <input type="text" name="menor"/>
    A <input type="text" name="mayor"/>
    <button type="submit" value="Aceptar">Buscar</button>
</form>
<%
    String error = (String) request.getAttribute("error");

    if (error != null) {
%>
<div style="color: red"><%=error%></div>
<%
    }
    PreparedStatement preListado = conexion.prepareStatement(
            "SELECT p.id, p.total, p.fecha, c.nombre, c.id, co.nombre FROM pedido p LEFT JOIN cliente c on p.id_cliente = c.id LEFT JOIN comercial co on p.id_comercial = co.id WHERE p.total BETWEEN ? AND ?;"
    );
    Double menor = 0.0;
    Double mayor = Double.MAX_VALUE;

    try{
        menor = Double.parseDouble(request.getParameter("menor"));
        mayor = Double.parseDouble(request.getParameter("mayor"));
    }catch(Exception e){}

    preListado.setDouble(1, menor);
    preListado.setDouble(2, mayor);
    ResultSet listado = preListado.executeQuery();
    /*ResultSet listado = s.executeQuery
            ("SELECT p.id, p.total, p.fecha, c.nombre, c.id, co.nombre FROM pedido p LEFT JOIN cliente c on p.id_cliente = c.id LEFT JOIN comercial co on p.id_comercial = co.id;");*/
%>

<input type="button" onclick="location.href='formularioGrabaPedido.jsp'" value="Crear"/>
<br>
<table>
    <tr>
        <td>ID</td>
        <td>Total</td>
        <td>Fecha</td>
        <td>Nombre del cliente</td>
        <td>Nombre del comercial</td>
    </tr>
<%
    while (listado.next()) {
%>
    <tr>
        <td><%= listado.getInt("p.id")%></td>
        <td><%= listado.getDouble ("p.total")%></td>
        <td><%= listado.getDate("p.fecha") %></td>
        <td>
            <form method="get" action="EditarClienteServlet">
                <input type="hidden" name="codigo" value="<%= listado.getString("c.id")%>"/>
                <input type="submit" style="all: unset;" value="<%= listado.getString("c.nombre")%>">
            </form>
        </td>
        <td><%= listado.getString("co.nombre")%></td>
        <td>
            <form method="post" action="BorrarPedidosServlet">
                <input type="hidden" name="codigo" value="<%=listado.getInt("p.id")%>"/>
                <input class="btn btn-primary"  type="submit" value="Borrar">
            </form>
        </td>
        <td>
            <form method="get" action="EditarPedidosServlet">
                <input type="hidden" name="codigo" value="<%=listado.getInt("p.id")%>"/>
                <input class="btn btn-primary"  type="submit" value="Editar">
            </form>
        </td>
    </tr>
    <% }
    %>
</table>
<%
    listado.close();
    s.close();
    conexion.close();
%>
</body>
</html>
