package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import connection.ConnectionFactory;
import model.bean.Cliente;

public class ClienteDAO {
	public void create(Cliente c) {
		Connection con = ConnectionFactory.getConnection();
		PreparedStatement stmt = null;
		
		try {
			stmt = con.prepareStatement("INSERT INTO CLIENTE(nome, email, estadocivil, idade) VALUES" + "(?,?,?,?)");
			stmt.setString(1, c.getNome());
			stmt.setString(2, c.getEmail());
			stmt.setString(3, c.getEstadocivil());
			stmt.setInt(4, c.getIdade());
			
		
			stmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cadastro feito com sucesso!");
			
		} catch(SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao cadastrar: "+ e);
		}finally {
			ConnectionFactory.closeConnection(con, stmt);
		}
	}
}
