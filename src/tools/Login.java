package tools;

public class Login
{
	private LoginDAO logon;
	
	public Login()
	{
		logon = new LoginDAO();
	}
	
	public String testCredentials(String username, String password)
    {
        //blanks
        if (username.equals("") || password.equals(""))
        	return "INGRESE CREDENCIALES";
        
        //determinando si la cuenta existe, y que rol posee
        int rol = logon.checkCredentials(username, password);        
        
        if (rol == -1)
            return "CUENTA DESHABILITADA";
        
        else if (rol == 0)
            return"CREDENCIALES INVALIDAS";

        else if (rol == 1)
    		return "USUARIO";
        
        else if (rol == 2)
        	return "ADMIN";
        
        else if (rol == 99)
            return "ERROR AL CONECTARSE A LA BASE DE DATOS";
        
        else
        	return "UNDEFINED ERROR";
    }
}

