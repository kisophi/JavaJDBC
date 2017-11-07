package br.com.akira.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.akira.model.bean.Aluno;
import br.com.akira.model.jdbc.ConnectionFactory;

public class AlunoDAO {

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
//	Adiciona Aluno
	public void adicionar(Aluno a){
		String sql = "INSERT INTO aluno VALUES (null,?,?)";
		try {
			conn = ConnectionFactory.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, a.getNome());
			ps.setDate(2, new Date(a.getDataCadastro().getTimeInMillis()));
			ps.execute();
			System.out.println("SQL INSERT : "+ps.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	Edita Aluno
	public void editar(Aluno a){
		String sql = "UPDATE aluno SET nome=?, dataCadastro=? WHERE id=?";
		try {
			conn = ConnectionFactory.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, a.getNome());
			ps.setDate(2, new Date(a.getDataCadastro().getTimeInMillis()));
			ps.setInt(3, a.getId());
			ps.execute();
			System.out.println("SQL UPDATE : "+ps.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	se ID for igual a Zero adiciona, senão Edita
	public void salvar(Aluno a){
		if(a.getId()==0){
			adicionar(a);
		}else {
			editar(a);
		}
	}
	
//	Exclui Aluno
	public void excluir(int id){
		String sql = "DELETE FROM aluno WHERE id=?";
		try {
			conn = ConnectionFactory.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.execute();
			System.out.println("SQL DELETE : "+ps.toString());
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//	Busca o Aluno pelo ID
	public Aluno buscaPorId(int id){
		String sql = "SELECT * FROM aluno WHERE id=?";
		try {
			conn = ConnectionFactory.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if(rs.next()){
				Aluno a = new Aluno();
				a.setId(rs.getInt("id"));
				a.setNome(rs.getString("nome"));
				
				Calendar nascimento = Calendar.getInstance();
				nascimento.setTime(rs.getDate("dataNascimento"));
				a.setDataCadastro(nascimento);
				return a;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	Busca Todos os Alunos
	public List<Aluno> buscaTodos(){
		String sql = "SELECT * FROM aluno";
		ArrayList<Aluno>lista = new ArrayList<>();
		try {
			conn = ConnectionFactory.getConnection();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()){
				Aluno a = new Aluno();
				a.setId(rs.getInt("id"));
				a.setNome(rs.getString("nome"));
				
				Calendar nascimento = Calendar.getInstance();
				nascimento.setTime(rs.getDate("dataNascimento"));
				a.setDataCadastro(nascimento);
				lista.add(a);
			}
			return lista;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		
 	
}
