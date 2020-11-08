package org.yixiu.im.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.junit.Test;


public class SliceTest {
    @Test
    public  void testSlice() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        System.out.println("动作：分配 ByteBuf(9, 100) ==> " + buffer);
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        System.out.println("动作：写入4个字节 (1,2,3,4) ==> " + buffer);
        ByteBuf slice = buffer.slice();
        System.out.println("动作：切片 slice ==> " + slice);
    }

}