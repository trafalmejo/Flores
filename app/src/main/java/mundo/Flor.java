package mundo;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by GILBERTO on 28/02/2016.
 */
public class Flor
{
    private String imagenURL;
    private File imagen;
    private Categoria categoria;

    public Flor(String url, Categoria cat ) {
        this.imagenURL = url;
        if(!imagenURL.equals("")){
            imagen = new File(imagenURL);
        }
        this.categoria = cat;

    }


    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public File getImagen() {
        return imagen;
    }

    public void setImagen(File imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
