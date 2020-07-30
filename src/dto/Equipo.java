package dto;

public class Equipo 
{
	private int idEquipo;
	private String codigo;
	private String descripcion;
	private int cantidad;
	
	public Equipo()
	{
		//blank
	}

	public Equipo(int idEquipo, String codigo, String descripcion, int cantidad) {
		super();
		this.idEquipo = idEquipo;
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

	public int getIdEquipo() {
		return idEquipo;
	}

	public void setIdEquipo(int idEquipo) {
		this.idEquipo = idEquipo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
