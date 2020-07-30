package dto;

public class Usuario 
{
	private int idUsuario;
	private String nombre;
	private String apPat;
	private String apMat;
	private String run;
	private int perfil;
	private String username;
	private String password;
	
	public Usuario()
	{
		
	}

	public Usuario(int idUsuario, String nombre, String apPat, String apMat, String run, int perfil, String username,
			String password) {
		super();
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.apPat = apPat;
		this.apMat = apMat;
		this.run = run;
		this.perfil = perfil;
		this.username = username;
		this.password = password;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
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

	public int getPerfil() {
		return perfil;
	}

	public void setPerfil(int perfil) {
		this.perfil = perfil;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
