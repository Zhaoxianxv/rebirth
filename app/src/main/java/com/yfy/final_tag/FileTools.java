package com.yfy.final_tag;

import android.os.Environment;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by yfyandr on 2017/10/27.
 */

public class FileTools {
    /**
     * 即判断你指定的路径或着指定的目录文件是否已经存在。
     * @param path
     * @return
     */
    public static boolean fileIsExists(String path){
        try{
            File f=new File(path);
            if(!f.exists()){
                return false;
            }
        }catch (Exception e) {
            // TODO: handle exception
            return false;
        }
        return true;
    }

    /**
     * @return 获取一个图片文件路径
     */
    public static String getJPGpath(){
        return Environment.getExternalStorageDirectory().toString() +"/yfy/"+  System.currentTimeMillis() + ".jpg";
    }

    /**
     * 获取文件大小
     */
    public static long getFileSize(String filePath) {
        long size = 0;
        File file = new File(filePath);
        if (file.exists()) {
            size = file.length();
        }
        return size;
    }


    /**
     * 将子节数转换为Kb
     */
    public static String convertBytesToOther(long byteSize) {
        String result = null;
        float size;

        DecimalFormat decimalFormat1 = new DecimalFormat(".0");
        DecimalFormat decimalFormat2 = new DecimalFormat(".00");

        if (byteSize < 1024) {
            result = byteSize + "B";
        } else {
            size = byteSize / 1024;
            if (size < 1024) {
                result = decimalFormat1.format(size) + "KB";
            } else {
                size = size / 1024;
                if (size < 1024) {
                    result = decimalFormat2.format(size) + "M";
                } else {
                    size = size / 1024;
                    if (size < 1024) {
                        result = decimalFormat2.format(size) + "G";
                    }
                }
            }
        }
        return result;
    }


    /**
     * 删除单个文件
     *
     * @param sPath
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */

    public static void deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
        }
    }


    /**
     * 根据路径创建file
     * @param file
     */
    public static void createFile(File file) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(String path) {
        File file = new File(path);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 根据路径创建file并返回file
     * @param path
     */
    public static File getFile(String path) {
        File file = new File(path);
        createFile(file);
        if (file!=null){
            return file;
        }else{
            return null;
        }
    }
    /**
     * 根据路径 get file name
     * @param path
     */
    public static String getFileName(String path) {

        File file = new File(path);
        if (file.exists()) {
            return file.getName();
        }
        return "";
    }


    /**
     * 读取文件为字符串
     * @param file 文件
     * @return 文件内容字符串
     * @throws IOException
     */
    public static String $read(File file) throws IOException {
        String text = null;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            text = $read(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return text;
    }

    /**
     * 读取输入流为字符串,最常见的是网络请求
     * @param is 输入流
     * @return 输入流内容字符串
     * @throws IOException
     */
    public static String $read(InputStream is) throws IOException {
        StringBuffer strbuffer = new StringBuffer();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                strbuffer.append(line).append("\r\n");
            }
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return strbuffer.toString();
    }

    /**
     * 把字符串写入到文件中
     * @param file 被写入的目标文件
     * @param str 要写入的字符串内容
     * @throws IOException
     */
    public static void $write(File file, String str) throws IOException {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new FileOutputStream(file));
            out.write(str.getBytes());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * unzip zip file to dest folder
     * @param zipFilePath
     * @param destPath
     */
    public static void $unzip(String zipFilePath, String destPath) throws IOException {
        // check or create dest folder
        File destFile = new File(destPath);
        if (!destFile.exists()) {
            destFile.mkdirs();
        }

        // start unzip
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry zipEntry;
        String zipEntryName;
        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
            zipEntryName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                File folder = new File(destPath + File.separator + zipEntryName);
                folder.mkdirs();
            } else {
                File file = new File(destPath + File.separator + zipEntryName);
                if (file != null && !file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = zipInputStream.read(buffer)) > 0) {
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        zipInputStream.close();
    }

    /**
     * 删除文件或者文件夹，默认保留根目录
     * @param directory
     */
    public static void $del(File directory) {
        $del(directory, false);
    }

    /**
     * 删除文件或者文件夹
     * @param directory
     */
    public static void $del(File directory, boolean keepRoot) {
        if (directory != null && directory.exists()) {
            if (directory.isDirectory()) {
                for (File subDirectory : directory.listFiles()) {
                    $del(subDirectory, false);
                }
            }

            if (!keepRoot) {
                directory.delete();
            }
        }
    }




    /**
     * 获取某个目录下所有的文件的全路径和文件名的集合；
     *
     * @return
     */
    public List<List<String>> getAllFile(String mulu) {
        File file = new File(mulu);
        File[] files = file.listFiles();
        List<List<String>> ret = new ArrayList<List<String>>();
        List<String> allFilePath = new ArrayList<String>();
        List<String> allFileName = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                allFilePath.add(files[i].toString());
                allFileName.add(files[i].getName());
            }
        }
        ret.add(allFilePath);
        ret.add(allFileName);
        return ret;
    }



}
