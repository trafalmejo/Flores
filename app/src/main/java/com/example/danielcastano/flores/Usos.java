package com.example.danielcastano.flores;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import db.FloresDBHelper;
import mundo.Flor;
public class Usos extends AppCompatActivity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    String nombreCient = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usos);
        nombreCient = getIntent().getStringExtra("NombreFlorCientifico");
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        //SQLite
        FloresDBHelper db = new FloresDBHelper(this);
        SQLiteDatabase datos = db.getReadableDatabase();
        String consulta = "SELECT * FROM USOS WHERE NOM_CIENTIFICO_FLOR = '" + nombreCient  +"'";
        Cursor cursor = datos.rawQuery(consulta,null);
        while(cursor.moveToNext())
        {
            String tipo = cursor.getString(cursor.getColumnIndex("TIPO"));
            listDataHeader.add(tipo);
        }
        for (int i = 0; i < listDataHeader.size();i++)
        {
            List<String> childs = new ArrayList<String>();
            String consulta2 = "SELECT * FROM USOS WHERE NOM_CIENTIFICO_FLOR = '" + nombreCient  +"' AND TIPO ='" + (String)listDataHeader.get(i) +"'";
            Cursor cursor2 = datos.rawQuery(consulta2, null);
            while(cursor2.moveToNext())
            {
                String descr = cursor2.getString(cursor.getColumnIndex("DESCRIPCION"));
                childs.add(descr);
            }
            listDataChild.put(listDataHeader.get(i), childs); // Header, Child data
        }
        //\SQLite
    }
    class ExpandableListAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<String> _listDataHeader; // header titles
        // child data in format of header title, child title
        private HashMap<String, List<String>> _listDataChild;
        public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
            this._context = context;
            this._listDataHeader = listDataHeader;
            this._listDataChild = listChildData;
        }
        @Override
        public Object getChild(int groupPosition, int childPosititon) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .get(childPosititon);
        }
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }
        @Override
        public View getChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final String childText = (String) getChild(groupPosition, childPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.exp_list_item, null);
            }
            TextView txtListChild = (TextView) convertView
                    .findViewById(R.id.lblListItem);
            txtListChild.setText(childText);
            return convertView;
        }
        @Override
        public int getChildrenCount(int groupPosition) {
            return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                    .size();
        }
        @Override
        public Object getGroup(int groupPosition) {
            return this._listDataHeader.get(groupPosition);
        }
        @Override
        public int getGroupCount() {
            return this._listDataHeader.size();
        }
        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            String headerTitle = (String) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this._context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.list_group, null);
            }
            TextView lblListHeader = (TextView) convertView
                    .findViewById(R.id.lblListHeader);
            lblListHeader.setTypeface(null, Typeface.BOLD);
            lblListHeader.setText(headerTitle);
            return convertView;
        }
        @Override
        public boolean hasStableIds() {
            return false;
        }
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
