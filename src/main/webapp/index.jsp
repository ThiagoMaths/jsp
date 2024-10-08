<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link href="css/index.css" rel="stylesheet">
    <title>Estoque</title>

</head>
<body>
<h5>Bem-vindo!</h5>
<div class="row">
    <form action="<%= request.getContextPath() %>/ServletLogin" method="post" novalidate>

        <input type="hidden" value=<%= request.getParameter("url") %> name="url">

        <div class="form-group row" id="groupLogin">
            <label for="login" class="form-label">Login:</label>
            <input name="login" id="login" type="text" class="form-control" required="required">
            <div class="invalid-feedback">
                Por favor, insira seu login.
            </div>
        </div>

        <div class="form-group row" id="groupSenha">
            <label for="senha" class="form-label">Senha:</label>
            <input name="senha" id="senha" type="password" class="form-control" required="required"
                   aria-describedby="senhaFeedback">
            <div class="invalid-feedback">
                Por favor, insira sua senha.
            </div>
        </div>

        <div class="input-group-button">
            <div class="col-12">
                <button type="submit" name="acessar" id="acessar" class="btn btn-primary" aria-label="Acessar">Acessar</button>
            </div>

        </div>

    </form>
</div>

<h5 class="msg">${msg}</h5>

<script href="js/index.js" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossOrigin="anonymous"
></script>

</body>
</html>