package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connection.SingleConnectionBanco;
import model.ModelTelefone;

public class DAOTelefoneRepository {
	
	private Connection connection;
	
	public DAOTelefoneRepository() {
		connection = SingleConnectionBanco.getConnection();
	}
	
	public void gravaTelefone (ModelTelefone modelTelefone) throws Exception{
		
		String sql = "insert into telefone (numero, usuario_pai_id, usuario_cad_id) values (?,?,?)";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setString(1, modelTelefone.getNumero());
		preparedStatement.setLong(2, modelTelefone.getUsuario_pai_id().getId());
		preparedStatement.setLong(3, modelTelefone.getUsuario_cad_id().getId());
		
		preparedStatement.execute();
		
		connection.commit();
		
		
		
	}

	
	
	
	
	public void deleteFone (Long id) throws Exception{
		String sql = "delete from telefone where id = ?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		preparedStatement.setLong(1,id);
		
		preparedStatement.executeUpdate();
		
		connection.commit();
	}
	
}
