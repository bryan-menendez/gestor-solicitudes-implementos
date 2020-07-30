package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;



import dto.Usuario;
import tools.*;

public class UsuarioDAO extends Connector 
{
	/*
	 * los metodos de aqui deben recibir la contraseña en texto plano
	 * ya que realizaran el hasheo aqui
	 */
	
	public String borrarUsuario(String username)
	{
		if(getUsuario(username) == null)
			return "Usuario no existe";
		
		try
        {
        	this.connect();
            String sql="UPDATE usuarios SET estado = 0 WHERE username = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setString(1, username);
            
            st.executeUpdate();
        }
        catch (Exception ex)
        {
            System.out.println("Error UsuarioDAO::borrarUsuario(): " + ex.toString());
            return "Error al borrar usuario";
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
                return "Error: disconnect";
            }
        }
		
		return "Usuario eliminado con exito";
	}
	
	public String editarUsuario(Usuario user)
	{
		if (getUsuario(user.getUsername()) == null)
			return "Usuario no existe";
		if (user.getPassword().equals("") || user.getPassword() == null)
			return "Contraseña invalida";
		if (user.getPerfil() != 1 && user.getPerfil() != 2)
			return "Perfil invalido";
		
		try
        {
        	this.connect();
            String sql="UPDATE usuarios SET nombre = ?, appat = ?, apmat = ?, run = ?, perfil = ?, password = ? WHERE username = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, user.getNombre());
            st.setString(2, user.getApPat());
            st.setString(3, user.getApMat());
            st.setString(4, user.getRun());
            st.setInt(   5, user.getPerfil());
            st.setString(6, Hasher.encrypt_SHA_512(user.getPassword()));
            st.setString(7, user.getUsername());
            
            st.executeUpdate();
        }
        catch (Exception ex)
        {
            System.out.println("Error UsuarioDAO::editarUsuario(): " + ex.toString());
            return "Error al editar usuario";
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
                return "Error: disconnect";
            }
        }
		
		return "Usuario editado con exito";
	}
	
	
	public String crearUsuario(Usuario user)
	{
		if (user.getPerfil() != 1 && user.getPerfil() != 2)
			return "Perfil invalido";
		if (user.getUsername().equals("") || user.getUsername() == null)
			return "Nombre de usuario invalido";
		if (user.getPassword().equals("") || user.getPassword() == null)
			return "Contraseña invalida";
		if(getUsuario(user.getUsername()) != null)
			return "Nombre de usuario esta en uso";
		
		try
        {
        	this.connect();
            String sql="INSERT INTO usuarios VALUES (?,?,?,?,?,?,?,?,1)";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, null);
            st.setString(2, user.getNombre());
            st.setString(3, user.getApPat());
            st.setString(4, user.getApMat());
            st.setString(5, user.getRun());
            st.setInt(   6, user.getPerfil());
            st.setString(7, user.getUsername());
            st.setString(8, Hasher.encrypt_SHA_512(user.getPassword()));

            st.executeUpdate();
        }
        catch (Exception ex)
        {
            System.out.println("Error UsuarioDAO::crearUsuario(): " + ex.toString());
            return "Error al crear usuario";
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
                return "Error: disconnect";
            }
        }
        
        return "Usuario creado con exito";
	}
	
	public Usuario getUsuario(String username)
	{
		Usuario usuario = new Usuario();
		
		try
        {
            this.connect();
            String sql="SELECT * FROM usuarios WHERE username = ? and estado = 1";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setString(1, username);
            
            ResultSet rs = st.executeQuery();
            
            if(rs.next())
            {
            	
            	usuario.setIdUsuario(rs.getInt("idusuario"));  
            	usuario.setNombre(rs.getString("nombre"));
            	usuario.setApPat(rs.getString("appat"));
            	usuario.setApMat(rs.getString("apmat"));
            	usuario.setRun(rs.getString("run"));
            	usuario.setPerfil(rs.getInt("perfil"));
            	usuario.setUsername(rs.getString("username"));
            	usuario.setPassword(rs.getString("password"));
            }
            else
            {
            	return null;
            }
            
        }
        catch (Exception ex)
        {
            System.out.println("Error UsuarioDAO::getUsuario(): " + ex.toString());
            return null;
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
                return null;
            }
        }
        
        return usuario;
	}

	
	public ArrayList<Usuario> getUsuarios()
    {
        ArrayList<Usuario> list = new ArrayList<>();
        
        try
        {
            this.connect();
            String sql="SELECT * FROM usuarios WHERE estado = 1";
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
            	Usuario usuario = new Usuario();
            	
            	usuario.setIdUsuario(rs.getInt("idusuario"));      
            	usuario.setNombre(rs.getString("nombre"));
            	usuario.setApPat(rs.getString("appat"));
            	usuario.setApMat(rs.getString("apmat"));
            	usuario.setRun(rs.getString("run"));
            	usuario.setPerfil(rs.getInt("perfil"));
            	usuario.setUsername(rs.getString("username"));
            	usuario.setPassword(rs.getString("password"));
            	
                list.add(usuario);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error UsuarioDAO::getUsuarios(): " + ex.toString());
        }
        finally
        {
            try
            {
                this.disconnect();
            }
            catch(Exception ex)
            {
                System.out.println("Error:disconnect");
                ex.printStackTrace();
                return list;
            }
        }
        
        return list;
    }
}
