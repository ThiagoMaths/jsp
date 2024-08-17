package servlets;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import util.ReportUtil;

@MultipartConfig
@WebServlet( urlPatterns =  {"/ServletUsuarioController"})
public class ServletUsuarioController extends ServletGenericUtil {
	
	@Serial
	private static final long serialVersionUID = 1L;
	
	
	private final DAOUsuarioRepository daoUsuarioRepository = new DAOUsuarioRepository();

    public ServletUsuarioController() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try {	
		
		 String acao  = request.getParameter("acao");
		 
		 if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletar")) {
			 
			 String idUser = request.getParameter("id");
			 
			 daoUsuarioRepository.userDelete(idUser);
			 
			 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
		     request.setAttribute("modelLogins", modelLogins);
		     
			 request.setAttribute("msg", "Excluido com sucesso!");
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

			 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("deletarajax")) {
				 
				 String idUser = request.getParameter("id");
				 
				 daoUsuarioRepository.userDelete(idUser);
				 
				 response.getWriter().write("Excluido com sucesso!");
				 
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjax")) {
			 
			 String nomeBusca = request.getParameter("nomeBusca");
			 
			 List<ModelLogin> dadosJsonUser =  daoUsuarioRepository.consultaUsuarioList(nomeBusca, super.getUserLogado(request));

			 ObjectMapper mapper = new ObjectMapper();
			 
			 String json = mapper.writeValueAsString(dadosJsonUser);
			 
			 response.addHeader("totalpagina", "" + daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
			 response.getWriter().write(json);
			 
		 }
		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarUserAjaxPage")) {

			 String nomeBusca = request.getParameter("nomeBusca");
			 String pagina = (request.getParameter("pagina"));

			 List<ModelLogin> dadosJsonUser =  daoUsuarioRepository.consultaUsuarioListOffset(nomeBusca, super.getUserLogado(request), Integer.parseInt(pagina));

			 ObjectMapper mapper = new ObjectMapper();

			 String json = mapper.writeValueAsString(dadosJsonUser);

			 response.addHeader("totalPagina", ""+ daoUsuarioRepository.consultaUsuarioListTotalPaginaPaginacao(nomeBusca, super.getUserLogado(request)));
			 response.getWriter().write(json);

		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("buscarEditar")) {
			
			    String id = request.getParameter("id");
			 
			     ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(Long.valueOf(id), super.getUserLogado(request));
			 
			     List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			     request.setAttribute("modelLogins", modelLogins);
			     
			    request.setAttribute("msg", "Usuário em edição");
				request.setAttribute("modolLogin", modelLogin);
				request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));
				request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		 }
		 
		 else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("listarUser")) {
			 
			 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
			 
			 request.setAttribute("msg", "Usuários carregados");
		     request.setAttribute("modelLogins", modelLogins);
		 	request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

			 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 

		 } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
			 	
			 Integer offset = Integer.parseInt(request.getParameter("pagina").toString());
			 
			 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
			 request.setAttribute("modelLogins", modelLogins);
			 	request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 
		 } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioUser")) {

			 String dataInicial = request.getParameter("dataInicial");
			 String dataFinal = request.getParameter("dataFinal");

			 if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {

				 request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request)));
			 } else {

				 request.setAttribute("listaUser", daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal));

			 }

			 request.setAttribute("dataInicial", dataInicial);
			 request.setAttribute("dataFinal", dataFinal);
			 request.getRequestDispatcher("principal/reluser.jsp").forward(request, response);

		 } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("imprimirRelatorioPdf")) {

			 String dataInicial = request.getParameter("dataInicial");
			 String dataFinal = request.getParameter("dataFinal");
;
			 List<ModelLogin> modelLogins = null;

			 if (dataInicial == null || dataInicial.isEmpty() && dataFinal == null || dataFinal.isEmpty()) {

				 modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request));

			 }else{

				 modelLogins = daoUsuarioRepository.consultaUsuarioListRel(super.getUserLogado(request), dataInicial, dataFinal);

			 }
			 
			 HashMap<String, Object> params = new HashMap<String, Object>();
			 params.put("PARAM_SUB_REPORT", request.getServletContext().getRealPath("relation") + File.separator);

			 byte[] relation = new ReportUtil().geraRelatorioPDF(modelLogins,"rel-user-jsp", params , request.getServletContext());

			 response.setHeader("Content-Disposition", "attachment; filename=arquivo.pdf");
			 response.getOutputStream().write(relation);


		 } else {
			 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
		     request.setAttribute("modelLogins", modelLogins);
		 	request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

			 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		 }
		 
		
		 
		 
		}catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
		String msg = "Operação realizada com sucesso!";	
		
		String id = request.getParameter("id");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String login = request.getParameter("login");
		String senha = request.getParameter("senha");
		String perfil = request.getParameter("perfil");
		String sexo = request.getParameter("sexo");
		String cep = request.getParameter("cep");
		String logradouro = request.getParameter("logradouro");
		String bairro = request.getParameter("bairro");
		String cidade = request.getParameter("cidade");
		String uf = request.getParameter("uf");
		String numero = request.getParameter("numero");
		String dataNascimento = request.getParameter("dataNascimento");
		String rendaMensal  = request.getParameter("rendamensal");

		rendaMensal = rendaMensal.split("\\ ")[1].replaceAll("\\.", "").replaceAll("\\,", ".");


		ModelLogin modelLogin = new ModelLogin();
		
		modelLogin.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
		modelLogin.setNome(nome);
		modelLogin.setEmail(email);
		modelLogin.setLogin(login);
		modelLogin.setSenha(senha);
		modelLogin.setPerfil(perfil);
		modelLogin.setSexo(sexo);
		modelLogin.setCep(cep);
		modelLogin.setLogradouro(logradouro);
		modelLogin.setBairro(bairro);
		modelLogin.setCidade(cidade);
		modelLogin.setUf(uf);
		modelLogin.setNumero(numero);

		Date.valueOf(new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento)));
		modelLogin.setDataNascimento(new Date(new SimpleDateFormat("dd/mm/yyyy").parse(dataNascimento).getTime()));
		modelLogin.setRendamensal(Double.valueOf(rendaMensal));
		
		if (daoUsuarioRepository.validarLogin(modelLogin.getLogin()) && modelLogin.getId() == null) {
			msg = "Já existe usuário com o mesmo login, informe outro login;";
		}else {
			if (modelLogin.isNovo()) {
				msg = "Gravado com sucesso!";
			}else {
				msg= "Atualizado com sucesso!";
			}
			
		    modelLogin = daoUsuarioRepository.gravarUsuario(modelLogin,super.getUserLogado(request) );
		}
		
		
		 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioList(super.getUserLogado(request));
	     request.setAttribute("modelLogins", modelLogins);
		request.setAttribute("msg", msg);
		request.setAttribute("modolLogin", modelLogin);
		request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

		request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
		
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
		}
		
	}

}