package com.zhaoyh.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created by zhaoyh on 2017/5/31.
 */
public class DownloadUtils {


    /**
     * 下载图片到本地
     * @param urlStr
     * @param filePath
     */
    public static void downloadPictureFromUrl(String urlStr, String filePath) {
        try {
            URL url = new URL(urlStr);
            DataInputStream dis = new DataInputStream(url.openStream());

            //建立新的文件
            FileOutputStream fos = new FileOutputStream(new File(filePath));
            byte[] buffer = new byte[1024];
            int length;

            // 开始填充数据
            while ((length = dis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            dis.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
