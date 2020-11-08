package org.yixiu.im.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.nio.charset.Charset;

/**
  * @Author yixiu
  * @Description
  * @Date 17:51 2020/11/8
  * @Param
  * @return
  * Heap 和 Direct 使用对比
  **/
public class BufferTypeTest {

   private static Logger logger = Logger.getLogger(BufferTypeTest.class);

   final static Charset UTF_8 = Charset.forName("UTF-8");

    //堆缓冲区
    @Test
    public  void testHeapBuffer() {
        //取得堆内存
        ByteBuf heapBuf =  ByteBufAllocator.DEFAULT.heapBuffer();
        heapBuf.writeBytes("疯狂创客圈:高性能学习社群".getBytes(UTF_8));
        if (heapBuf.hasArray()) {
            //取得内部数组
            byte[] array = heapBuf.array();
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int length = heapBuf.readableBytes();
            logger.info(new String(array,offset,length, UTF_8));
        }
        heapBuf.release();

    }

    //直接缓冲区
    @Test
    public  void testDirectBuffer() {
        ByteBuf directBuf =  ByteBufAllocator.DEFAULT.directBuffer();
        directBuf.writeBytes("疯狂创客圈:高性能学习社群".getBytes(UTF_8));
        if (!directBuf.hasArray()) {
            int length = directBuf.readableBytes();
            byte[] array = new byte[length];
            //读取数据到堆内存
            directBuf.getBytes(directBuf.readerIndex(), array);
            logger.info(new String(array, UTF_8));
        }
        directBuf.release();
    }
}
