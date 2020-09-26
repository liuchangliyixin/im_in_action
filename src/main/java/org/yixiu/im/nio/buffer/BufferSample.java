package org.yixiu.im.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class BufferSample {
    public static void main(String[] args) {
        allocateInt();
        putInt();
        flipInt();
        getInt();
        rewindInt();
        markResetInt();
        clearOrCompactInt();
    }

    /**
     * 使用allocate创建缓冲区
     */
    static void allocateInt(){
        System.out.println("创建大小为10的整型数据缓冲区......");
        IntBuffer buffer = IntBuffer.allocate(10);
        printBufferAttr("allocate",buffer);
    }

    /**
     * 使用put写入数据
     */
    static void putInt(){
        System.out.println("创建大小为10的整型数据缓冲区......");
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("放入数据1-5......");
        for(int i=1;i<=5;i++){
            buffer.put(i);
        }
        printBufferAttr("put",buffer);
    }

    /**
     * 使用flip转换读模式
     */
    static void flipInt(){
        System.out.println("创建大小为10的整型数据缓冲区......");
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("放入数据1-5......");
        for(int i=1;i<=5;i++){
            buffer.put(i);
        }
        System.out.println("转变缓冲区模式为读取模式......");
        buffer.flip();
        printBufferAttr("flip",buffer);
    }

    /**
     * 使用get读取数据
     */
    static void getInt(){
        System.out.println("创建大小为10的整型数据缓冲区......");
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("放入数据1-5......");
        for(int i=1;i<=5;i++){
            buffer.put(i);
        }
        System.out.println("转变缓冲区模式为读取模式......");
        buffer.flip();
        System.out.println("读取数据1-4......");
        for (int i=0;i<4;i++){
            int res = buffer.get();//如果是get(i)，则是绝对读，不会改变position的值
            System.out.println(res);
        }
        printBufferAttr("get",buffer);
    }

    /**
     * 使用rewind -- 倒带 重新读取数据
     */
    static void rewindInt(){
        System.out.println("创建大小为10的整型数据缓冲区......");
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("放入数据1-5......");
        for(int i=1;i<=5;i++){
            buffer.put(i);
        }
        System.out.println("转变缓冲区模式为读取模式......");
        buffer.flip();
        System.out.println("读取数据1-4......");
        for (int i=0;i<4;i++){
            int res = buffer.get();
            System.out.println(res);
        }
        System.out.println("重新读取缓冲区的数据......");
        buffer.rewind();
        for (int i=0;i<4;i++){
            int res = buffer.get();
            System.out.println(res);
        }
        printBufferAttr("rewind",buffer);
    }

    /**
     * 使用mark标记，使用reset重置
     */
    static void markResetInt(){
        System.out.println("创建大小为10的整型数据缓冲区......");
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("放入数据1-5......");
        for(int i=1;i<=5;i++){
            buffer.put(i);
        }
        System.out.println("转变缓冲区模式为读取模式......");
        buffer.flip();
        System.out.println("读取数据1-4......");
        for (int i=0;i<4;i++){
            if(i == 2){
                System.out.println("标记索引2......");
                buffer.mark();
            }
            int res = buffer.get();
            System.out.println(res);
        }
        System.out.println("重置索引2并读取数据......");
        buffer.reset();
        int res = buffer.get();
        System.out.println(res);
        printBufferAttr("mark&reset",buffer);
    }

    /**
     * 使用clear或compact转换写模式
     */
    static void clearOrCompactInt(){
        System.out.println("创建大小为10的整型数据缓冲区......");
        IntBuffer buffer = IntBuffer.allocate(10);
        System.out.println("放入数据1-5......");
        for(int i=1;i<=5;i++){
            buffer.put(i);
        }
        System.out.println("转变缓冲区模式为读取模式......");
        buffer.flip();
        System.out.println("读取数据1-2......");
        for (int i=0;i<2;i++){
            int res = buffer.get();
            System.out.println(res);
        }
        System.out.println("压缩缓冲区......");
        buffer.compact();
        System.out.println("放入数据1-5......");
        for(int i=1;i<=5;i++){
            buffer.put(i);
        }
        printBufferAttr("compact",buffer);//如果是clear清空，position就是5，如果是compact压缩，position就是8
    }

    /**
     * 打印buffer属性
     * @param action
     * @param buffer
     */
    static void printBufferAttr(String action,Buffer buffer){
        System.out.println("action ---- " + action + "..........");
        System.out.println("capacity:"+buffer.capacity()+";limit:"+buffer.limit()+";position:"+buffer.position());
    }
}
