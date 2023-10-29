package ve.edu.unet;

public class RegistroSimbolo {
	private String identificador;
	private int NumLinea;
	private int DireccionMemoria;
	private String tipo;
	private int size;
	
	public RegistroSimbolo(String identificador, int numLinea,
			int direccionMemoria, String tipo, int size) {
		super();
		this.identificador = identificador;
		this.NumLinea = numLinea;
		this.DireccionMemoria = direccionMemoria;
		this.tipo = tipo;
		this.size = size;
	}

	public RegistroSimbolo(String identificador, int numLinea,
			int direccionMemoria) {
		super();
		this.identificador = identificador;
		NumLinea = numLinea;
		DireccionMemoria = direccionMemoria;
	}

	public String getIdentificador() {
		return this.identificador;
	}

	public int getNumLinea() {
		return this.NumLinea;
	}

	public int getDireccionMemoria() {
		return this.DireccionMemoria;
	}

	public void setDireccionMemoria(int direccionMemoria) {
		this.DireccionMemoria = direccionMemoria;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getSize() {
		return this.size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}
