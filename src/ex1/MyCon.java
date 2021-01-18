package ex1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyCon {
	
	private Connection myConnection;
	
	public MyCon() throws FileNotFoundException, IOException, SQLException {
		Properties props=new Properties();
		
		String path=System.getProperty("user.dir");
		props.load(new FileInputStream(path+"\\src\\ex1\\demo.properties"));
		
		String user=props.getProperty("user");
		String password=props.getProperty("password");
		String dburl=props.getProperty("dburl");
		
		myConnection=DriverManager.getConnection(dburl,user,password);
		
		System.out.println("Database connection successful to : "+dburl);;
	}
	
	public List<Persoana> getPersoane() throws SQLException
	{
		List<Persoana> list=new ArrayList<>();
		
		Statement myStatement=null;
		ResultSet myRs=null;
		
		try {
			myStatement=myConnection.createStatement();
			myRs=myStatement.executeQuery("select * from persoane");
			
			while(myRs.next()) {
				Persoana tempPersoana=convertRowToPersoana(myRs);
				list.add(tempPersoana);
			}
			return list;
		}finally {
			close(myStatement,myRs);
		}
	}
	
	public void addPerson(Persoana p) throws SQLException {
		Statement myStatement=null;
		
		try {
			myStatement=myConnection.createStatement();
			myStatement.execute("INSERT INTO persoane(id,nume,varsta) VALUES("+p.getId()+","+"'"+p.getNume()+"'"+","+p.getVarsta()+");");
		}finally {
			close(myStatement,null);
		}
		
	}
	
	public void modifyPerson(Persoana p) throws SQLException {
		Statement myStatement=null;
		try {
			myStatement=myConnection.createStatement();
			myStatement.executeUpdate("UPDATE persoane SET nume='"+p.getNume()+"' , varsta="+p.getVarsta()+" WHERE id="+p.getId()+";");
		}finally{
			close(myStatement,null);
		}
	}
	
	public void deletePerson(Persoana p) throws SQLException {
		Statement myStatement=null;
		try {
			myStatement=myConnection.createStatement();
			myStatement.executeUpdate("DELETE FROM persoane WHERE id="+p.getId()+";");
		}finally {
			close(myStatement,null);
		}
	}
	
	public List<Persoana> searchPersoanaList(String name) throws Exception {
		List<Persoana> list = new ArrayList<>();

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			name += "%";
			myStmt = myConnection.prepareStatement("select * from persoane where nume like ?");
			
			myStmt.setString(1, name);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				Persoana tempPers = convertRowToPersoana(myRs);
				list.add(tempPers);
			}
			
			return list;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	public Persoana searchPersoana(String name) throws Exception {
		Persoana tempPers=new Persoana(null,null,null);

		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			name += "%";
			myStmt = myConnection.prepareStatement("select * from persoane where nume like ?");
			
			myStmt.setString(1, name);
			
			myRs = myStmt.executeQuery();
			
			while (myRs.next()) {
				tempPers = convertRowToPersoana(myRs);
			}
			
			return tempPers;
		}
		finally {
			close(myStmt, myRs);
		}
	}
	
	
	
	private static void close(Connection myConnection,Statement myStatement, ResultSet myRs) throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStatement != null) {
			myStatement.close();
		}
		
		if (myConnection != null) {
			myConnection.close();
		}
	}
	
	private static void close(Statement myStatement, ResultSet myRs) throws SQLException {
		close(null,myStatement,myRs);
	}
	
	private static Persoana convertRowToPersoana(ResultSet myRs) throws SQLException
	{
		String id=myRs.getString("id");
		String nume=myRs.getString("nume");
		String varsta=myRs.getString("varsta");
		
		Persoana tempPers=new Persoana(id,nume,varsta);
		
		return tempPers;
	}
	
}
