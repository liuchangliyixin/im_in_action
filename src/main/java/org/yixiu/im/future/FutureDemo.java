package org.yixiu.im.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureDemo {

    static class HtoWater implements Callable<Boolean>{

        public Boolean call() throws Exception {
            System.out.println("洗水壶");
            System.out.println("灌水");
            System.out.println("烧水");
            try {
                Thread.sleep(500);
                System.out.println("水开了");
            } catch (InterruptedException e) {
                System.out.println("烧水异常");
                return false;
            }
            System.out.println("烧水线程结束");
            return true;
        }
    }

    static class Wash implements Callable<Boolean>{

        public Boolean call() throws Exception {
            System.out.println("洗茶壶");
            System.out.println("洗茶杯");
            System.out.println("拿茶叶");
            try {
                Thread.sleep(500);
                System.out.println("洗好了");
            } catch (InterruptedException e) {
                System.out.println("洗茶壶异常");
                return false;
            }
            System.out.println("洗茶壶线程结束");
            return true;
        }
    }

    public static void main(String[] args) {
        FutureTask<Boolean> hotWaterTask = new FutureTask<Boolean>(new HtoWater());
        Thread hotWaterThread = new Thread(hotWaterTask,"hot-water-thread");

        FutureTask<Boolean> washTask = new FutureTask<Boolean>(new Wash());
        Thread washThread = new Thread(washTask,"wash-thread");

        hotWaterThread.start();
        washThread.start();

        try {
            boolean hotWater = hotWaterTask.get();
            boolean wash = washTask.get();
            Thread.currentThread().setName("泡茶线程");
            if(hotWater && wash){
                System.out.println("泡茶喝");
            }
        } catch (InterruptedException e) {
            System.out.println("泡茶异常");
        } catch (ExecutionException e) {
            System.out.println("泡茶异常");
        }
        System.out.println(Thread.currentThread().getName()+"泡茶成功");
    }
}
