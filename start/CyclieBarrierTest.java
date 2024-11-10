package start;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 *JAVA同时启动多个线程（CyclicBarrier实现）
 * @Author : isxuwl
 * @Date: 2024/11/10 17:44
 * @Model Description:
 * @Description:
 */
public class CyclieBarrierTest {

    public static void main(String[] args) {
        int n = 5;
        //定义cyclicBarrier，第一个参数是计数器的大小，第二个参数是计数器为0时需要执行的任务
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 唤醒时间：" + System.currentTimeMillis());
            }
        });
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //阻塞线程等待所有线程就绪
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread().getName() + " 开始时间：" + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("线程-" + i);
            thread.start();
            System.out.println(thread.getName() + " 就绪时间：" + System.currentTimeMillis());
        }
    }
}
