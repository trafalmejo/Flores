package mundo;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.HttpGet;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.mime.HttpMultipartMode;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;
import db.FloresDBHelper;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
/**
 * Created by danielcastano on 3/2/16.
 */
public class Flock extends Application{
    public final String USER_AGENT = "Mozilla/5.0";
    public final String API_KEY = "78810bd2-2ea4-4418-a6b7-7322b426aa66";
    public final String ROSA = "http://c2.staticflickr.com/4/3467/3895548557_5ff7e67db2_n.jpg";


    public final static String FLORESNOIDENTIFICADAS = "Esta flor aun no se ha identificado";

    public static Bitmap bm;
    private int contador;
    private ArrayList<Flor>  misFlores;
    private ArrayList<Flor> floresNoIdentificadas;
    private ArrayList<Categoria> categorias;
    public String resultado;
    public File imagenAnalizar;


    public Flock(){
        misFlores = new ArrayList<Flor>();
        floresNoIdentificadas = new ArrayList<Flor>();
        categorias = new ArrayList<Categoria>();
        Categoria noId = new Categoria(FLORESNOIDENTIFICADAS);
        categorias.add(noId);
        resultado = "No se encontro una flor. Revisa su conexión e intentalo más tarde";
        imagenAnalizar = null;
        contador = 99;


    }
    public File getImagen(){
        return imagenAnalizar;
    }
    public void setImagen(File a){
        imagenAnalizar = a;
    }
    public String getResultado(){
        return resultado;
    }

    public int getContador(){
        return contador;
    }
    public void setResultado(String a){
        resultado = a;
    }

    public void setContador(int a){
        contador = a;
    }

    public ArrayList<Flor> getMisFlores() {
        return misFlores;
    }

    public ArrayList<Flor> getFloresNoIdentificadas() {
        return floresNoIdentificadas;
    }

    public void setFloresNoIdentificadas(ArrayList<Flor> floresNoIdentificadas) {
        this.floresNoIdentificadas = floresNoIdentificadas;
    }

    public String POST(final String url, String img) {
        //File imagen = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Flores/"+getContador()+".jpg");
        File imagen = new File("");
        Log.d("camino",img);
        try
        {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(url);

            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


            if(imagen != null)
            {
                entityBuilder.addBinaryBody("image", imagen);
            }

            HttpEntity entity = entityBuilder.build();
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            if (!result.equals("")){
                JSONObject json = new JSONObject(result);
                JSONArray jArray = json.getJSONArray("images");

                JSONObject json_data = jArray.getJSONObject(0);
                String name = json_data.getString("plantNames");
                setResultado("La Flor corresponde a: "+ name);
            }
            Log.v("resultado", resultado);
            Log.v("resultado", result);
        }
        catch(Exception e)
        {
            Flor noIdentificada = new Flor("", null);
            if(floresNoIdentificadas.size() < 10) {
                floresNoIdentificadas.add(noIdentificada);
            }
            else{
               anunciarMensaje("La cola de fotos en espera esta llena (10), por favor conecte su dispositivo a una red Wifi para poder identificar las flores pendientes");
            }
            e.printStackTrace();
        }

        return getResultado();
    }
    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "No se obtuvo respuesta del servidor. Intentelo de nuevo";

        } catch (Exception e) {
            Log.d("ERROR", e.getLocalizedMessage());
        }

        return result;
    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }
    public void anunciarMensaje(String mensaje){
        Context c = getApplicationContext();
        CharSequence text = mensaje;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(c, text, duration);
        toast.show();

    }
}
