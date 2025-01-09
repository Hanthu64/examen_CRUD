<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Redirigiendo...</title>
</head>
<body>
<h1>Redirigiendo...</h1>
<%
    response.sendRedirect("ListarPedidosServlet");
%>
</body>
</html>