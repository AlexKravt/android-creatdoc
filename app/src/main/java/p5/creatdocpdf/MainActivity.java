package p5.creatdocpdf;

import android.media.Image;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImage;
import com.itextpdf.text.pdf.PdfIndirectObject;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic";
    public static final String DIR_PDF = "dirPDF";
    public static final String SRC = "sample.pdf";
    public static final String DEST = "stamp_file.pdf";
    public static final String IMG = "avatar_photo_oval.png";
    private  String PathDirDCIM;
    public static final String FRAGMENT_PDF_EDITOR = "pdf_editor";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_real);

        if (savedInstanceState == null) {
           /* getFragmentManager().beginTransaction()
                    .add(R.id.container, new PdfRendererBasicFragment(),
                            FRAGMENT_PDF_RENDERER_BASIC)
                    .commit();*/
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PdfEditFragment(),
                            FRAGMENT_PDF_EDITOR)
                    .commit();



        PathDirDCIM = Environment.getExternalStorageDirectory() + "/" + DIR_PDF;
        File dirDCIM = new File(PathDirDCIM);
        if(!dirDCIM.exists()) {
            dirDCIM.mkdir();
        }
        else
        {
            try {
                  createPdf();
                  manipulatePdf(PathDirDCIM+"/"+SRC, PathDirDCIM+"/"+DEST);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


   public void manipulatePdf(String src, String dest) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(src);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(dest));
        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(PathDirDCIM+"/"+IMG);
        PdfImage stream = new PdfImage(image, "", null);
        stream.put(new PdfName("ITXT_SpecialId"), new PdfName("123456789"));
        PdfIndirectObject ref = stamper.getWriter().addToBody(stream);
        image.setDirectReference(ref.getIndirectReference());
        image.setAbsolutePosition(36,0);
        PdfContentByte over = stamper.getOverContent(2);
        over.addImage(image);
        PdfContentByte over2 = stamper.getOverContent(3);
       over2.addImage(image);
        stamper.close();
        reader.close();
    }

    private void createPdf() throws FileNotFoundException, DocumentException {

        File myFile = new File(PathDirDCIM+"/"+DEST);
        OutputStream output = new FileOutputStream(myFile);
        //Step 1
        Document document = new Document();
        //Step 2
        PdfWriter.getInstance(document, output);
        //Step 3
        document.open();
        //Step 4 Add content
        document.add(new Paragraph("New document"));
        document.add(new Paragraph("title"));
        //Step 5: Close the document
        document.close();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.intro_message)
                        .setPositiveButton(android.R.string.ok, null)
                        .show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
