package org.yixiu.im.future;

public class JoinDemo {

    static class HotWater implements Runnable{

        public void run() {
            System.out.println("洗水壶");
            System.out.println("灌水");
            System.out.println("烧水");
            try {
                Thread.sleep(500);
                System.out.println("水开了");
            } catch (InterruptedException e) {
                System.out.println("烧水异常");
            }
            System.out.println("烧水线程结束");
        }
    }

    static class Wash implements Runnable{

        public void run() {
            System.out.println("洗茶壶");
            System.out.println("洗茶杯");
            System.out.println("拿茶叶");
            try {
                Thread.sleep(500);
                System.out.println("洗好了");
            } catch (InterruptedException e) {
                System.out.println("洗茶壶异常");
            }
            System.out.println("洗茶壶线程结束");
        }
    }

    public static void main(String[] args) {
        Thread hotWaterThread = new Thread(new HotWater(),"hot-water-thread");
        Thread washThread = new Thread(new Wash(),"wash-thread");

        hotWaterThread.start();
        washThread.start();

        Thread.currentThread().setName("泡茶线程");

        try {
            hotWaterThread.join();
            washThread.join();
            System.out.println("泡茶喝");
        } catch (InterruptedException e) {
            System.out.println("泡茶异常");
        }
        System.out.println(Thread.currentThread().getName()+"泡茶完成。");
    }
}


