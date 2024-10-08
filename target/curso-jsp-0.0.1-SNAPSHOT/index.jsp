<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="icon" href="assets/images/icon-capsula.ico" type="image/png">

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

<title>Jdev</title>
<style type="text/css">


form{
	position: absolute;
	top: 40%;
	left: 33%;
	right: 33%;
	}
	
h1{
	position: absolute;
	top: 30%;
	left: 33%;
	}
	
h5{
	position: absolute;
	top: 30%;
	left: 33%;
	}	
	
.msg{
	position: absolute;
	top: 80%;
	left: 33%;
	font-size: 15px;
	color: red;
	
</style>



</head>
<body>
<h5>Bem vindo ao curso de JSP</h5>
	<form action="<%= request.getContextPath() %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	
	<input type="hidden" value= <%= request.getParameter("url") %> name="url">
	
	<div class="md-3">
	<label class="form-label">Login</label>
	 <input name="login" type="text" class="form-control" required="required">
	 <div class="invalid-feedback">
     Obrigat�rio
    </div>
	    </div>
	
	
	<div class="md-3">
	<label class="form-label">Senha</label>
	<input name="senha" type="password" class="form-control" required="required">
	<div class="invalid-feedback">
     Obrigat�rio
    </div>
	</div>
	
	  <div class="col-12">
	
		<button type="submit" class="btn btn-primary">Acessar</button>	
	</div>
	
	</form>
	
	<h5 class="msg">${msg}</h5>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous" type="text/javascript"></script>
	<script type="text/javascript">
	// Example starter JavaScript for disabling form submissions if there are invalid fields
	(() => {
	  'use strict'

	  // Fetch all the forms we want to apply custom Bootstrap validation styles to
	  const forms = document.querySelectorAll('.needs-validation')

	  // Loop over them and prevent submission
	  Array.from(forms).forEach(form => {
	    form.addEventListener('submit', event => {
	      if (!form.checkValidity()) {
	        event.preventDefault()
	        event.stopPropagation()
	      }

	      form.classList.add('was-validated')
	    }, false)
	  })
	})()
	</script>
</body>
</html>