import java.sql.Connection;


public class InternetDownloader
{
	
	protected  Connection conn;
	
	public void init()
	{
		try
		{
			getClass().getClassLoader().loadClass("com.mysql.jdbc.Driver");
			conn = java.sql.DriverManager.getConnection("");
		}catch(Exception e){ e.printStackTrace();}
	}
	
	public void iteration()
	{
		
	}
}





