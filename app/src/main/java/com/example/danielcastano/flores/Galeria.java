package com.example.danielcastano.flores;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Galeria extends AppCompatActivity {

    private Vector<ImageView> mySDCardImages;
    private Cursor cursor;
    private int columnIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        GridView gridview = (GridView) findViewById(R.id.gridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(Galeria.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        String[] projection = {MediaStore.Images.Media._ID};
// Create the cursor pointing to the SDCard
        cursor = managedQuery( MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Images.Media.DATA + " like ? ",
                new String[] {"%Flores%"},
                null);
// Get the column index of the image ID
        columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        gridview.setAdapter(new ImageAdapter(this));
    }





    class ImageAdapter extends BaseAdapter {
        private Context context;

        public ImageAdapter(Context c) {
            context = c;
        }

        public int getCount() {
            return cursor.getCount();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView i = new ImageView(context);
            // Move cursor to current position
            cursor.moveToPosition(position);
            // Get the current value for the requested column
            int imageID = cursor.getInt(columnIndex);
            // obtain the image URI
            Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(imageID) );
            String url = uri.toString();
            // Set the content of the image based on the image URI
            int originalImageId = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1, url.length()));
            Bitmap b = MediaStore.Images.Thumbnails.getThumbnail(getContentResolver(),
                    originalImageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
            i.setImageBitmap(b);

            return i;
        }

        // references to our images
        private Integer[] mThumbIds = {
                R.drawable.rosa, R.drawable.girasol,
                R.drawable.cartucho, R.drawable.ic_delete
        };
    }
}
