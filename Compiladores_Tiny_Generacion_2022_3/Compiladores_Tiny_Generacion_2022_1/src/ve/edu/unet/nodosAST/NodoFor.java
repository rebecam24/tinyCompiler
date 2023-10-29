package ve.edu.unet.nodosAST;

public class NodoFor extends NodoBase {

    private NodoBase cuerpo;
    private NodoBase prueba;

    public NodoFor(NodoBase cuerpo, NodoBase prueba) {
        this.cuerpo = cuerpo;
        this.prueba = prueba;
    }

    public NodoBase getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(NodoBase cuerpo) {
        this.cuerpo = cuerpo;
    }

    public NodoBase getPrueba() {
        return prueba;
    }

    public void setPrueba(NodoBase prueba) {
        this.prueba = prueba;
    }
}