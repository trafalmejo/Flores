package mundo;

/**
 * Created by danielcastano on 3/25/16.
 */
public class Regalo {
    private String emisor;
    private String receptor;
    private String mensaje;

    public Regalo(String em, String re, String men){
        emisor = em;
        receptor = re;
        mensaje = men;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
