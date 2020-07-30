package dto;

public class Trabajador 
{
	private int idTrabajador;
	private String nombre;
	private String apPat;
	private String apMat;
	private String run;
	private String empresa;
	private String correo;
	private String telefono;
	
	public Trabajador()
	{
		
	}

	public Trabajador(int idTrabajador, String nombre, String apPat, String apMat, String run, String empresa,
			String correo, String telefono) {
		super();
		this.idTrabajador = idTrabajador;
		this.nombre = nombre;
		this.apPat = apPat;
		this.apMat = apMat;
		this.run = run;
		this.empresa = empresa;
		this.correo = correo;
		this.telefono = telefono;
	}

	public int getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(int idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApPat() {
		return apPat;
	}

	public void setApPat(String apPat) {
		this.apPat = apPat;
	}

	public String getApMat() {
		return apMat;
	}

	public void setApMat(String apMat) {
		this.apMat = apMat;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
}
