package com.lvcoding;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.junit.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author wuyanshen
 * @description 描述
 * @date 2021-08-18 上午9:26
 */
public class ZipTest {

    @Test
    public void zip() throws Exception {

        ZipOutputStream zipOutputStream = null;
        FileInputStream fileInputStream = null;

        try {
            File inFile = new File("/Users/wuyanshen/Downloads/aa.txt");
            fileInputStream = new FileInputStream(inFile);
            byte [] bytes1 = new byte[fileInputStream.available()];

            // byte[] bytes = IoUtil.readBytes(fileInputStream);

            File outputFile = new File("/Users/wuyanshen/Downloads/bb.zip");
            zipOutputStream = new ZipOutputStream(new FileOutputStream(outputFile));
            zipOutputStream.putNextEntry(new ZipEntry("bb.txt"));

            int len;
            while ((len = fileInputStream.read(bytes1)) != -1){
                zipOutputStream.write(bytes1, 0, len);
            }

        } catch (Exception e) {

        } finally {
            zipOutputStream.flush();
            zipOutputStream.closeEntry();
            fileInputStream.close();
        }
    }


    @Test
    public void fileUtilTest() {
        // File touch = FileUtil.touch("/Users/wuyanshen/Downloads/test.txt");
        // System.out.println("touch = " + touch);
        // BufferedInputStream inputStream = FileUtil.getInputStream("/Users/wuyanshen/downloads/aa.txt");
        // BufferedOutputStream outputStream = FileUtil.getOutputStream(touch);
        // IoUtil.copy(inputStream, outputStream);

        boolean del = FileUtil.del("/Users/wuyanshen/Downloads/test.txt");
        System.out.println("del = " + del);

        File[] ls = FileUtil.ls("/Users/wuyanshen/Downloads");
        for (File l : ls) {
            System.out.println("l.getName() = " + l.getName());
        }
    }



}
