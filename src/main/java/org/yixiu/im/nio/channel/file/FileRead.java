package org.yixiu.im.nio.channel.file;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileRead {
    static final String SRC_PATH = "D:\\src\\access.log";

    public static void main(String[] args) {
        //readByOio();
        readByNio();
    }

    static void readByOio(){
        File srcFile = new File(SRC_PATH);

        try {
            Reader reader = new FileReader(srcFile);
            BufferedReader br = new BufferedReader(reader);
            String data = null;
            while((data = br.readLine()) != null){
                System.out.println(data);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static void readByNio(){
        try {
            RandomAccessFile raf = new RandomAccessFile(SRC_PATH,"rw");
            FileChannel fc = raf.getChannel();

            ByteBuffer buffer = ByteBuffer.allocate(2048);
            int length = -1;
            while((length = fc.read(buffer)) != -1){
                buffer.flip();
                String str = new String(buffer.array(),0,length);
                System.out.print(str);//这里使用println会破坏原有的数据格式
            }

            raf.close();
            fc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
