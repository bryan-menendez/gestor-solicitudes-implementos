package dto;

import java.util.ArrayList;

import dao.EquipoDAO;
import dao.TrabajadorDAO;

public class Prestamo 
{
	private int idPrestamo;
	private String fechaPrestamo;
	private String fechaDevolucion;
	private String descripcion;
	private ArrayList<Equipo> equipos;
 	private int trabajadorFK;
 	private boolean devuelto;
	private TrabajadorDAO trabajadordao;
	private EquipoDAO equipodao;
 	
 	public Prestamo()
 	{
 		//blank
 	}

	public Prestamo(int idPrestamo, String fechaPrestamo, String fechaDevolucion, String descripcion,
			ArrayList<Equipo> equipos, int trabajadorFK) {
		super();
		this.idPrestamo = idPrestamo;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
		this.descripcion = descripcion;
		this.equipos = equipos;
		this.trabajadorFK = trabajadorFK;
	}
	
	public void setDevuelto(boolean state){
		devuelto = state;
	}
	
	public boolean getDevuelto(){
		return devuelto;
	}

	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public String getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(String fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public String getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(String fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<Equipo> getEquipos() {
		return equipos;
	}

	public void setEquipos(ArrayList<Equipo> equipos) {
		this.equipos = equipos;
	}

	public int getTrabajadorFK() {
		return trabajadorFK;
	}

	public void setTrabajadorFK(int trabajadorFK) {
		this.trabajadorFK = trabajadorFK;
	}
 	
	@Override
	public String toString()
	{
		trabajadordao = new TrabajadorDAO();
		equipodao = new EquipoDAO();
		
		String str = "";
		
		str += "Prestamo numero " + getIdPrestamo() + "\r\n";
		
		Trabajador t = trabajadordao.getTrabajador(getTrabajadorFK());
		str += "Responsable: " + t.getNombre() + " " + t.getApPat() + " " + t.getApMat() + "\r\n";
		str += "Run: " + t.getRun() + "\r\n";
		
		str += "Solicitado a la fecha: " + getFechaPrestamo() + "\r\n";
		
		if (!getDevuelto())
			str += "Prestamo aun no ha sido devuelto \r\n";
		else
			str += "Devuelto a la fecha: " + getFechaDevolucion() + "\r\n";
		
		str += "Descripcion: " + getDescripcion() + "\r\n";
		str += "\r\nEquipos solicitados:\r\n";
		
		for (Equipo e : getEquipos())
		{
			str += "    " + e.getCantidad() + " x " + e.getCodigo() + " " + e.getDescripcion() + "\r\n";
		}
		
		return str;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 	
}
