package server;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager
{
	private Connection con;

	private Connection getConnection()
            throws SQLException, IOException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost:5432/bittorrent";
        String username = "postgres";
        String password = "postgres";

        return DriverManager.getConnection(url, username, password);
    }
    
	public DBManager (){}
	
	public void connect()
	{
		try
	    {
	    	con = getConnection();
	    	
	    	if (con != null)
	            System.out.println("Successful database connection");
	        else
	            System.out.println("Failed to make database connection");
	    }
	    catch (Exception e)
	    { e.printStackTrace(); }
	}
	
    public void createTables()
    {
        try
        {
        	Statement stmt = con.createStatement();
	        stmt.execute("CREATE TABLE users ( id SERIAL PRIMARY KEY, ipaddress CHAR(30), online BOOLEAN)");
	        stmt.execute("CREATE TABLE files ( user_id INTEGER, filename CHAR(100), part INTEGER)");
	        stmt.close();
        }
        catch (Exception e)
        { e.printStackTrace(); }
    }
    
    public int insertUser(String ipaddress)
    {
    	int id = -1;
        try
        {
        	String query = "INSERT INTO users VALUES (DEFAULT,?,TRUE);";
        	PreparedStatement pstmt = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        	pstmt.setString(1, ipaddress);
        	pstmt.executeUpdate();
        	
        	ResultSet keys = pstmt.getGeneratedKeys();
        	keys.next();
        	id = keys.getInt(1);
        	
        	pstmt.close();
	    }
        catch (Exception e)
        { e.printStackTrace(); }
        
        return id;
    }
    
    public void insertFile(int userId, String fileName, int part)
    {
    	try
        {
    		String query = "INSERT INTO files VALUES (?,?,?);";
        	PreparedStatement pstmt = con.prepareStatement(query);
        	
	        pstmt.setInt(1, userId);
	        pstmt.setString(2, fileName);
	        pstmt.setInt(3, part);
        	pstmt.execute();
	        
        	pstmt.close();
        }
        catch (Exception e)
        { e.printStackTrace(); }
    }
}
