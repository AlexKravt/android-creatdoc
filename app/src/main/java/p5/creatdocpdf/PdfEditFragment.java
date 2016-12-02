package p5.creatdocpdf;

import android.app.Fragment;
import android.content.Context;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.IOException;


public class PdfEditFragment extends Fragment{

    Context context;
    PDFView pdfView;
    public PdfEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pdf_editor, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = view.getContext();
        pdfView = (PDFView) view.findViewById(R.id.pdfView);

        try {
            //String filestorage = Environment.getExternalStorageDirectory().getAbsolutePath().toString();
            String newpathdir = FileUtils.getNewDir("dirPDF");
            String filePDF = newpathdir+"/sample.pdf";
            openRenderer(context, Uri.parse(filePDF).toString());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error! " + e.getMessage(), Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

    }

    private void openRenderer(Context context,String path_file) throws IOException {

        // File file =  FileUtils.fileFromAsset(context, path_file);
        File file = new File(path_file); //  FileUtils.fileFromCard(context,path_file);
        if(file.exists())
        {
            pdfView.fromFile(file)
                   // .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                   /* .onDraw(onDrawListener)
                    .onLoad(onLoadCompleteListener)
                    .onPageChange(onPageChangeListener)
                    .onPageScroll(onPageScrollListener)
                    .onError(onErrorListener)*/
                    .enableAnnotationRendering(false)
                    .password(null)
                    .scrollHandle(null)
                    .load();
        }
        else
        {
            Toast.makeText(context,"Файл отсутствует",Toast.LENGTH_LONG).show();
        }
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

}
