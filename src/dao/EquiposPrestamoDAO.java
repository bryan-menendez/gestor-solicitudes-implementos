package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import dto.Equipo;
import tools.Connector;

public class EquiposPrestamoDAO extends Connector 
{
	public ArrayList<Equipo> getEquiposPrestamo()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        String date = dtf.format(now);  
        
		return getEquiposPrestamo("0000-00-00", date);

	}
	
	public ArrayList<Equipo> getEquiposPrestamo(String fechaInicio, String fechaLimite)
	{
		EquipoDAO equipodao = new EquipoDAO();
		
		try
		{
			this.connect();
			
			String sql="";
	        PreparedStatement st;
			
			//obteniendo prestamos que esten dentro del rango de fechas
	    	sql="SELECT idprestamo FROM prestamos WHERE fechaPrestamo >= ? and fechaPrestamo <= ? and estado = 1";
	        st = this.connection.prepareStatement(sql);
	        st.setString(1, fechaInicio);
	        st.setString(2, fechaLimite);
	        
	        ResultSet rs = st.executeQuery(); 
	        Map<Integer, Integer> mapEP = new HashMap<Integer, Integer>();
	        
	        //obteniendo los objetos de equipos_prestamo que pertenezcan a alguno de los prestamos encontrados  
	        while (rs.next())
	        {
	        	sql="SELECT cantidad, idEquipo_FK FROM equipos_prestamo WHERE idprestamo_fk = ? and estado = 1";
		        st = this.connection.prepareStatement(sql);
		        st.setInt(1, rs.getInt("idprestamo"));
		        
		        ResultSet rs2 = st.executeQuery();
		        
		        //for each entry found (might be several) add them together into a single counter
		        
		        while(rs2.next())
		        {
		        	//if key does not exists, add it 
		        	if (mapEP.get(rs2.getInt("idEquipo_FK")) == null)
		        		mapEP.put(rs2.getInt("idEquipo_FK") , 0);
		        	
		        	//then add the value
		        	//put: (equipofk, previousvalue + newvalue)
		        	mapEP.put(rs2.getInt("idequipo_fk"), mapEP.get(rs2.getInt("idequipo_fk")) + rs2.getInt("cantidad"));
		        }
	        }

	        Set<Entry<Integer, Integer>> setEP = mapEP.entrySet();
	        ArrayList<Equipo> equipos = new ArrayList<>();
	        
	        for (Entry<Integer, Integer> i : setEP )
	        {
	        	Equipo e = equipodao.getEquipo((int) i.getKey());
	        	e.setCantidad(i.getValue());
	        	
	        	equipos.add(e);
	        }
	        
	        return equipos;
		}
		catch(Exception ex)
		{
            System.out.println("Error EquiposPrestamoDAO::getEquiposPrestamo(): " + ex.toString());
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
                return null;
            }
        }
		
		return null;
	}
}
