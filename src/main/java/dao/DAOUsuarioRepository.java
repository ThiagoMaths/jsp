package dao;

import connection.SingleConnectionBanco;
import model.ModelLogin;
import model.ModelTelefone;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DAOUsuarioRepository {

	private final Connection connection;

	public DAOUsuarioRepository() {

		connection = SingleConnectionBanco.getConnection();
	}

	public ModelLogin gravarUsuario(ModelLogin objeto, Long userLogado) throws Exception {

		if (objeto.isNovo()) {

			String sql = "INSERT INTO model_login(login, senha, nome, email, usuario_id, perfil, sexo, cep, logradouro, bairro, cidade, uf, numero, datanascimento, rendamensal)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			PreparedStatement preparedsql = connection.prepareStatement(sql);

			preparedsql.setString(1, objeto.getLogin());
			preparedsql.setString(2, objeto.getSenha());
			preparedsql.setString(3, objeto.getNome());
			preparedsql.setString(4, objeto.getEmail());
			preparedsql.setLong(5, userLogado);
			preparedsql.setString(6, objeto.getPerfil());
			preparedsql.setString(7, objeto.getSexo());
			preparedsql.setString(8, objeto.getCep());
			preparedsql.setString(9, objeto.getLogradouro());
			preparedsql.setString(10, objeto.getBairro());
			preparedsql.setString(11, objeto.getCidade());
			preparedsql.setString(12, objeto.getUf());
			preparedsql.setString(13, objeto.getNumero());
			preparedsql.setDate(14, objeto.getDataNascimento());
			preparedsql.setString(15, objeto.getRendamensal().toString());

			preparedsql.execute();

			connection.commit();


			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where login =?";
				preparedsql = connection.prepareStatement(sql);
				preparedsql.setString(1, objeto.getFotoUser());
				preparedsql.setString(2, objeto.getExtensaofotouser());
				preparedsql.setString(3, objeto.getLogin());
				
				
				preparedsql.execute();
	
				connection.commit();
			}
		
		
		
		} else {
			String sql = "UPDATE model_login SET login=?, senha=?, nome=?, email=?, perfil=?, sexo=?, cep=?, logradouro=?, bairro=?, cidade=?, uf=?, numero=?, datanascimento=?, rendamensal=? WHERE id = " + objeto.getId() + ";";
			PreparedStatement preparesql = connection.prepareStatement(sql);
			preparesql.setString(1, objeto.getLogin());
			preparesql.setString(2, objeto.getSenha());
			preparesql.setString(3, objeto.getNome());
			preparesql.setString(4, objeto.getEmail());
			preparesql.setString(5, objeto.getPerfil());
			preparesql.setString(6, objeto.getSexo());
			preparesql.setString(7, objeto.getCep());
			preparesql.setString(8, objeto.getLogradouro());
			preparesql.setString(9, objeto.getBairro());
			preparesql.setString(10, objeto.getCidade());
			preparesql.setString(11, objeto.getUf());
			preparesql.setString(12, objeto.getNumero());
			preparesql.setDate(13, objeto.getDataNascimento());
			preparesql.setDouble(14, objeto.getRendamensal());



			preparesql.executeUpdate();

			connection.commit();
			
			if (objeto.getFotoUser() != null && !objeto.getFotoUser().isEmpty()) {
				sql = "update model_login set fotouser =?, extensaofotouser=? where id =?";
				preparesql = connection.prepareStatement(sql);
				preparesql.setString(1, objeto.getFotoUser());
				preparesql.setString(2, objeto.getExtensaofotouser());
				preparesql.setLong(3, objeto.getId());
				
				
				preparesql.execute();
	
				connection.commit();
			}

		}

		return this.consultaUsuario(objeto.getLogin(), userLogado);
	}


	public List<ModelLogin> consultaUsuarioListRel(Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id =  " + userLogado;
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre o resultado do SQL */
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));


			retorno.add(modelLogin);

		}
		return retorno;

	}

	public List<ModelLogin> consultaUsuarioListRel(Long userLogado, String dataInicial, String dataFinal) throws Exception {

		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id =  " + userLogado + " and datanascimento >= ? and datanascimento <= ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setDate(1, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataInicial))));
		statement.setDate(2, Date.valueOf(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataFinal))));


		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre o resultado do SQL */
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("datanascimento"));
			modelLogin.setTelefones(this.listFone(modelLogin.getId()));


			retorno.add(modelLogin);

		}
		return retorno;

	}

	
	public List<ModelLogin> consultaUsuarioList(Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id =  " + userLogado + "limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre o resultado do SQL */
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

			retorno.add(modelLogin);

		}
		return retorno;

	}

	public int totalPagina(Long userLogado) throws Exception {
		
		String sql = "select count (1) as total from model_login where usuario_id = " + userLogado;
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultado = statement.executeQuery();
		
		resultado.next();
		
		Double cadastros = resultado.getDouble("total");
		
		Double porPagina = 5.0;
		
		double pagina = cadastros / porPagina;
		
		double resto = pagina % 2;
		
		if (resto > 0) {
			pagina ++;
		}
		return (int) pagina;
	}
	
	
	public List<ModelLogin> consultaUsuarioListPaginada(Long userLogado, Integer offset) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login where useradmin is false and usuario_id =  " + userLogado + " order by nome offset "+offset+" limit 5";
		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre o resultado do SQL */
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

			retorno.add(modelLogin);

		}
		return retorno;

	}




	public int consultaUsuarioListTotalPaginaPaginacao(String nome, Long userLogado) throws Exception {


		String sql = "select count(1) as total from model_login  where upper(nome) like upper (?) and useradmin is false and usuario_id = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		resultado.next();

		Double cadastros = resultado.getDouble("total");

		Double porPagina = 5.0;

		double pagina = cadastros / porPagina;

		double resto = pagina % 2;

		if (resto > 0) {
			pagina ++;
		}
		return (int) pagina;


	}



	public List<ModelLogin> consultaUsuarioList(String nome, Long userLogado) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre o resultado do SQL */
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

			retorno.add(modelLogin);

		}
		return retorno;

	}
	public List<ModelLogin> consultaUsuarioListOffset(String nome, Long userLogado, int offset) throws Exception {
		List<ModelLogin> retorno = new ArrayList<ModelLogin>();

		String sql = "select * from model_login  where upper(nome) like upper(?) and useradmin is false and usuario_id = ? offset "+offset+" limit 5";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, "%" + nome + "%");
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) { /* Percorre o resultado do SQL */
			ModelLogin modelLogin = new ModelLogin();
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setNome(resultado.getString("nome"));
			// modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));


			retorno.add(modelLogin);

		}
		return retorno;

	}

	public ModelLogin consultaUsuarioLogado(String Login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + Login + "')";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tem resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setUserAdmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));

			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}

		return modelLogin;
	}

	public ModelLogin consultaUsuario(String Login) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + Login + "') and useradmin is false";

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tem resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}

		return modelLogin;
	}

	
	
	
	
	public ModelLogin consultaUsuario(String Login, Long userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where upper(login) = upper('" + Login + "') and useradmin is false and usuario_id = " + userLogado;

		PreparedStatement statement = connection.prepareStatement(sql);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tem resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}

		return modelLogin;
	}
	public ModelLogin consultaUsuarioID(Long id) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false ";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tem resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setUserAdmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}

		return modelLogin;
	}
	public ModelLogin consultaUsuarioId(Long id, Long  userLogado) throws Exception {

		ModelLogin modelLogin = new ModelLogin();

		String sql = "select * from model_login where id = ? and useradmin is false and usuario_id = ? ";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);
		statement.setLong(2, userLogado);

		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) /* se tem resultado */ {

			modelLogin.setId(resultado.getLong("id"));
			modelLogin.setEmail(resultado.getString("email"));
			modelLogin.setSenha(resultado.getString("senha"));
			modelLogin.setNome(resultado.getString("nome"));
			modelLogin.setLogin(resultado.getString("login"));
			modelLogin.setUserAdmin(resultado.getBoolean("userAdmin"));
			modelLogin.setPerfil(resultado.getString("perfil"));
			modelLogin.setSexo(resultado.getString("sexo"));
			modelLogin.setFotoUser(resultado.getString("fotouser"));
			modelLogin.setExtensaofotouser(resultado.getString("extensaofotouser"));
			modelLogin.setCep(resultado.getString("cep"));
			modelLogin.setLogradouro(resultado.getString("logradouro"));
			modelLogin.setBairro(resultado.getString("bairro"));
			modelLogin.setCidade(resultado.getString("cidade"));
			modelLogin.setUf(resultado.getString("uf"));
			modelLogin.setNumero(resultado.getString("numero"));
			modelLogin.setDataNascimento(resultado.getDate("dataNascimento"));
			modelLogin.setRendamensal(resultado.getDouble("rendamensal"));

		}

		return modelLogin;
	}
	

	public boolean validarLogin(String login) throws Exception {

		String sql = "select count (1) > 0 as existe from model_login where upper(login) = upper('" + login + "');";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		resultado.next(); /* Para ele entrar no resultado do sql */
		return resultado.getBoolean("Existe");

	}

	public void userDelete(String idUser) throws Exception {
		String sql = "DELETE FROM model_login WHERE id = ? and useradmin is false ;";
		PreparedStatement prearesql = connection.prepareStatement(sql);

		prearesql.setLong(1, Long.parseLong(idUser));
		prearesql.executeUpdate();

		connection.commit();

	}
	public List<ModelTelefone> listFone (Long idUserPai) throws Exception{
		List<ModelTelefone> retorno = new ArrayList<ModelTelefone>();

		String sql = "select * from telefone where usuario_pai_id = ?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, idUserPai);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			ModelTelefone model = new ModelTelefone();
			model.setId(rs.getLong("id"));
			model.setNumero(rs.getString("numero"));
			model.setUsuario_cad_id(this.consultaUsuarioID(rs.getLong("usuario_cad_id")));
			model.setUsuario_pai_id(this.consultaUsuarioID(rs.getLong("usuario_pai_id")));

			retorno.add(model);

		}

		return retorno;
	}
}
