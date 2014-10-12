package edu.sjsu.cmpe282.dao;

import java.sql.*;

import edu.sjsu.cmpe282.dto.*;

/**
 * @author madhuajeeth
 *
 */
public class UserDAO 
{
	private Connection connect;
	private Statement stmt;
	private ResultSet rs;

	// Constructor with JDBC connection
	public UserDAO() 
	{
		try
		{
			try
			{
				Class.forName("com.mysql.jdbc.Driver");
			}
			catch (ClassNotFoundException e)
			{
				System.out.println("Unable to find MySQL JDBC Driver");
				e.printStackTrace();
			}
		connect = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/cmpe282","root","July@2787");
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
	}
	
	public boolean addUser(User user) 
	{
		try
		{
			stmt = connect.createStatement();
			String lastname = user.getLastName();
			if(lastname.equals("admin"))
			{
				 final String accountType = "admin";
				 user.setaccType(accountType);
				 String query = "INSERT INTO `cmpe282`.`users` (`acctype`,`firstname`, `lastname`, `email`, `passwd`) VALUES ('" + accountType + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getMailId() + "', '" + user.getPasswd() + "');";
				 stmt.executeUpdate(query);
			}else
			{
				 final String accountType = "user";
				 user.setaccType(accountType);
				 String query = "INSERT INTO `cmpe282`.`users` (`acctype`,`firstname`, `lastname`, `email`, `passwd`) VALUES ('" + accountType + "', '" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.getMailId() + "', '" + user.getPasswd() + "');";
				 stmt.executeUpdate(query);
			}
		}
		catch (SQLException se)
		{
			se.printStackTrace();
		}
		return true;
	}
	
	public boolean checkUser(User user)
	{
		String origPasswd = null;
		try{
			stmt = connect.createStatement();
			String query = "Select passwd from cmpe282.users where email = '"+ user.getMailId() +"';";
			rs = stmt.executeQuery(query);
			rs.next();
			origPasswd = rs.getString("passwd");
		}
		catch (SQLException se){
			se.printStackTrace();			
		}
		return user.getPasswd().equals(origPasswd);
	}
	
//	finally{
//		try {
//			stmt.close();
//			connect.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//     }

}
