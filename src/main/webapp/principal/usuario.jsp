<%@page import="servlets.ServletUsuarioController, model.ModelLogin" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="ISO-8859-1" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="head.jsp"/>

<body>

<jsp:include page="theme-loader.jsp"/>

<div id="pcoded" class="pcoded">
    <div class="pcoded-overlay-box"></div>
    <div class="pcoded-container navbar-wrapper">

        <jsp:include page="navbar.jsp"/>

        <div class="pcoded-main-container">
            <div class="pcoded-wrapper">

                <jsp:include page="navbarmainmenu.jsp"/>

                <div class="pcoded-content">

                    <jsp:include page="page-header.jsp"/>

                    <div class="pcoded-inner-content">
                        <div class="main-body">
                            <div class="page-wrapper">
                                <div class="page-body">

                                    <div class="row">
                                        <div class="col-sm-12">
                                            <div class="card">
                                                <div class="card-block">
                                                    <h4 class="sub-title">Cad. Usuário</h4>

                                                    <form class="form-material" enctype="multipart/form-data"
                                                          action="<%= request.getContextPath() %>/ServletUsuarioController"
                                                          method="post" id="formUser">

                                                        <input type="hidden" name="acao" id="acao" value="">

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="id" id="id" class="form-control"
                                                                   readonly="readonly" value="${modolLogin.id}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">ID:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="nome" id="nome"
                                                                   class="form-control" required="required"
                                                                   value="${modolLogin.nome}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Nome:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="dataNascimento" id="dataNascimento"
                                                                   class="form-control" required="required"
                                                                   value="${modolLogin.dataNascimento}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Data Nascimento:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="rendamensal" id="rendamensal"
                                                                   class="form-control" required="required"
                                                                   value="${modolLogin.rendamensal}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Renda Mensal:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="email" name="email" id="email"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.email}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">E-mail:</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <select class="form-control"
                                                                    aria-label="Default select example" name="perfil">
                                                                <option disabled="disabled">[Selecione o Perfil]
                                                                </option>

                                                                <option value="ADMIN"
                                                                        <c:if test="${modelLogin != null && modelLogin.perfil == 'ADMIN'}">
                                                                            selected="selected"</c:if>
                                                                >Admin
                                                                </option>

                                                                <option value="SECRETARIA"
                                                                        <c:if test="${modelLogin != null && modelLogin.perfil == 'SECRETARIA'}">
                                                                            selected="selected"</c:if>
                                                                >Secretária
                                                                </option>

                                                                <option value="AUXILIAR"
                                                                        <c:if test="${modelLogin != null && modelLogin.perfil == 'AUXILIAR'}">
                                                                            selected="selected"</c:if>
                                                                >Auxiliar
                                                                </option>

                                                            </select>
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Perfil:</label>
                                                        </div>


                                                        <div class="form-group form-default form-static-label">
                                                            <input onblur="pesquisaCep();" type="text" name="cep"
                                                                   id="cep" class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.cep}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Cep</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="logradouro" id="logradouro"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.logradouro}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Logradouro</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="bairro" id="bairro"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.bairro}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Bairro</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="cidade" id="cidade"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.cidade}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Cidade</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="uf" id="uf" class="form-control"
                                                                   required="required" autocomplete="off"
                                                                   value="${modolLogin.uf}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Estado</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="numero" id="numero"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.numero}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Número</label>
                                                        </div>


                                                        <div class="form-group form-default form-static-label">
                                                            <input type="text" name="login" id="login"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.login}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Login</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="password" name="senha" id="senha"
                                                                   class="form-control" required="required"
                                                                   autocomplete="off" value="${modolLogin.senha}">
                                                            <span class="form-bar"></span>
                                                            <label class="float-label">Senha</label>
                                                        </div>

                                                        <div class="form-group form-default form-static-label">
                                                            <input type="radio" name="sexo" checked="checked"
                                                                   value="MASCULINO"
                                                            <c:if test="${modelLogin != null && modelLogin.sexo == 'MASCULINO'}">
                                                                   selected="selected"</c:if>
                                                            >Masculino

                                                            <input type="radio" name="sexo" value="FEMININO"
                                                            <c:if test="${modelLogin != null && modelLogin.sexo == 'FEMININO'}">
                                                                   selected="selected"</c:if>
                                                            >Feminino
                                                        </div>

                                                        <button type="button"
                                                                class="btn btn-primary waves-effect waves-light"
                                                                onclick="limparForm();">
                                                            Novo
                                                        </button>
                                                        <button class="btn btn-success waves-effect waves-light">
                                                            Salvar
                                                        </button>
                                                        <button type="button"
                                                                class="btn btn-info waves-effect waves-light"
                                                                onclick="criarDeleteComAjax();">Excluir
                                                        </button>

                                                        <c:if test="${modolLogin.id > 0 }">
                                                            <a href="<%=request.getContextPath()%>/ServletTelefone?iduser=${modolLogin.id}"
                                                               class="btn btn-info waves-effect waves-light">Telefone</a>

                                                        </c:if>
                                                        <button type="button" class="btn btn-secondary"
                                                                data-toggle="modal" data-target="#exampleModalUsuario">
                                                            Pesquisar
                                                        </button>

                                                    </form>

                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <span id="msg">${msg}</span>

                                    <div style="height: 300px; overflow: scroll;">
                                        <table class="table" id="tabelaresultadosview">
                                            <thead>
                                            <tr>
                                                <th scope="col">ID</th>
                                                <th scope="col">Nome</th>
                                                <th scope="col">Ver</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items='${modelLogins}' var='ml'>
                                                <tr>
                                                    <td><c:out value="${ml.id}"></c:out></td>
                                                    <td><c:out value="${ml.nome}"></c:out></td>
                                                    <td><a class="btn btn-success"
                                                           href="<%= request.getContextPath() %>/ServletUsuarioController?acao=buscarEditar&id=${ml.id}">Ver</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>


                                    <nav aria-label="Page navigation example">
                                        <c:set var="totalPagina" value="${requestScope.totalPagina}"/>
                                        <ul class="pagination">
                                            <c:forEach begin="0" end="${totalPagina - 1}" var="ml">
                                                <c:set var="url"
                                                       value="${pageContext.request.contextPath}/ServletUsuarioController?acao=paginar&pagina=${p * 5}}"/>
                                                <li class="page-item">
                                                    <a class="page-link" href="${url}">${ml + 1}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </nav>
                                </div>
                            </div>
                            <div id="styleSelector"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="javascriptfile.jsp"/>


<!-- Modal -->
<div class="modal fade" id="exampleModalUsuario" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Pesquisa de usuário</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">

                <div class="input-group mb-3">
                    <input type="text" class="form-control" placeholder="Nome" aria-label="nome" id="nomeBusca"
                           aria-describedby="basic-addon2">
                    <div class="input-group-append">
                        <button class="btn btn-success" type="button" onclick="buscarUsuario();">Buscar</button>
                    </div>
                </div>

                <div style="height: 300px;overflow: scroll;">
                    <table class="table" id="tabelaresultados">
                        <thead>
                        <tr>
                            <th scope="col">ID</th>
                            <th scope="col">Nome</th>
                            <th scope="col">Ver</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
                <nav aria-label="Page navigation example">
                    <ul class="pagination" id="ulPaginacaoUserAjax">

                    </ul>
                </nav>

                <span id="totalResultados"></span>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
            </div>
        </div>
    </div>
</div>


<script href="js/usuario.js" type="text/javascript"></script>


</body>
</html>