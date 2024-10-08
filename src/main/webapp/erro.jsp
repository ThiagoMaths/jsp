<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tela que mostra os erros</title>
</head>
<body>
<div class="row">

    <h1> Mensagem de erro, entre em contato com a equipe de suporte do sistema</h1>
    <c:out value="${msg}" />

</div>


</body>
</html>