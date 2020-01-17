package com.itheima.test;
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  

public class Test_Files_Move {
		
	 public static void main(String[] args) {  
	        //����D���µ�bbsĿ¼��  D���µ�bb2/mms1��  
	        Test_Files_Move.copyFileOrDir("C:\\Users\\dell\\Desktop\\image_temp", "C:\\Users\\dell\\Desktop\\LoginByMysql_JavaWeb-master\\WebContent\\images_user");  
	    }  
	 
	 /** 
     * @Description: ��������������һ�����ļ��е�����Ŀ¼��Ȼ����Ŀ¼�µ������ļ�����Ӧ��Ŀ¼ 
     * @param srcPath   ��Ҫ���Ƶ�Ŀ¼ 
     * @param targetPath  ���Ƶ����� 
     * @author L.Eric 
     * create: 2013-4-16 
     */  
    public static void copyFileOrDir(String srcPath, String targetPath){  
        parseDir(srcPath,targetPath);  
        copyAllFile(srcPath, targetPath);  
    }  
      
    public static void copyAllFile(String srcPath, String targetPath){  
        File f = new File(srcPath);  
        File[] fileList = f.listFiles();  
        for(File f1 : fileList){  
            if(f1.isFile()) {  
                Test_Files_Move.copyFile(srcPath + "//" + f1.getName(), targetPath + "//hjh_" + f1.getName());  
            }  
            //�ж��Ƿ���Ŀ¼  
            if(f1.isDirectory()) {  
                copyAllFile(f1.getPath().toString(), targetPath + "//" + f1.getName());  
            }  
        }  
    }  
      
     /** 
         * @Description: ͨ���ֽ�������һ���ļ� 
         * @param src      Դ�ļ���·�� 
         * @param target   Ŀ���ļ���·�� 
         * @author L.Eric 
         * create: 2013-4-16 
    */  
    public static void copyFile(String src, String target){  
            InputStream is = null;  
            OutputStream os = null;  
              
            try {  
                is = new FileInputStream(src);  
                os = new FileOutputStream(target);  
                byte[] buff = new byte[1024];  
                int len = 0;  
                while((len = is.read(buff, 0, buff.length)) != -1) {  
                    os.write(buff, 0, len);  
                }  
                System.out.println("�ļ������ɹ���");  
            } catch (FileNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } finally {  
                if(os!=null){  
                    try {  
                        os.close();  
                    } catch (IOException e) {  
                        // TODO Auto-generated catch block  
                        e.printStackTrace();  
                    } finally {  
                        if(is!=null){  
                            try {  
                                is.close();  
                            } catch (IOException e) {  
                                // TODO Auto-generated catch block  
                                e.printStackTrace();  
                            }  
                        }  
                    }  
                }  
            }  
              
        }  
      
     /** 
     * @Description: ����һ��Ŀ¼�µ�����Ŀ¼�ļ���ֻ����Ŀ¼�ṹ�� 
     * @param pathName   ��Ҫ���Ƶ�Ŀ��Ŀ¼ 
     * @param target     ���ɵ�Ŀ���ļ�Ŀ¼ 
     * @author L.Eric 
     * create: 2013-4-16 
     */  
    public static void parseDir(String pathName, String target){  
        //����һ���µ�Ŀ¼  
        File targetFile = new File(target);  
        if(!targetFile.exists()) {  
            targetFile.mkdirs();  
        }  
          
        //����һ������·��  
        File file = new File(pathName);  
        if(file.isDirectory()){  
            File[] files = file.listFiles();  
            for(File f : files){  
                if(f.isDirectory()) {  
                    parseDir(f.getPath(), target + "//" + f.getName());  
                }  
            }  
        }  
    }  
}
