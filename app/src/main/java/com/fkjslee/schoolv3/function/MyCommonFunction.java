package com.fkjslee.schoolv3.function;

import android.graphics.Bitmap;

import com.fkjslee.schoolv3.activity.LogActivity;
import com.fkjslee.schoolv3.network.HttpThread;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author fkjslee
 * @time 2017/4/15
 */

public class MyCommonFunction {
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            //
        }
        return null;
    }

    public static void compressAndGenImage(Bitmap image, String outPath, int maxSize) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        // scale
        int options = 100;
        // Store the bitmap into output stream(no compress)
        image.compress(Bitmap.CompressFormat.JPEG, options, os);
        // Compress by loop
        while ( os.toByteArray().length / 1024 > maxSize) {
            // Clean up os
            os.reset();
            // interval 10
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, os);
        }


        try {
            FileOutputStream fos = new FileOutputStream(outPath);
            fos.write(os.toByteArray());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String sendRequestToServer(String param) {
        String url = LogActivity.url;
        HttpThread httpThread = new HttpThread(url, param);
        new Thread(httpThread).start();
        return httpThread.getResult();
    }
}
