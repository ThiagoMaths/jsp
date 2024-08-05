package servlets;

import java.io.IOException;
import java.io.Serial;
import java.util.List;

import dao.DAOTelefoneRepository;
import dao.DAOUsuarioRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelLogin;
import jakarta.servlet.annotation.WebServlet;


@WebServlet("/ServletTelefone")
public class ServletTelefone extends ServletGenericUtil {
	@Serial
	private static final long serialVersionUID = 1L;
       


    public ServletTelefone() {
            }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
