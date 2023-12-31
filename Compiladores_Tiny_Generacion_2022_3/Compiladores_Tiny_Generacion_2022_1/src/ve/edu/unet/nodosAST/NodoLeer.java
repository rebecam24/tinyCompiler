package ve.edu.unet.nodosAST;

public class NodoLeer extends NodoBase {
	private String id;
	private NodoBase variable;
	public NodoLeer(String identificador) {
		super();
		this.id = identificador;
	}

	public NodoLeer() {
		super();
		id="";
	}

	public String getIdentificador() {
		return id;
	}

	public void setExpresion(String identificador) {
		this.id = identificador;
	}
	public NodoBase getVariable() {
		return variable;
	}
	public void setVariable(NodoBase variable) {
		this.variable = variable;
	}
}
