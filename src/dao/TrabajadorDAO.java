package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dto.Trabajador;
import tools.Connector;

public class TrabajadorDAO extends Connector
{
	public String borrarTrabajador(String run) 
	{
		if(getTrabajador(run) == null)
			return "Trabajador no existe";
		
		try
        {
        	this.connect();
            String sql="UPDATE trabajadores SET estado = 0  WHERE run = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setString(1, run);
            
            st.executeUpdate();
        }
        catch (Exception ex)
        {
            System.out.println("Error TrabajadorDAO::borrarTrabajador(): " + ex.toString());
            return "Error al borrar trabajador";
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
		
		return "Trabajador eliminado con exito";
	}
	
	public String editarTrabajador(Trabajador trabajador)
	{
		if(getTrabajador(trabajador.getRun()) == null)
			return "Trabajador no existe";
		
		try
        {
        	this.connect();
            String sql="UPDATE trabajadores SET nombre = ?, appat = ?, apmat = ?, empresa = ?, correo = ?, telefono = ? WHERE run = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, trabajador.getNombre());
            st.setString(2, trabajador.getApPat());
            st.setString(3, trabajador.getApMat());
            st.setString(4, trabajador.getEmpresa());
            st.setString(5, trabajador.getCorreo());
            st.setString(6, trabajador.getTelefono());
            st.setString(7, trabajador.getRun());
            
            st.executeUpdate();
        }
        catch (Exception ex)
        {
            System.out.println("Error TrabajadorDAO::editarTrabajador(): " + ex.toString());
            return "Error al editar trabajador";
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
		
		return "Trabajador editado con exito";
	}
	
	public String crearTrabajador(Trabajador trabajador)
	{
		if (getTrabajador(trabajador.getRun()) != null) //codigo ya existe
			return "Trabajador con dicho run ya existe";
		if (trabajador.getRun().equals("") || trabajador.getRun() == null)
			return "El run no puede estar el blanco";
		
		try
        {
           	this.connect();
            String sql="INSERT INTO trabajadores VALUES (?,?,?,?,?,?,?,?,1)";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, null);
            st.setString(2, trabajador.getNombre());
            st.setString(3, trabajador.getApPat());
            st.setString(4, trabajador.getApMat());
            st.setString(5, trabajador.getRun());
            st.setString(6, trabajador.getEmpresa());
            st.setString(7, trabajador.getCorreo());
            st.setString(8, trabajador.getTelefono());
            
            st.executeUpdate();
            
        }
        catch (Exception ex)
        {
            System.out.println("Error TrabajadorDAO::crearTrabajador(): " + ex.toString());
            return "Error al crear trabajador";
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
		
		return "Trabajador creado con exito";
	}
	
	public Trabajador getTrabajador(int id)
	{
		Trabajador trabajador;
		
		try
        {
            this.connect();
            String sql="SELECT * FROM trabajadores WHERE estado = 1 and idtrabajador = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next())
            {
            	trabajador = new Trabajador();
            	
            	trabajador.setIdTrabajador(rs.getInt("idtrabajador"));
            	trabajador.setNombre(rs.getString("nombre"));
            	trabajador.setApPat(rs.getString("appat"));
            	trabajador.setApMat(rs.getString("apmat"));
            	trabajador.setRun(rs.getString("run"));
            	trabajador.setEmpresa(rs.getString("empresa"));
            	trabajador.setCorreo(rs.getString("correo"));
            	trabajador.setTelefono(rs.getString("telefono"));
            	
                return trabajador;
            }
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
	
	public Trabajador getTrabajador(String run)
	{
		Trabajador trabajador;
		
		try
        {
            this.connect();
            String sql="SELECT * FROM trabajadores WHERE estado = 1 and run = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setString(1, run);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next())
            {
            	trabajador = new Trabajador();
            	
            	trabajador.setIdTrabajador(rs.getInt("idtrabajador"));
            	trabajador.setNombre(rs.getString("nombre"));
            	trabajador.setApPat(rs.getString("appat"));
            	trabajador.setApMat(rs.getString("apmat"));
            	trabajador.setRun(rs.getString("run"));
            	trabajador.setEmpresa(rs.getString("empresa"));
            	trabajador.setCorreo(rs.getString("correo"));
            	trabajador.setTelefono(rs.getString("telefono"));
            	
                return trabajador;
            }
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
	
	public ArrayList<Trabajador> getTrabajadores()
	{
		ArrayList<Trabajador> list = new ArrayList<>();
		
		try
        {
            this.connect();
            String sql="SELECT * FROM trabajadores WHERE estado = 1";
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
        		Trabajador trabajador = new Trabajador();
            	
            	trabajador.setIdTrabajador(rs.getInt("idtrabajador"));
            	trabajador.setNombre(rs.getString("nombre"));
            	trabajador.setApPat(rs.getString("appat"));
            	trabajador.setApMat(rs.getString("apmat"));
            	trabajador.setRun(rs.getString("run"));
            	trabajador.setEmpresa(rs.getString("empresa"));
            	trabajador.setCorreo(rs.getString("correo"));
            	trabajador.setTelefono(rs.getString("telefono"));
            	
                list.add(trabajador);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error TrabajadorDAO::getTrabajador(): " + ex.toString());
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
