package servlets;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Part;

import dao.DAOUsuarioRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;

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
			 
		 } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("downloadFoto")) {
			String idUser = request.getParameter("id");
			
			ModelLogin modelLogin = daoUsuarioRepository.consultaUsuarioId(Long.valueOf(idUser), super.getUserLogado(request));
			if (modelLogin.getFotoUser() != null && !modelLogin.getFotoUser().isEmpty()) {
				
				response.setHeader("content-Disposition", "attachment;filename=arquivo." + modelLogin.getExtensaofotouser());
				response.getOutputStream().write(new Base64().decodeBase64(modelLogin.getFotoUser().split("\\,")[1]));
				
			}
		 } else if (acao != null && !acao.isEmpty() && acao.equalsIgnoreCase("paginar")) {
			 	
			 Integer offset = Integer.parseInt(request.getParameter("pagina").toString());
			 
			 List<ModelLogin> modelLogins = daoUsuarioRepository.consultaUsuarioListPaginada(this.getUserLogado(request), offset);
			 request.setAttribute("modelLogins", modelLogins);
			 	request.setAttribute("totalPagina", daoUsuarioRepository.totalPagina(this.getUserLogado(request)));

				 request.getRequestDispatcher("principal/usuario.jsp").forward(request, response);
			 
		 }

		 
		 else {
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

		if(ServletFileUpload.isMultipartContent(request)) {
			
			Part part = request.getPart("fileFoto"); /*Pega a foto da tela*/
			
			if(part.getSize() > 0) {
			byte[] foto = IOUtils.toByteArray(part.getInputStream()); /*Converta imagem para byte*/
			String  imagemaBase64 = "data:image/" + part.getContentType().split("\\/") [1] + ";base64," + new Base64().encodeBase64String(foto); /*Padrão para o html entender a imagem*/	
			modelLogin.setFotoUser(imagemaBase64);
			modelLogin.setExtensaofotouser(part.getContentType().split("\\/") [1]);
			}
		}
		
		
		
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