package com.itheima.test;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
public class Test_fenbianlv {
	  /**
     * 通过BufferedImage获取
     * @param file 文件
     * @return 图片的分辨率
     * @throws IOException
     */
    public static String getResolution1(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        return image.getWidth() + "x" + image.getHeight();
    }

    /**
     * 获取图片的分辨率
     * 
     * @param path
     * @return
     */
    public static Dimension getImageDim(String path) {
        Dimension result = null;
        String suffix = getFileSuffix(path);
        //解码具有给定后缀的文件
        Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
        System.out.println(ImageIO.getImageReadersBySuffix(suffix));
        if (iter.hasNext()) {
            ImageReader reader = iter.next();
            try {
                ImageInputStream stream = new FileImageInputStream(new File(
                        path));
                reader.setInput(stream);
                int width = reader.getWidth(reader.getMinIndex());
                int height = reader.getHeight(reader.getMinIndex());
                result = new Dimension(width, height);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                reader.dispose();
            }
        }
        return result;
    }

    /**
     * 获得图片的后缀名
     * @param path
     * @return
     */
    private static String getFileSuffix(final String path) {
        String result = null;
        if (path != null) {
            result = "";
            if (path.lastIndexOf('.') != -1) {
                result = path.substring(path.lastIndexOf('.'));
                if (result.startsWith(".")) {
                    result = result.substring(1);
                }
            }
        }
        return result;
    }

    /**
     * 截取Dimension对象获得分辨率
     * @param path
     * 
     * @return
     */
    public static String getResolution2(String path) {
        String s = getImageDim(path).toString();
        s = s.substring(s.indexOf("[") + 1, s.indexOf("]"));
        String w = s.substring(s.indexOf("=") + 1, s.indexOf(","));
        String h = s.substring(s.lastIndexOf("=") + 1);
        String result = w + " x " + h;
        System.out.println(result);
        return result;
    }

    /**
     * 测试
     * 
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\dell\\Desktop\\LoginByMysql_JavaWeb-master\\WebContent\\images\\banji.jpg";
        getResolution2(path);
    }

}
