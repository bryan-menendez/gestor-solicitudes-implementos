package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import dto.Equipo;
import dto.Prestamo;
import tools.Connector;

/*
 * los equipos asociados a cada prestamo son artificiales, y no representan los equipos reales en la base de datos
 * sino una tabla intermedia, que sirve para recordar cuales fueron los equipos solicitados en el prestamo
 * 
 * por esto se validan los equipos asociados al prestamo, vienen creados desde la informacion de la interfaz 
 */
public class PrestamoDAO extends Connector
{
	//TODO devolver prestamo
	public String devolverPrestamo(int id)
	{
		EquipoDAO equipodao = new EquipoDAO();
		Prestamo p = getPrestamo(id);
		
		if (p == null)
			return "Prestamo no existe";
		if (p.getDevuelto())
			return "Prestamo ya devuelto";
			
		try
		{
			this.connect();
			//return objects, restock pool
	        ArrayList<Equipo> equipos = getEquiposPrestamo(p.getIdPrestamo());
	        
	        for (Equipo e : equipos)
	        {
	        	String sql="UPDATE equipos SET cantidad = ? WHERE codigo = ? and estado = 1";
	            PreparedStatement st= this.connection.prepareStatement(sql);
	            
	            //cantidad en db + cantidad devuelta
	            st.setInt(1, equipodao.getEquipo(e.getCodigo()).getCantidad() + e.getCantidad());
	            st.setString(2, e.getCodigo());
	            
	            st.executeUpdate();
	        }
	        
	        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            String date = dtf.format(now);
	        
			//flag prestamo as returned
	        String sql="UPDATE prestamos SET devuelto = 1, fechadevolucion = ? WHERE idprestamo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, date);
            st.setInt(2, id);
	        
            st.executeUpdate();
		}
		catch (Exception ex)
        {
            System.out.println("Error PrestamoDAO::devolverPrestamo(): " + ex.toString());
            return "Error al devolver prestamo";
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
		
		return "Prestamo devuelto con exito";
	}
	
	public String borrarPrestamo(int id) 
	{
		EquipoDAO equipodao = new EquipoDAO();
		
		if(getPrestamo(id) == null)
			return "Prestamo no existe";
		
		try
        {
        	this.connect();
        	//delete id
            String sql="UPDATE prestamos SET estado = 0  WHERE idprestamo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setInt(1, id);
            
            st.executeUpdate();
            
            //return objects, restock pool
	        ArrayList<Equipo> equipos = getEquiposPrestamo(id);
	        
	        for (Equipo e : equipos)
	        {
	        	sql="UPDATE equipos SET cantidad = ? WHERE codigo = ? and estado = 1";
	            st= this.connection.prepareStatement(sql);
	            
	            //cantidad en db + cantidad devuelta
	            st.setInt(1, equipodao.getEquipo(e.getCodigo()).getCantidad() + e.getCantidad());
	            st.setString(2, e.getCodigo());
	            
	            st.executeUpdate();
	        }
            
            //delete related entries
            sql="UPDATE equipos_prestamo SET estado = 0 WHERE idPrestamo_FK = ?";
            st= this.connection.prepareStatement(sql);
            st.setInt(1, id);
            
            st.executeUpdate();
        }
        catch (Exception ex)
        {
            System.out.println("Error PrestamoDAO::borrarPrestamo(): " + ex.toString());
            return "Error al borrar prestamo";
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
		
		return "Prestamo eliminado con exito";
	}
	
	public String editarPrestamo(Prestamo prestamo)
	{
    	EquipoDAO equipodao = new EquipoDAO();
		
		if (getPrestamo(prestamo.getIdPrestamo()) == null)
			return "Prestamo no existe";
		if (prestamo.getEquipos().isEmpty())
			return "El prestamo no posee equipos";
		
		for (Equipo e : prestamo.getEquipos())
		{
			if (e.getCantidad() > equipodao.getEquipo(e.getCodigo()).getCantidad())
				return "Ha solicitado mas equipos de los que hay disponibles: equipo codigo " + e.getCodigo();
		}
		
		try
        {
        	this.connect();
        	
        	//update prestamo
            String sql="UPDATE prestamos SET descripcion = ?, trabajador_fk = ?, devuelto = ? WHERE idprestamo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, prestamo.getDescripcion());
            st.setInt	(2, prestamo.getTrabajadorFK());
            st.setBoolean(3, prestamo.getDevuelto());
            st.setInt	(4, prestamo.getIdPrestamo());
            
            st.executeUpdate();
            
            //TODO
            //put this FOR loop in its own method 
            
            //re-stock/return stuff before deleting it
            ArrayList<Equipo> equipos = getEquiposPrestamo(prestamo.getIdPrestamo());
            
            for (Equipo e : equipos)
            {
            	sql="UPDATE equipos SET cantidad = ? WHERE codigo = ? and estado = 1";
                st= this.connection.prepareStatement(sql);
                
                //cantidad en db + cantidad devuelta
                st.setInt(1, equipodao.getEquipo(e.getCodigo()).getCantidad() + e.getCantidad());
                st.setString(2, e.getCodigo());
                
                st.executeUpdate();
            }
            
            //delete old entries
            sql="UPDATE equipos_prestamo SET estado = 0 WHERE idPrestamo_FK = ?";
            st= this.connection.prepareStatement(sql);
            
            st.setInt(1, prestamo.getIdPrestamo());
            
            st.executeUpdate();
            
            //insert new entries
            for (Equipo e : prestamo.getEquipos())
            {
            	sql="INSERT INTO equipos_prestamo VALUES (?,?,?,1)";
                st= this.connection.prepareStatement(sql);
                
                st.setInt(1, e.getCantidad());
                st.setInt(2, equipodao.getEquipo(e.getCodigo()).getIdEquipo());
                st.setInt(3, prestamo.getIdPrestamo());
                
                st.executeUpdate();
            }
            
            //update current stock
            for (Equipo e : prestamo.getEquipos())
            {
            	sql="UPDATE equipos SET cantidad = ? WHERE codigo = ? and estado = 1";
                st= this.connection.prepareStatement(sql);
                
                //cantidad actual - cantidad solicitada
                st.setInt(1, equipodao.getEquipo(e.getCodigo()).getCantidad() - e.getCantidad());
                st.setString(2, e.getCodigo());
                
                st.executeUpdate();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error PrestamoDAO::editarPrestamo(): " + ex.toString());
            return "Error al editar prestamo";
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
		
		return "Prestamo editado con exito";
	}
	
	public String crearPrestamo(Prestamo prestamo)
	{
		EquipoDAO equipodao = new EquipoDAO();
		
		if (getPrestamo(prestamo.getIdPrestamo()) != null)
			return "Prestamo ya existe";
		if (prestamo.getEquipos().isEmpty())
			return "El prestamo no posee equipos";
		
		try
        {
        	this.connect();
        	
        	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            String date = dtf.format(now);  
            
        	//add prestamo
            String sql="INSERT INTO prestamos VALUES (null,?,null,?,?,0,1)";
            PreparedStatement st= this.connection.prepareStatement(sql);
            
            st.setString(1, date);
            st.setString(2, prestamo.getDescripcion());
            st.setInt	(3, prestamo.getTrabajadorFK());
            
            st.executeUpdate();
            
            //get the value of the new .idprestamo, since you dont have it
            sql="SELECT idprestamo FROM prestamos ORDER BY idprestamo DESC LIMIT 1";
            st= this.connection.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next())
            	prestamo.setIdPrestamo(rs.getInt("idprestamo"));
            
            
            //insert new entries
            for (Equipo e : prestamo.getEquipos())
            {
            	sql="INSERT INTO equipos_prestamo VALUES (?,?,?,1)";
                st= this.connection.prepareStatement(sql);
                
                st.setInt(1, e.getCantidad());
                st.setInt(2, equipodao.getEquipo(e.getCodigo()).getIdEquipo());
                st.setInt(3, prestamo.getIdPrestamo());
                
                st.executeUpdate();
            }
            
            //update current stock
            for (Equipo e : prestamo.getEquipos())
            {
            	sql="UPDATE equipos SET cantidad = ? WHERE codigo = ?";
                st= this.connection.prepareStatement(sql);
                
                //cantidad actual - cantidad solicitada
                st.setInt(1, equipodao.getEquipo(e.getCodigo()).getCantidad() - e.getCantidad());
                st.setString(2, e.getCodigo());
                
                st.executeUpdate();
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error PrestamoDAO::crearPrestamo(): " + ex.toString());
            ex.printStackTrace();
            return "Error al crear prestamo";
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
		
		return "Prestamo creado con exito";
	}
	
	public ArrayList<Equipo> getEquiposPrestamo(int id)
	{
		EquipoDAO equipodao = new EquipoDAO();
		
		try
		{
			if (this.connection == null)
				this.connect();
			
			//obteniendo equipos asociados al prestamo
	    	String sql2="SELECT * FROM equipos_prestamo WHERE estado = 1 and idprestamo_fk = ?";
	        PreparedStatement st2 = this.connection.prepareStatement(sql2);
	        st2.setInt(1, id);
	        
	        ResultSet rs2 = st2.executeQuery(); 
	        //esta tabla de resulados solo posee las foraneas (idequipo_FK), necesito el resto
	        //pero no tengo ganas de hacer un join, podria romper algo
	        
	        //obteniendo informacion de los equipos para no hacer joins lmao
	        ArrayList<Equipo> equipos = new ArrayList<>();
	        while(rs2.next())
	        {
	        	Equipo e = equipodao.getEquipo(rs2.getInt("idequipo_fk")); //quedo compleja la idea
	        	
	        	if (e != null)
	        	{
	        		e.setCantidad(rs2.getInt("cantidad")); //este no es el equipo real, es el dato de la tabla intermedia	        
		        	equipos.add(e); 
	        	}
	        }	
	        
	        return equipos;
		}
		catch(Exception ex)
		{
            System.out.println("Error PrestamoDAO::getEquiposPrestamo(): " + ex.toString());
            ex.printStackTrace();
		}
		
		return null;
	}
	
	public Prestamo getPrestamo(int id)
	{
		Prestamo prestamo;
		
		if (id == 0)
			return null;
		
		try
        {
            this.connect();
            String sql="SELECT * FROM prestamos WHERE estado = 1 and idprestamo = ?";
            PreparedStatement st= this.connection.prepareStatement(sql);
            st.setInt(1, id);
            
            ResultSet rs = st.executeQuery();
            
            if (rs.next())
            {
            	prestamo = new Prestamo();
            	
            	prestamo.setIdPrestamo(rs.getInt("idprestamo"));
            	prestamo.setFechaPrestamo(rs.getString("fechaprestamo"));
            	prestamo.setFechaDevolucion(rs.getString("fechadevolucion"));
            	prestamo.setDescripcion(rs.getString("descripcion"));
            	prestamo.setTrabajadorFK(rs.getInt("trabajador_FK"));
            	prestamo.setDevuelto(rs.getBoolean("devuelto"));
                prestamo.setEquipos(getEquiposPrestamo(id));
            	
                return prestamo;
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error PrestamoDAO::getPrestamo(): " + ex.toString());
        }
		/*
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
		*/
		
		return null;
	}
	
	public ArrayList<Prestamo> getPrestamos()
	{
		ArrayList<Prestamo> list = new ArrayList<>();
		
		try
        {
            this.connect();
            String sql="SELECT * FROM prestamos WHERE estado = 1";
            PreparedStatement st= this.connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            while(rs.next())
            {
            	Prestamo prestamo = new Prestamo();
            	
            	prestamo.setIdPrestamo(rs.getInt("idprestamo"));
            	prestamo.setFechaPrestamo(rs.getDate("fechaprestamo").toString());
            	prestamo.setFechaDevolucion(rs.getString("fechadevolucion"));
            	prestamo.setDescripcion(rs.getString("descripcion"));
            	prestamo.setTrabajadorFK(rs.getInt("trabajador_FK"));
            	prestamo.setDevuelto(rs.getBoolean("devuelto"));
                prestamo.setEquipos(getEquiposPrestamo(rs.getInt("idprestamo")));
            	
                list.add(prestamo);
            }
        }
        catch (Exception ex)
        {
            System.out.println("Error PrestamoDAO::getPrestamos(): " + ex.toString());
            ex.printStackTrace();
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
