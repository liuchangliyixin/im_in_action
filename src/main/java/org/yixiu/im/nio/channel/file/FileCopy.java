package org.yixiu.im.nio.channel.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopy {
    static final String SRC_PATH = "D:\\src\\test.pdf";
    static final String DEST_PATH = "D:\\dest\\test.pdf";

    public static void main(String[] args) {
        copyByOio();
        copyByNio();
        copyByNioFast();
    }

    static void copyByOio(){
        File srcFile = new File(SRC_PATH);
        File destFile = new File(DEST_PATH);
        InputStream is = null;
        OutputStream os = null;

        try {
            if(!destFile.exists()){
                destFile.createNewFile();
            }

            long startTime = System.currentTimeMillis();

            is = new FileInputStream(srcFile);
            os = new FileOutputStream(destFile);

            byte[] bytes = new byte[1024*10];
            int length;
            while((length = is.read(bytes)) != -1){
                os.write(bytes,0,length);
            }
            os.flush();

            long endTime = System.currentTimeMillis();
            System.out.println("OIO need " +(endTime-startTime)+ "s.");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    static void copyByNio(){
        File srcFile = new File(SRC_PATH);
        File destFile = new File(DEST_PATH);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;


        try {
            if(!destFile.exists()){
                destFile.createNewFile();
            }

            long startTime = System.currentTimeMillis();

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(1024*10);
            int length = -1;
            while((length = inChannel.read(buffer)) != -1){
                buffer.flip();
                int outLength = 0;
                while((outLength = outChannel.write(buffer)) != 0){
                    //System.out.println("write bytes:" + outLength);
                }
                buffer.clear();
            }
            outChannel.force(true);
            long endTime = System.currentTimeMillis();
            System.out.println("NIO need:" +(endTime-startTime)+ " s.");
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
                inChannel.close();
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static void copyByNioFast(){
        File srcFile = new File(SRC_PATH);
        File destFile = new File(DEST_PATH);

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;


        try {
            if(!destFile.exists()){
                destFile.createNewFile();
            }
            long startTime = System.currentTimeMillis();

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            long size = inChannel.size();
            /*long pos = 0;
            long count = 0;
            while(pos <size){
                count = (size - pos) > 1024*10 ? 1024*10 : (size-pos);
                pos += outChannel.transferFrom(inChannel,pos,count);
            }*/
            outChannel.transferFrom(inChannel,0,size);

            outChannel.force(true);
            long endTime = System.currentTimeMillis();
            System.out.println("NIO fast need:" +(endTime-startTime)+ " s.");
        }catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
                fos.close();
                inChannel.close();
                outChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
