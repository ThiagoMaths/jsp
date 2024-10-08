$("#rendamensal").maskMoney({showSymbol: true, symbol: "R$ ", decimal: ",", thousands: "."});
const formatter = new Intl.NumberFormat('PT-BR', {
    currency: 'BRL',
    minimumFractionDigits: 2

});

$("#rendamensal").val(formatter.format($("#rendamensal").val()));
$("#rendamensal").focus();

var dataNascimento = $("#dataNascimento").val();
var dataFormat = new date(dataNascimento);

$("#dataNascimento").val(dataFormat.toLocaleString('pt-BR', {timeZone: 'UTC'}));

$("#nome").focus();

$(function () {
    $("#dataNascimento").datepicker({
        dateFormat: 'dd/mm/yy',
        dayNames: ['Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta', 'Sexta', 'Sábado'],
        dayNamesMin: ['D', 'S', 'T', 'Q', 'Q', 'S', 'S', 'D'],
        dayNamesShort: ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb', 'Dom'],
        monthNames: ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'],
        monthNamesShort: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul', 'Ago', 'Set', 'Out', 'Nov', 'Dez'],
        nextText: '>',
        prevText: '<'
    });
});

$("#numero").keypress(function (event) {

    return /\d/.test(String.fromCharCode(event.keyCode));

});

$("#cep").keypress(function (event) {

    return /\d/.test(String.fromCharCode(event.keyCode));

});

function pesquisaCep() {
    var cep = $("#cep").val();

    $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {

        if (!("erro" in dados)) {
            $("#cep").val(dados.cep);
            $("#logradouro").val(dados.logradouro);
            $("#bairro").val(dados.bairro);
            $("#cidade").val(dados.localidade);
            $("#uf").val(dados.uf);
        }


    });
}

function visualizarImg(fotoembase64, filefoto) {


    var preview = document.getElementById(fotoembase64); // campo IMG html
    var fileUser = document.getElementById(filefoto).files[0];
    var reader = new FileReader();

    reader.onloadend = function () {
        preview.src = reader.result; /*Carrega a foto na tela*/
    };

    if (fileUser) {
        reader.readAsDataURL(fileUser); /*Preview da imagem*/
    } else {
        preview.src = '';
    }

}


function verEditar(id) {

    var urlAction = document.getElementById('formUser').action;


    window.location.href = urlAction + '?acao=buscarEditar&id=' + id;

}

function buscaUserPagAjax(url) {


    var urlAction = document.getElementById('formUser').action;
    var nomeBusca = document.getElementById('nomeBusca').value;

    $.ajax({
        method: "get",
        url: urlAction,
        data: url,
        success: function (response, textStatus, xhr) {

            var json = JSON.parse(response);


            $('#tabelaresultados > tbody > tr').remove();
            $("#ulPaginacaoUserAjax > li").remove();

            for (var p = 0; p < json.length; p++) {
                $('#tabelaresultados > tbody').append('<tr> <td>' + json[p].id + '</td> <td> ' + json[p].nome + '</td> <td><button onclick="verEditar(' + json[p].id + ')" type="button" class="btn btn-info">Ver</button></td></tr>');
            }

            document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;

            var totalPagina = xhr.getResponseHeader("totalPagina");


            for (var p = 0; p < totalPagina; p++) {


                var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);


                $("#ulPaginacaoUserAjax").append('<li class="page-item"> <a class="page-link" href="#" onclick="buscaUserPagAjax(\'' + url + '\')">' + (p + 1) + '</a></li>');

            }

        }

    }).fail(function (xhr, status, errorThrown) {
        alert('Erro ao buscar usuário por nome: ' + xhr.responseText);
    });
}

function buscarUsuario() {

    var nomeBusca = document.getElementById('nomeBusca').value;

    if (nomeBusca != null && nomeBusca !== '' && nomeBusca.trim() !== '') { /*Validando que tem que ter valor pra buscar no banco*/

        var urlAction = document.getElementById('formUser').action;

        $.ajax({

            method: "get",
            url: urlAction,
            data: "nomeBusca=" + nomeBusca + '&acao=buscarUserAjax',
            success: function (response, textStatus, xhr) {

                var json = JSON.parse(response);


                $('#tabelaresultados > tbody > tr').remove();
                $('#ulPaginacaoUserAjax > li').remove();


                for (var p = 0; p < json.length; p++) {
                    $('#tabelaresultados > tbody').append('<tr> <td>' + json[p].id + '</td> <td> ' + json[p].nome + '</td> <td><button onclick="verEditar(' + json[p].id + ')" type="button" class="btn btn-info">Ver</button></td></tr>');
                }

                document.getElementById('totalResultados').textContent = 'Resultados: ' + json.length;
                var totalPagina = xhr.getResponseHeader('totalPagina');

                for (var p = 0; p < totalPagina; p++) {

                    var url = 'nomeBusca=' + nomeBusca + '&acao=buscarUserAjaxPage&pagina=' + (p * 5);

                    $("#ulPaginacaoUserAjax").append('<li class="page-item"><a class="page-link" href="#" onclick="buscaUserPagAjax(\'' + url + '\')">' + (p + 1) + '</a></li>');
                }

            }

        }).fail(function (xhr, status, errorThrown) {
            alert('Erro ao buscar usuário por nome: ' + xhr.responseText);

        });

    }

}

function criarDeleteComAjax() {

    if (confirm('Deseja realmente excluir os dados?')) {

        var urlAction = document.getElementById('formUser').action;
        var idUser = document.getElementById('id').value;

        $.ajax({

            method: "get",
            url: urlAction,
            data: "id=" + idUser + '&acao=deletarajax',
            success: function (response, textStatus, xhr) {

                limparForm();
                document.getElementById('msg').textContent = response;
            }

        }).fail(function (xhr, status, errorThrown) {
            alert('Erro ao deletar usuário por id: ' + xhr.responseText);
        });


    }

}


function criarDelete() {

    if (confirm('Deseja realmente excluir os dados?')) {

        document.getElementById("formUser").method = 'get';
        document.getElementById("acao").value = 'deletar';
        document.getElementById("formUser").submit();

    }

}


function limparForm() {

    var elementos = document.getElementById("formUser").elements; /*Retorna os elementos html dentro do form*/

    for (p = 0; p < elementos.length; p++) {
        elementos[p].value = '';
    }
}


