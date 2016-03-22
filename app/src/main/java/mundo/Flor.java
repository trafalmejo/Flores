package mundo;

import android.graphics.Bitmap;

/**
 * Created by GILBERTO on 28/02/2016.
 */
public class Flor
{
    private String nombre;

    private String nombreCientifico;

    public Flor(String nombre, String nombreCientifico) {
        this.nombre = nombre;
        this.nombreCientifico = nombreCientifico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreCientifico() {
        return nombreCientifico;
    }

    public void setNombreCientifico(String nombreCientifico) {
        this.nombreCientifico = nombreCientifico;
    }

}
