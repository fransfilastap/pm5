package id.franspratama.geol.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Email {
    static int seq = 0;
    static String hostname;
    
    public static synchronized int getSeq() {
        return seq++ % 100000;
    }

    public static String getContentId() {
        int c = Email.getSeq();
        return String.valueOf(c) + "." + System.currentTimeMillis() + "@" + hostname;
    }

    public static byte[] extractBytes(File imgFile) throws IOException {
        File imgPath = imgFile;
        BufferedImage bufferedImage = ImageIO.read(imgPath);
        WritableRaster raster = bufferedImage.getRaster();
        DataBufferByte data = (DataBufferByte)raster.getDataBuffer();
        return data.getData();
    }
	
}
