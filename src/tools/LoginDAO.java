package tools;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

public class LoginDAO extends Connector
{
	
	public LoginDAO()
	{
		//blank
	}
	
	public int checkCredentials(String username, String password)
	{
		String sql;
        ResultSet rs;
        sql="SELECT * FROM usuarios WHERE username = ?";
        
        try
        {
        	this.connect();
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setString(1, username);
            rs = st.executeQuery();
            
            //if there is an account with that name
            if (rs.next())
            {
            	//check password
            	String hpassword = Hasher.encrypt_SHA_512(password);
            	
            	//same password?
            	if (hpassword.equals(rs.getString("password")))
            	{
        			//right password
            		System.out.println("LOGGING AS " + username);
        	    	
                    if (rs.getInt("estado") == 0) //disabled account?
                        return -1;
                    else
                    	return rs.getInt("perfil"); //admin or user?
            	}
            	
            	//wrong password
            	else 
            		return 0;
            }
            
            //there was no account with such name
            else
            	return 0;
        }
        catch(Exception e)
        {
        	System.out.println("Error LoginDAO::testCredentials(): " + e.toString());
        }
        finally
        {
        	this.disconnect();
        }
        
        return 0;
	}
	
}
