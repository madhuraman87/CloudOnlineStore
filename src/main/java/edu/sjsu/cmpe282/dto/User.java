package edu.sjsu.cmpe282.dto;

public class User 
{
	
	private String userId;
	private String accType;//admin = 0 & for others = 1
	private String firstName;
	private String lastName;
	private String mailId;
	private String passwd; 
	
	public User() 
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public User(String accType, String firstName, String lastName, String mailId, String passwd)
	{
		super();
		//this.accType = accType;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailId = mailId;
		this.passwd = passwd;
	}
	
	
	public String getaccType()
	{
		return accType;
	}
	public void setaccType(String accType)
	{
		this.accType = accType;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public String getMailId()
	{
		return mailId;
	}
	public void setMailId(String mailId)
	{
		this.mailId = mailId;
	}
	
	public String getPasswd()
	{
		return passwd;
	}
	public void setPasswd(String passwd)
	{
		this.passwd = passwd;
	}
	
	public String getUserId()
	{
		return userId;
	}
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
}
