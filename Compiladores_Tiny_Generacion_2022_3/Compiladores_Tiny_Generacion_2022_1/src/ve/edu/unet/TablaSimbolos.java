package ve.edu.unet;

import ve.edu.unet.nodosAST.*;

import java.util.*;




public class TablaSimbolos {
	private ArrayList<HashMap<String, RegistroSimbolo>> secciones;
	private HashMap<String, RegistroSimbolo> tabla;
	private int direccion;  //Contador de las localidades de memoria asignadas a la tabla
	
	public TablaSimbolos() {
		super();
		tabla = new HashMap<String, RegistroSimbolo>();
		direccion=0;
	}

	public void cargarTabla(NodoBase raiz) throws IdNotFoundException, VectorAlreadyDeclared{
		while (raiz != null) {
	    if (raiz instanceof NodoIdentificador){
	    	InsertarSimbolo(((NodoIdentificador)raiz).getNombre(),-1);
	    	//TODO: A�adir el numero de linea y localidad de memoria correcta
	    }

	    /* Hago el recorrido recursivo */
	    if (raiz instanceof  NodoIf){
	    	cargarTabla(((NodoIf)raiz).getPrueba());
	    	cargarTabla(((NodoIf)raiz).getParteThen());
	    	if(((NodoIf)raiz).getParteElse()!=null){
	    		cargarTabla(((NodoIf)raiz).getParteElse());
	    	}
	    }
	    else if (raiz instanceof  NodoRepeat){
	    	cargarTabla(((NodoRepeat)raiz).getCuerpo());
	    	cargarTabla(((NodoRepeat)raiz).getPrueba());
	    }
		else if (raiz instanceof  NodoFor) {
			cargarTabla(((NodoFor) raiz).getPrueba());
			cargarTabla(((NodoFor) raiz).getCuerpo());
		}
	    else if (raiz instanceof  NodoAsignacion)
	    	cargarTabla(((NodoAsignacion)raiz).getExpresion());
	    else if (raiz instanceof  NodoEscribir)
	    	cargarTabla(((NodoEscribir)raiz).getExpresion());
	    else if (raiz instanceof NodoOperacion){
	    	cargarTabla(((NodoOperacion)raiz).getOpIzquierdo());
	    	cargarTabla(((NodoOperacion)raiz).getOpDerecho());
	    }
		else if(raiz instanceof NodoVector) {
			NodoVector vector = (NodoVector) raiz;
			boolean inserto = false;
			if (vector.isDeclaracion()) {
				cargarTabla(vector.getExpresion());
				int direccionesReservadas = ((NodoValor) vector.getExpresion()).getValor();
				if (BuscarSimbolo(((NodoIdentificador) vector.getIdentificador()).getNombre()) == null) {
					InsertarSimbolo(((NodoIdentificador) vector.getIdentificador()).getNombre(),-1);
					this.direccion += direccionesReservadas - 1;
				} else {
					throw new VectorAlreadyDeclared("El vector " + ((NodoIdentificador) vector.getIdentificador()).getNombre() + " ya esta declarado");
				}
			} else {
				String identificador = ((NodoIdentificador) vector.getIdentificador()).getNombre();
				RegistroSimbolo simbolo = BuscarSimbolo(identificador);
				if (simbolo == null) {
					throw new IdNotFoundException("El vector " + ((NodoIdentificador) vector.getIdentificador()).getNombre() + " no esta esta declarado");
				}
				cargarTabla(vector.getExpresion());
			}
		}
	    raiz = raiz.getHermanoDerecha();
	  }
	}
	
	//true es nuevo no existe se insertara, false ya existe NO se vuelve a insertar 
	public boolean InsertarSimbolo(String identificador, int numLinea){
		RegistroSimbolo simbolo;
		if(tabla.containsKey(identificador)){
			return false;
		}else{
			simbolo= new RegistroSimbolo(identificador,numLinea,direccion++);
			tabla.put(identificador,simbolo);
			return true;			
		}
	}
	
	public RegistroSimbolo BuscarSimbolo(String identificador){
		RegistroSimbolo simbolo=(RegistroSimbolo)tabla.get(identificador);
		return simbolo;
	}
	
	public void ImprimirClaves(){
		System.out.println("*** Tabla de Simbolos ***");
		for( Iterator <String>it = tabla.keySet().iterator(); it.hasNext();) { 
            String s = (String)it.next();
	    System.out.println("Consegui Key: "+s+" con direccion: " + BuscarSimbolo(s).getDireccionMemoria());
		}
	}

	public int getDireccion(String Clave, int numLinea){
		return BuscarSimbolo(Clave).getDireccionMemoria();
	}

	public RegistroSimbolo BuscarSimbolo(String identificador, int bloque){
		HashMap<String, RegistroSimbolo> seccion;
		try {
			// si la seccion existe
			seccion = secciones.get(bloque);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		RegistroSimbolo simbolo = (RegistroSimbolo)seccion.get(identificador);
		return simbolo;
	}

	public void ImprimirTabla(){
		System.out.println("*** Tabla de Simbolos ***");
		for(int i = 0; i<secciones.size(); i++){
			for (String s : secciones.get(i).keySet()) {
				System.out.println("Bloque: " + i + " Nombre: "+ s + " Con direccion: " + BuscarSimbolo(s,i).getDireccionMemoria());
			}
		}

	}

	/*
	 * TODO:
	 * 1. Crear lista con las lineas de codigo donde la variable es usada.
	 * */

	public class VectorAlreadyDeclared extends Exception {

		public VectorAlreadyDeclared(String message) {
			super(message);
		}

		public VectorAlreadyDeclared(String message, Throwable cause) {
			super(message, cause);
		}
	}

	public class IdNotFoundException extends Exception {

		public IdNotFoundException(String message) {
			super(message);
		}

		public IdNotFoundException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
