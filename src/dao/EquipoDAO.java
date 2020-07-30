package dao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.Equipo;
import tools.*;

public class EquipoDAO extends Connector 
{
	public String borrarEquipo(String codigo) 
	{
		if(getEquipo(codigo) == null)
			return "Equipo no existe";
		
		try
        {
        	this.connect();
            String sql="UPDATE equipos SET estado = 0  WHERE codigo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setString(1, codigo);
            
            st.executeUpdate();
            
            //TODO
            //RETURN THE VALUES
        }
        catch (Exception ex)
        {
            System.out.println("Error EquipoDAO::borrarEquipo(): " + ex.toString());
            return "Error al borrar equipo";
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
		
		return "Equipo eliminado con exito";
	}
	
	public String editarEquipo(Equipo equipo)
	{
		if(getEquipo(equipo.getCodigo()) == null)
			return "Equipo no existe";
		if (equipo.getCantidad() < 0)
			return "Cantidad invalida";
		
		try
        {
        	this.connect();
            String sql="UPDATE equipos SET descripcion = ?, cantidad = ? WHERE codigo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, equipo.getDescripcion());
            st.setInt	(2, equipo.getCantidad());
            st.setString(3, equipo.getCodigo());
            
            st.executeUpdate();
        }
        catch (Exception ex)
        {
            System.out.println("Error UsuarioDAO::editarEquipo(): " + ex.toString());
            return "Error al editar equipo";
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
		
		return "Equipo editado con exito";
	}
	
	public String crearEquipo(Equipo equipo)
	{
		if (getEquipo(equipo.getCodigo()) != null) //codigo ya existe
			return "Codigo ya existe";
		if (equipo.getCantidad() <= 0)
			return "Cantidad invalida";
		
		
		try
        {
           	this.connect();
            String sql="INSERT INTO equipos VALUES (?,?,?,?,1)";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, null);
            st.setString(2, equipo.getCodigo());
            st.setString(3, equipo.getDescripcion());
            st.setInt	(4, equipo.getCantidad());

            st.executeUpdate();
            
        }
        catch (Exception ex)
        {
            System.out.println("Error EquipoDAO::crearEquipo(): " + ex.toString());
            return "Error al crear equipo";
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
		
		return "Equipo creado con exito";
	}
	
	public Equipo getEquipo(String codigo)
	{
		Equipo equipo;
		
		try
        {
            this.connect();
            String sql="SELECT * FROM equipos WHERE estado = 1 and codigo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setString(1, codigo);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next())
            {
            	equipo = new Equipo();
            	
            	equipo.setIdEquipo(rs.getInt("idequipo"));
            	equipo.setCodigo(rs.getString("codigo"));
            	equipo.setDescripcion(rs.getString("descripcion"));
            	equipo.setCantidad(rs.getInt("cantidad"));
            	
                return equipo;
            }
            else
            	return null;
        }
        catch (Exception ex)
        {
            System.out.println("Error EquipoDAO::getEquipo(): " + ex.toString());
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
		
		return null;
	}
	
	public Equipo getEquipo(int id)
	{
		Equipo equipo;
		
		try
        {
            this.connect();
            String sql="SELECT * FROM equipos WHERE estado = 1 and idequipo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next())
            {
            	equipo = new Equipo();
            	
            	equipo.setCodigo(rs.getString("codigo"));
            	equipo.setDescripcion(rs.getString("descripcion"));
            	equipo.setCantidad(rs.getInt("cantidad"));
            	
                return equipo;
            }
            else
            	return null;
        }
        catch (Exception ex)
        {
            System.out.println("Error EquipoDAO::getEquipo(): " + ex.toString());
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
		
		return null;
	}
	
	public ArrayList<Equipo> getEquipos()
	{
		ArrayList<Equipo> list = new ArrayList<>();
		
		try
        {
            this.connect();
            String sql="SELECT * FROM equipos WHERE estado = 1";
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
            	Equipo equipo = new Equipo();
            	
            	equipo.setIdEquipo(rs.getInt("idequipo"));
            	equipo.setCodigo(rs.getString("codigo"));
            	equipo.setDescripcion(rs.getString("descripcion"));
            	equipo.setCantidad(rs.getInt("cantidad"));
            	
                list.add(equipo);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error EquipoDAO::getEquipos(): " + ex.toString());
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
