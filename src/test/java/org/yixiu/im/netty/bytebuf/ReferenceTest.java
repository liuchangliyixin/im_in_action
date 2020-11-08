package org.yixiu.im.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.yixiu.im.netty.handler.InHandlerDemo;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class ReferenceTest {

    private static Logger logger = Logger.getLogger(InHandlerDemo.class);

    @Test
    public  void testRef()
    {

        ByteBuf buffer  = ByteBufAllocator.DEFAULT.buffer();
        logger.info("after create:"+buffer.refCnt());
        buffer.retain();
        logger.info("after retain:"+buffer.refCnt());
        buffer.release();
        logger.info("after release:"+buffer.refCnt());
        buffer.release();
        logger.info("after release:"+buffer.refCnt());
        //错误:refCnt: 0,不能再retain
        buffer.retain();
        logger.info("after retain:"+buffer.refCnt());
    }
}
