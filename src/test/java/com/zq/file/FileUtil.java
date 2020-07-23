package com.zq.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: zhangqi
 * @CreateTime: 2020/7/710:03
 * @Company: MGL  原文地址:https://blog.csdn.net/qq1036053871/article/details/17055081?utm_source=blogxgwz3
 */
public class FileUtil {

    /**
     * 列出指定目录下（包括其子目录）的所有文件
     */

    public static void listDirectory(File dir)

            throws IOException {

        if (!dir.exists()) {

            throw new IllegalArgumentException("目录：" + dir

                    + "不存在");

        }

        if (!dir.isDirectory()) {

            throw new IllegalArgumentException(dir + "不是目录");

        }

        File[] subs = dir.listFiles();

        if (subs != null && subs.length > 0) {

            for (File sub : subs) {

                if (sub.isDirectory()) {

                    listDirectory(sub);

                } else {

                    System.out.println(sub);

                }

            }

        }

    }

    /**
     * 删除指定目录及其包含的所有内容
     */

    public static void deleteDirectory(File dir)

            throws IOException {

        if (!dir.exists()) {

            throw new IllegalArgumentException("目录：" + dir +

                    "不存在");

        }

        if (!dir.isDirectory()) {

            throw new IllegalArgumentException(dir + "不是目录");

        }

        File[] subs = dir.listFiles();

        if (subs != null && subs.length > 0) {

            for (File sub : subs) {

                if (sub.isDirectory()) {

                    deleteDirectory(sub);

                } else {

                    System.out.println(sub);

                    if (!sub.delete()) {

                        throw new IOException("无法删除文件：" + sub);

                    }

                }

            }

        }

        System.out.println(dir);

        if (!dir.delete()) {

            throw new IOException("无法删除目录：" + dir);

        }

    }

    /**
     * 列出指定目录下指定扩展名的所有文件
     */

    public static List<File> listFile(File dir,

                                      final String[] extensions, boolean recursive) {

        if (!dir.exists()) {

            throw new IllegalArgumentException("目录：" + dir +

                    "不存在");

        }

        if (!dir.isDirectory()) {

            throw new IllegalArgumentException(dir + "不是目录");

        }

        FileFilter ff = null;

        if (extensions == null || extensions.length == 0) {

            ff = new FileFilter() {

                @Override
                public boolean accept(File pathname) {

                    return true;

                }

            };

        } else {

            ff = new FileFilter() {

                @Override
                public boolean accept(File pathname) {

                    if (pathname.isDirectory())

                        return true;

                    String name = pathname.getName();

                    for (String ext : extensions) {

                        if (name.endsWith(ext)) {

                            return true;

                        }

                    }

                    return false;

                }

            };

        }

        return listFile(dir, ff, recursive);

    }

    /**
     * 列出文件名为指定文件名的文件
     */

    public static List<File> listFile(File dir,

                                      final String filename, boolean recursive) {

        if (!dir.exists()) {

            throw new IllegalArgumentException("目录：" + dir

                    + "不存在");

        }

        if (!dir.isDirectory()) {

            throw new IllegalArgumentException(dir + "不是目录");

        }

        FileFilter ff = null;

        if (filename == null || filename.length() == 0) {

            ff = new FileFilter() {

                @Override
                public boolean accept(File pathname) {

                    return true;

                }

            };

        } else {

            ff = new FileFilter() {

                @Override
                public boolean accept(File pathname) {

                    if (pathname.isDirectory())

                        return true;

                    String name = pathname.getName();

                    if (name.indexOf(filename) != -1)

                        return true;

                    else

                        return false;

                }

            };

        }

        return listFile(dir, ff, recursive);

    }

    private static List<File> listFile(File dir, FileFilter ff,

                                       boolean recursive) {

        List<File> list = new ArrayList<File>();

        File[] subs = dir.listFiles(ff);

        if (subs != null && subs.length > 0) {

            for (File sub : subs) {

                if (sub.isFile()) {

                    list.add(sub);

                } else if (recursive) {

                    list.addAll(listFile(sub, ff, true));

                }

            }

        }

        return list;

    }

    /***
     * 复制文件
     *
     * @param srcFile
     * @param destFile
     * @throws IOException
     */
    public static void copyFile(File srcFile, File destFile)

            throws IOException {

        if (!srcFile.exists()) {

            throw new IllegalArgumentException("文件：" + srcFile

                    + "不存在");

        }

        if (!srcFile.isFile()) {

            throw new IllegalArgumentException(srcFile +

                    "不是文件");

        }

        RandomAccessFile srcRaf = new RandomAccessFile(srcFile,

                "r");

        if (!destFile.exists()) {

            if (!destFile.createNewFile())

                throw new IOException("无法创建文件：" + destFile);

        }

        RandomAccessFile destRaf = new RandomAccessFile(

                destFile, "rw");

        byte[] buffer = new byte[8 * 1024];

        int len = -1;

        while ((len = srcRaf.read(buffer)) != -1) {

            destRaf.write(buffer, 0, len);

        }

        srcRaf.close();

        destRaf.close();

    }

    /***
     * 复制文件夹
     *
     * @param srcDir
     * @param destDir
     * @throws IOException
     */

    public static void copyDirectory(File srcDir, File destDir)

            throws IOException {

        if (!srcDir.exists()) {

            throw new IllegalArgumentException("文件：" +

                    srcDir + "不存在");

        }

        if (!srcDir.isDirectory()) {

            throw new IllegalArgumentException(srcDir +

                    "不是目录");

        }

        if (!destDir.exists()) {

            if (!destDir.mkdir())

                throw new IOException("无法创建目录：" + destDir);

        }

        File[] subs = srcDir.listFiles();

        if (subs != null && subs.length > 0) {

            for (File sub : subs) {

                if (sub.isFile()) {

                    copyFile(sub, new File(destDir,

                            sub.getName()));

                } else if (sub.isDirectory()) {

                    copyDirectory(sub, new File(destDir,

                            sub.getName()));
                }
            }
        }
    }

    /**
     * 判断目录是否存在
     *
     * @param strDir
     * @return
     */
    public static boolean existsDirectory(String strDir) {
        File file = new File(strDir);
        return file.exists() && file.isDirectory();
    }

    /**
     * 判断文件是否存在
     *
     * @param strDir
     * @return
     */
    public static boolean existsFile(String strDir) {
        File file = new File(strDir);
        return file.exists();
    }

    /**
     * 强制创建目录
     *
     * @param strDir
     * @return
     */
    public static boolean forceDirectory(String strDir) {
        File file = new File(strDir);
        file.mkdirs();
        return existsDirectory(strDir);
    }

    /**
     * 得到文件的大小
     *
     * @param fileName
     * @return
     */
    public static int getFileSize(String fileName) {

        File file = new File(fileName);
        FileInputStream fis = null;
        int size = 0;
        try {
            fis = new FileInputStream(file);
            size = fis.available();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

}

