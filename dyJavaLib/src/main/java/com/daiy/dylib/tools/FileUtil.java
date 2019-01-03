package com.daiy.dylib.tools;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.daiy.dylib.Constants;
import com.daiy.dylib.logcat.LogTools;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class FileUtil {

    /***
     * 获取项目文件夹
     *
     * @return
     */
    public static File getDir(Context context) {
        String packname = context.getApplicationContext().getPackageName();
        String name = packname.substring(packname.lastIndexOf(".") + 1,
                packname.length());
        File dir = null;
        if ((!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED))) {
            dir = context.getCacheDir();
        } else {
            dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/dai/" + name);
        }
        dir.mkdirs();
        return dir;
    }

    /**
     * 获取项目缓存文件
     *
     * @return
     */
    public static File getCacheDir(Context context) {
        File file = new File(getDir(context).getAbsolutePath() + "/cache");
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 判断SD是否可以
     *
     * @return
     */
    public static boolean isSdcardExist() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 创建根目录
     *
     * @param path 目录路径
     */
    public static void createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 创建文件
     *
     * @param path 文件路径
     * @return 创建的文件
     */
    public static File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    /**
     * 获取项目使用过程中产生的图片文件夹
     *
     * @return
     */
    public static File getImageDir(Context context) {
        File file = new File(getDir(context).getAbsolutePath() + "/image");
        file.mkdirs();
        return file;
    }

    /**
     * 删除文件夹
     *
     * @param dirf
     */
    public static void deleteDir(File dirf) {
        if (dirf.isDirectory()) {
            File[] childs = dirf.listFiles();
            for (int i = 0; i < childs.length; i++) {
                deleteDir(childs[i]);
            }
        }
        dirf.delete();
    }

    /**
     * 判断文件是否存在
     *
     * @param path 文件的路径
     * @return
     */
    public static boolean FindFile(String path) {
        try {
            File f = new File(path);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * uri装换文件
     *
     * @param context
     * @param uri
     * @return
     */
    public static File uriToFile(Activity context, Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = context.managedQuery(uri, proj, null, null,
                null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        File file = new File(img_path);
        return file;
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void write(InputStream in, File file) {
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            while (in.read(buffer) > -1) {
                out.write(buffer);
            }

            out.flush();
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     *
     * @param in
     * @param file
     */
    public static void write(String in, File file, boolean append) {
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file, append);
            fw.write(in);
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读文件
     *
     * @param file
     * @return
     */
    public static String read(File file) {
        if (!file.exists()) {
            return "";
        }
        try {
            FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer buffer = new StringBuffer();
            String s;
            while ((s = br.readLine()) != null) {
                buffer.append(s);
            }
            return buffer.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 删除文件
     *
     * @param folderPath 文件夹的路径
     */
    public static void delFolder(String folderPath) {
        delAllFile(folderPath);
        String filePath = folderPath;
        filePath = filePath.toString();
        File myFilePath = new File(filePath);
        myFilePath.delete();
    }

    /**
     * 删除文件
     *
     * @param path 文件的路径
     */
    public static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);
                delFolder(path + "/" + tempList[i]);
            }
        }
    }

    /**
     * 移动文件
     *
     * @param path 文件的路径
     */
    public static void moveFile(String path, String toPath) {
        File file = null;
        File filePath = null;
        try {
            file = new File(path);
        } catch (Exception e) {
            LogTools.loge(path + e.getMessage());
            return;
        }

        if ((!file.exists()) || (!file.canRead())) {
            LogTools.loge("目标文件不存在或者不允许读操作!");
            return;
        }

        // String newPath= toPath.substring(0,toPath.lastIndexOf("/"));
        try {
            filePath = new File(toPath);
        } catch (Exception e) {
            LogTools.loge(filePath + e.getMessage());
            return;
        }
        ;
        if (!filePath.getParentFile().exists()) {
            if (!filePath.getParentFile().mkdirs()) {
                return;
            }
        }

/*		if (!filePath.canWrite()) {
            LogTools.loge("目标文件不允许写");
			return;
		}*/

        if (filePath.exists()) {
            filePath.delete();
        }

        try {
            FileInputStream fosfrom = new FileInputStream(file);
            FileOutputStream fosto = new FileOutputStream(filePath);
            byte bt[] = new byte[1024];
            int c;

            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); // 将内容写到新文件当中
            }
            // 关闭数据流
            fosfrom.close();
            fosto.close();
            // 删除原来的文件
            if (file.exists()) {
                file.delete();
            }

        } catch (Exception e) {
            e.printStackTrace();
            LogTools.loge("拷贝文件失败！" + path + "-->" + toPath);
        }
    }

    /**
     * 读文件为 byte[]
     *
     * @return
     */
    public static byte[] read(String mFileName) {
        if (mFileName == null || mFileName.equals("")) {
            return "".getBytes();
        }
        File srcFile = null;
        srcFile = new File(mFileName);
        if (!srcFile.exists()) {
            return "".getBytes();
        }
        try {

            /*
             * FileInputStream stream = new FileInputStream(srcFile);
             * ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
             * byte[] b = new byte[1000]; int n; while ((n = stream.read(b)) !=
             * -1) out.write(b, 0, n); stream.close(); out.close(); return
             * out.toByteArray();
             */
            int size = (int) srcFile.length();
            byte[] fileData = new byte[(int) size];

            FileInputStream fin = new FileInputStream(srcFile);
            DataInputStream dis = new DataInputStream(fin);
            dis.readFully(fileData);
            dis.close();

            fin.close();
            return fileData;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "".getBytes();
    }

    /** */
    /**
     * 获取文件名
     */
    public static String getFileName(String fileName) {
        fileName = fileName.trim();
        return fileName.substring(fileName.lastIndexOf("/") + 1);
    }

    /** */
    /**
     * 把字节数组保存为一个文件
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;

        // bufferreader和stringbuffer
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 换算文件大小
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "未知大小";
        if (size < 1024) {
            fileSizeString = df.format((double) size) + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public static File saveBitmap(Bitmap bitmap, String name) throws IOException {
        File file = new File(Constants.DATABASE_PATH + name);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
                LogTools.loge("图片成功" + file.getPath());
                return file;

            }
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
