package p5.creatdocpdf;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kravtsov.a on 01.12.2016.
 */


public class FileUtils {
    private FileUtils() {
    }

    public static File fileFromCard(Context context, String path_file) throws IOException {
        File outFile = new File(path_file);
       // copy(context.openFileInput(path_file), outFile);


        return outFile;
    }

    public static String getNewDir(String nameDirRoot)
    {
        String PathDirDCIM = Environment.getExternalStorageDirectory() + "/" + nameDirRoot;


        File dirDCIM = new File(PathDirDCIM);
        if(!dirDCIM.exists()) {
            dirDCIM.mkdir();
        }

        return PathDirDCIM;
    }

    public static File fileFromAsset(Context context, String assetName) throws IOException {
        File outFile = new File(context.getCacheDir(), assetName );
        copy(context.getAssets().open(assetName), outFile);

        return outFile;
    }

    public static void copy(InputStream inputStream, File output) throws IOException {
        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(output);
            boolean read = false;
            byte[] bytes = new byte[1024];

            int read1;
            while((read1 = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read1);
            }
        } finally {
            try {
                if(inputStream != null) {
                    inputStream.close();
                }
            } finally {
                if(outputStream != null) {
                    outputStream.close();
                }

            }

        }

    }
}