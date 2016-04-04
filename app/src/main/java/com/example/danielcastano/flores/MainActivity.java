package com.example.danielcastano.flores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import mundo.Flock;
import mundo.Flor;

public class MainActivity extends AppCompatActivity {

    int umbral = 30;
    static final int REQUEST_TAKE_PHOTO = 1;
    String mCurrentPhotoPath;
    private ImageView imageView;
    private int contador;
    public String lastPhoto;
    private Flock flock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        imageView = (ImageView) findViewById(R.id.imageView);
        contador = ((mundo.Flock)this.getApplication()).getContador();

        lastPhoto = "";
        flock = new Flock();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void botonIrColeccionFlores(View view) {

        startActivity(new Intent(this, ColeccionFlores.class));
    }

    public void botonIrGaleria(View view) {

        startActivity(new Intent(this, Galeria.class));
    }

    public void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public void dispatchTakePictureIntent(View view) {

        ((mundo.Flock)this.getApplication()).setContador(((mundo.Flock) this.getApplication()).getContador() + 1);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                flock.setImagen(photoFile);
            }
        }
    }
    public File createImageFile() throws IOException {
        // Create an image file name
        String imageFileName = ((mundo.Flock)this.getApplication()).getContador()+"";
        File image = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/Flores/"+imageFileName+".jpg");
        image.getParentFile().mkdirs();
        image.createNewFile();

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        lastPhoto = image.getAbsolutePath();
        Log.d("Direccion: ", lastPhoto);

        galleryAddPic();
        return image;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {

            Bitmap imagen = BitmapFactory.decodeFile(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/Flores/" + ((mundo.Flock) this.getApplication()).getContador() + ".jpg");
            String mensaje = leerFloor(imagen);




        }
    }
    public String leerFloor(Bitmap imagen){
        //GET
        //new HttpAsyncTask().execute("http://garden.vsapi01.com/api-search/by-url?url="+ROSA+"&apikey="+API_KEY);
        //POST
        new HttpAsyncTask().execute("http://garden.vsapi01.com/api-search?apikey="+flock.API_KEY, lastPhoto);
        return flock.getResultado();

    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override

        protected String doInBackground(String... urls) {

            //return GET(urls[0]);
            return flock.POST(urls[0],lastPhoto);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();
            //Log.d("RESPONSE", result);
            Context c = getApplicationContext();
            CharSequence text = flock.getResultado();
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(c, text, duration);
            toast.show();

        }
    }



}
