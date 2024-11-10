package start;

import java.util.concurrent.CountDownLatch;

/**
 * JAVA同时启动多个线程（CountDownLatch实现）
 * @Author : isxuwl
 * @Date: 2024/11/10 17:43
 * @Model Description:
 * @Description:
 */
public class CountDownLatchTest {

    public static void main(String[] args) {
        int n = 5;
        CountDownLatch countDownLatch = new CountDownLatch(n);
        for (int i = 0; i < n; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        //等待所有线程就绪
                        countDownLatch.await();
                        System.out.println(Thread.currentThread().getName() + " 开始时间：" + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.setName("线程-" + i);
            thread.start();
            System.out.println(thread.getName() + " 就绪时间：" + System.currentTimeMillis());
            //countDownLatch内部计数器减1
            countDownLatch.countDown();
        }
    }


}

