package org.yixiu.im.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author yixiu
 * @title: EchoServer
 * @projectName im_in_action
 * @description: TODO
 * @date 2020/10/1320:34
 */
public class EchoServer {

    public static void main(String[] args) throws IOException {
        new EchoServer().start();
    }

    private void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8888);
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(address);
        channel.configureBlocking(false);
        new Thread(new EchoServerReactor(channel)).start();
    }

    //反应器
    class EchoServerReactor implements Runnable{

        private ServerSocketChannel channel;
        private Selector selector;

        public EchoServerReactor(ServerSocketChannel channel) throws IOException {
            this.channel = channel;
            selector = Selector.open();
            SelectionKey key = channel.register(selector, SelectionKey.OP_ACCEPT);
            key.attach(new AcceptorHandler());
        }

        public void run() {
            try {
                while(!Thread.interrupted()){
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> keyIter = keys.iterator();
                    while(keyIter.hasNext()){
                        SelectionKey key = keyIter.next();
                        dispatch(key);
                    }
                    keys.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void dispatch(SelectionKey key) {
            Runnable runnable = (Runnable) key.attachment();
            if(null != runnable)
                runnable.run();
        }

        //新连接 处理器
        class AcceptorHandler implements Runnable{

            public void run() {
                try {
                    SocketChannel socketChannel = channel.accept();
                    if(null != socketChannel)
                        new EchoServerHandler(selector,socketChannel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //IO处理器
    class EchoServerHandler implements Runnable{

        SocketChannel socketChannel;
        SelectionKey sk;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        static final int RECEIVING = 0,SENDING = 1;
        int state = RECEIVING;

        public EchoServerHandler(Selector selector, SocketChannel socketChannel) throws Exception {
            this.socketChannel = socketChannel;
            socketChannel.configureBlocking(false);
            sk = socketChannel.register(selector,0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }

        public void run() {
            try {
                if(state == RECEIVING){
                    int length = 0;
                    while((length = socketChannel.read(buffer)) > 0){
                        String str = new String(buffer.array(),0,length);
                        System.out.println(str);
                    }
                    buffer.flip();
                    sk.interestOps(SelectionKey.OP_WRITE);
                    state = SENDING;
                }else if(state == SENDING){
                    socketChannel.write(buffer);
                    buffer.clear();
                    sk.interestOps(SelectionKey.OP_READ);
                    state = RECEIVING;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
