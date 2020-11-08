package org.yixiu.im.netty.bytebuf;

import io.netty.buffer.ByteBuf;
import org.apache.log4j.Logger;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class PrintAttribute {

    private static Logger logger = Logger.getLogger(PrintAttribute.class);

    public static void print(String action, ByteBuf b) {
        logger.info("after ===========" + action + "============");
        logger.info("1.0 isReadable(): " + b.isReadable());
        logger.info("1.1 readerIndex(): " + b.readerIndex());
        logger.info("1.2 readableBytes(): " + b.readableBytes());
        logger.info("2.0 isWritable(): " + b.isWritable());
        logger.info("2.1 writerIndex(): " + b.writerIndex());
        logger.info("2.2 writableBytes(): " + b.writableBytes());
        logger.info("3.0 capacity(): " + b.capacity());
        logger.info("3.1 maxCapacity(): " + b.maxCapacity());
        logger.info("3.2 maxWritableBytes(): " + b.maxWritableBytes());
    }
}
