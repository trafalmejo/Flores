package mundo;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;

import db.FloresDBHelper;

/**
 * Created by danielcastano on 3/2/16.
 */
public class Flock extends Application{

    private int contador;
    private ArrayList<Flor>  misFlores;


    private FloresDBHelper puente;
    private SQLiteDatabase datos;
    private ContentValues valores;


    public Flock(){
        misFlores = new ArrayList<Flor>();
        contador = 99;
 //       puente = new FloresDBHelper(this);
//        datos = puente.getReadableDatabase();
//        valores = new ContentValues();

    }
    public int getContador(){
        return contador;
    }
    public void setContador(int a){
        contador = a;
    }

    public void agregarFlorEncontrada(int id, Bitmap imagen){

    }




    public double compareImages(Bitmap imgA, Bitmap imgB)
    {
        Bitmap img2 = Bitmap.createScaledBitmap(imgB , imgA.getWidth(), imgA.getHeight(), true);
        Bitmap img1 = imgA;
        int width1 = img1.getWidth();
        int width2 = img2.getWidth();
        int height1 = img1.getHeight();
        int height2 = img2.getHeight();
        if ((width1 != width2) || (height1 != height2)) {
            System.err.println("Error: Images dimensions mismatch");
            System.exit(1);
        }
        long diff = 0;
        for (int y = 0; y < height1; y++) {
            for (int x = 0; x < width1; x++) {
                int rgb1 = img1.getPixel(x, y);
                int rgb2 = img2.getPixel(x, y);
                int r1 = (rgb1 >> 16) & 0xff;
                int g1 = (rgb1 >>  8) & 0xff;
                int b1 = (rgb1      ) & 0xff;
                int r2 = (rgb2 >> 16) & 0xff;
                int g2 = (rgb2 >>  8) & 0xff;
                int b2 = (rgb2      ) & 0xff;
                diff += Math.abs(r1 - r2);
                diff += Math.abs(g1 - g2);
                diff += Math.abs(b1 - b2);
            }
        }
        double n = width1 * height1 * 3;
        double p = diff / n / 255.0;
        return p * 100.0;
    }

}
