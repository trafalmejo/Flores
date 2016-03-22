package com.example.danielcastano.flores;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import db.FloresDBHelper;
public class FlorVista extends AppCompatActivity {
    String nombreCient = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flor_vista);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        Intent intent = getIntent();
        TextView nombreFlor = (TextView)findViewById(R.id.card_flor_name);
        nombreFlor.setText(intent.getStringExtra("NombreFlor"));
        TextView nombreFlorCientifico = (TextView)findViewById(R.id.card_flor_name_cient);
        nombreFlorCientifico.setText(intent.getStringExtra("NombreFlorCientifico"));
        nombreCient = intent.getStringExtra("NombreFlorCientifico");
        TextView descripcionFlor = (TextView)findViewById(R.id.card_descripcion);
        //SQLite
        FloresDBHelper db = new FloresDBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        String consulta = "SELECT * FROM FLORES WHERE NOMBRE = '" + nombreFlor.getText()  +"'";
        Cursor cursor = datos.rawQuery(consulta,null);
        if(cursor.moveToFirst())
        {
            descripcionFlor.setText(cursor.getString(cursor.getColumnIndex("DESCRIPCION")));
        }
        //\SQLite
    }
    public void irUsos(View view) {
        Intent intent = new Intent(this, Usos.class);
        intent.putExtra("NombreFlorCientifico",nombreCient);
        startActivity(intent);
    }
}