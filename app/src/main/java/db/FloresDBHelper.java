package db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by GILBERTO on 28/02/2016.
 */
public class FloresDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Flores.db";
    public static final int DATABASE_VERSION = 1;
    public FloresDBHelper(Context context){
        super(context,
                DATABASE_NAME,//String name
                null,//factory
                DATABASE_VERSION//int version
        );
    }
    public void onCreate(SQLiteDatabase db)
    {
        String createTableFlores = "CREATE TABLE FLORES (NOMBRE TEXT, NOM_CIENTIFICO TEXT PRIMARY KEY, DESCRIPCION TEXT)";
        String createTableFloresEncontradas = "CREATE TABLE FLORES_ENCONTRADAS (NOMBRE TEXT, NOM_CIENTIFICO_2 TEXT PRIMARY KEY)";
        String createTableUsos = "CREATE TABLE USOS (TIPO TEXT, DESCRIPCION TEXT, NOM_CIENTIFICO_FLOR TEXT)";
        String insertarFlores = "INSERT INTO FLORES (NOMBRE, NOM_CIENTIFICO, DESCRIPCION) VALUES ('Girasol','Helianthus annuus', 'Helianthus annuus, llamado comúnmente girasol, calom, jáquima, maravilla, mirasol, tlapololote, maíz de teja, acahual2 (del náhuatl atl, agua y cahualli, dejado, abandonado) o flor de escudo (del náhuatl chimali,escudo y xochitl,flor). Es una planta herbácea anual de la familia de las asteráceas, originaria de América y cultivada como alimenticia, oleaginosa y ornamental en todo el mundo.')";
        String insertarFloresEncontradas = "INSERT INTO FLORES_ENCONTRADAS (NOMBRE, NOM_CIENTIFICO_2) VALUES ('Girasol','Helianthus annuus')";
        String insertarUsos = "INSERT INTO USOS (TIPO, DESCRIPCION, NOM_CIENTIFICO_FLOR) VALUES" +
                " ('Alimenticio'," +
                "'Los frutos del girasol, las populares «pipas», suelen ser consumidas tras un leve tostado y, en ocasiones, un leve salado; se consideran muy saludables ya que, al igual que el aceite de girasol, son ricas en alfa-tocoferol (vitamina E natural) y minerales.'," +
                "'Helianthus annuus')";
        db.execSQL(createTableFlores);
        db.execSQL(createTableFloresEncontradas);
        db.execSQL(createTableUsos);
        db.execSQL(insertarFlores);
        db.execSQL(insertarFloresEncontradas);
        db.execSQL(insertarUsos);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXIST FLORES");
        db.execSQL("DROP TABLE IF EXIST FLORES_ENCONTRADAS");
        db.execSQL("DROP TABLE IF EXIST USOS");
        onCreate(db);
    }
}