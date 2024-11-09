package base;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 方法五：使用CyclicBarrier
 * @Author : isxuwl
 * @Date: 2024/11/9 11:49
 * @Model Description:
 * @Description:
 */
public class PrintABC5 {
    // 共享变量， 表示当前打印哪个字母
    private static int state = 0;

    //参与线程数量
    private static int threadNum = 3;

    // 循环屏障，指定三个线程为屏障点，以及一个打印字母的屏障动作
    private static final CyclicBarrier barrier = new CyclicBarrier(threadNum, new Runnable() {
        @Override
        public void run() {
            //根据state的值确定打印哪个字母
            switch (state){
                case 0:
                    System.out.print("A");
                    break;
                case 1:
                    System.out.print("B");
                    break;
                case 2:
                    System.out.print("C");
                    break;
            }
            //修改状态
            state = (state + 1) % threadNum;
            // System.out.println(state);
        }
    });

    public static void main(String[] args) {
        // 创建三个线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环100次
                    for (int i = 0; i < threadNum * 100; i ++){
                        // 执行自己的任务
                        // 等待其他线程到达屏障点
                        barrier.await();
                    }
                }catch (InterruptedException | BrokenBarrierException e){
                    e.printStackTrace();
                }
            }
        });


        Thread threadAB= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环100次
                    for (int i = 0; i < threadNum * 100; i ++){
                        // 执行自己的任务
                        // 等待其他线程到达屏障点
                        barrier.await();
                    }
                }catch (InterruptedException | BrokenBarrierException e){
                    e.printStackTrace();
                }
            }
        });


        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环100次
                    for (int i = 0; i < threadNum * 100; i ++){
                        // 执行自己的任务
                        // 等待其他线程到达屏障点
                        barrier.await();
                    }
                }catch (InterruptedException | BrokenBarrierException e){
                    e.printStackTrace();
                }
            }
        });

        threadA.start();
        threadAB.start();
        threadC.start();
    }
}
