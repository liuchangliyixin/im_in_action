package org.yixiu.im.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * @author yixiu
 * @title: EchoClient
 * @projectName im_in_action
 * @description: TODO
 * @date 2020/10/1221:41
 */
public class EchoClient {
    public static void main(String[] args) throws IOException {
        new EchoClient().start();
    }

    private void start() throws IOException {
        InetSocketAddress address = new InetSocketAddress("127.0.0.1",8888);
        SocketChannel sc = SocketChannel.open(address);
        sc.configureBlocking(false);
        while(!sc.finishConnect()){

        }
        System.out.println("Client connect finish!");
        Processor processor = new Processor(sc);
        new Thread(processor).start();
    }

    class Processor implements Runnable{

        Selector selector;
        SocketChannel socketChannel;

        public Processor(SocketChannel socketChannel) throws IOException {
            selector = Selector.open();
            this.socketChannel = socketChannel;
            socketChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);
        }

        public void run() {
            try {
                while(!Thread.interrupted()){
                    selector.select();
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterKeys = keys.iterator();
                    while(iterKeys.hasNext()){
                        SelectionKey key = iterKeys.next();
                        if(key.isWritable()){
                            ByteBuffer bf = ByteBuffer.allocate(1024);
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("please input message:");
                            if(scanner.hasNext()){
                                SocketChannel channel = (SocketChannel) key.channel();
                                String msg = scanner.next();
                                bf.put(msg.getBytes());
                                bf.flip();
                                channel.write(bf);
                                bf.clear();
                            }
                        }
                        if(key.isReadable()){
                            SocketChannel channel = (SocketChannel) key.channel();
                            ByteBuffer bf = ByteBuffer.allocate(1024);
                            int length = 0;
                            while((length = channel.read(bf)) > 0){
                                bf.flip();
                                System.out.println("Server echo:" + new String(bf.array(),0,length));
                                bf.clear();
                            }
                        }
                    }
                    keys.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
