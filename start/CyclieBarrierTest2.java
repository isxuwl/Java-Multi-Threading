package start;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier 的复用性，假设任务由步骤一和步骤二组成，必须完成步骤一以后才能开始执行步骤二
 * @Author : isxuwl
 * @Date: 2024/11/10 17:46
 * @Model Description:
 * @Description:
 */
public class CyclieBarrierTest2 {

    public static void main(String[] args) {
        int n = 3;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(n, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 唤醒线程时间：" + System.currentTimeMillis());
            }
        });
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread().getName() + " 执行步骤一时间：" + System.currentTimeMillis());
                        Thread.sleep(1000);
                        //等待步骤一完成
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread().getName() + " 执行步骤二时间：" + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("线程-" + i);
            thread.start();
        }
    }
}

