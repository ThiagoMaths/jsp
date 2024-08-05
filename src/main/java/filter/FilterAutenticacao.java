package filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import connection.SingleConnectionBanco;

@WebFilter(urlPatterns = {"/principal/*"}) /*Intercepta todas as requises que vierem do projeto ou mapeamento*/
public class FilterAutenticacao extends HttpFilter implements Filter {
       
	private static Connection connection;
    
	private static final long serialVersionUID = 1L;

    public FilterAutenticacao() {
        super();
    }

	/*Encerra os processos quando o servidor é parado*/
	public void destroy() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
			/*Intercepta as requisiçoes e as resposta do sistema*/
			/*tudo que fier no sistema vai fazer por aqui*/
			/*Validação de autenticação*/
			/*Dar commit e rolback de transaçoes de banco*/
			/*Validar e fazer redirecionamento de paginas */
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpSession session = req.getSession();
			
			String usuarioLogado = (String) session.getAttribute("usuario");
			
			String urlParaAutenticar = req.getServletPath();
			
			if(usuarioLogado == null 
					&&!urlParaAutenticar.equalsIgnoreCase("/principal/ServletLogin")) {
				RequestDispatcher redireciona = request.getRequestDispatcher("/index.jsp?url=" + urlParaAutenticar);
				request.setAttribute("msg", "Por favor realize o login");
				redireciona.forward(request, response);
				return;/* Para a execucao e redireciona para o login*/
				
			}else {
				chain.doFilter(request, response); /*Deixa o processo cotinuar */
			}
			
			 connection.commit(); /*deu tudo certo, então comita as alterações no banco de dados*/
		} catch (Exception e) {
			e.printStackTrace();
			RequestDispatcher redirecionar = request.getRequestDispatcher("erro.jsp");
			request.setAttribute("msg", e.getMessage());
			redirecionar.forward(request, response);
			try {
				connection.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	
	}
			/*Inicia os processos pu recursos quando o servidor sobe os projetos*/
			/*Inicia conexao com o banco*/
	public void init(FilterConfig fConfig) throws ServletException {
		connection = SingleConnectionBanco.getConnection();
		
	}

}
