package com.example.admin.week1weekendhw;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfDocument;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import android.content.ActivityNotFoundException;
public class PDFActivity extends AppCompatActivity {


    private static final String TAG ="PDFTAG" ;
    private ImageView imageView;
    private int currentPage = 0;
    private Button next, previous;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);



        next = (Button) findViewById(R.id.next);
        previous = (Button) findViewById(R.id.previous);

        next.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                currentPage++;
                render();
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentPage--;
                render();
            }
        });

        render();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void render() {
        Log.d(TAG, "renderSection: ");


            imageView = (ImageView) findViewById(R.id.image);
            int REQ_WIDTH = imageView.getWidth();
            int REQ_HEIGHT = imageView.getHeight();

        File f = new File("/storage/emulated/0/Download/Nerd.pdf");
        if(f.exists() && !f.isDirectory()) {
            // do something
            Log.d(TAG, "File exist "+f.getName());
        }
        else
            Log.d(TAG, "No exists");

        try{
            Bitmap bitmap = Bitmap.createBitmap(REQ_WIDTH, REQ_HEIGHT, Bitmap.Config.ARGB_4444);
            File file = new File("/storage/emulated/0/Download/Nerd.pdf");



            PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY));
            Log.d(TAG, "render: " + renderer.toString());
            if(currentPage < 0) {
                currentPage = 0;
            } else if(currentPage > renderer.getPageCount()) {
                currentPage = renderer.getPageCount() - 1;
            }

            Matrix m = imageView.getImageMatrix();
            Rect rect = new Rect(0, 0, REQ_WIDTH, REQ_HEIGHT);
            renderer.openPage(currentPage).render(bitmap, rect, m, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
            imageView.setImageMatrix(m);
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();

        } catch(Exception e) {
            e.printStackTrace();
    Log.d(TAG, "render: "+e.toString());
        }
    }
}
