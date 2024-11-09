package base;

import java.util.concurrent.Semaphore;

/**
 * 方法三：使用Semaphore
 * @Author : isxuwl
 * @Date: 2024/11/9 11:39
 * @Model Description:
 * @Description:
 */
public class PrintABC3 {
    private static  int status = 0;

    // 三个信号量对象，分别表示A，B，C三个线程的初始许可数
    private static final Semaphore A = new Semaphore(1);
    private static final Semaphore B = new Semaphore(0);
    private static final Semaphore C = new Semaphore(0);

    public static void main(String[] args) {
        // 创建三个线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环100次
                    for (int i = 0; i < 100; i++) {
                        // 获取许可
                        A.acquire();
                        // 打印字母
                        System.out.print("A");
                        //修改状态
                        status ++;
                        // 释放锁
                        B.release();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环100次
                    for (int i = 0; i < 100; i++) {
                        // 获取许可
                        B.acquire();
                        // 打印字母
                        System.out.print("B");
                        //修改状态
                        status ++;
                        // 释放锁
                        C.release();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });


        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 循环100次
                    for (int i = 0; i < 100; i++) {
                        // 获取许可
                        C.acquire();
                        // 打印字母
                        System.out.print("C");
                        //修改状态
                        status ++;
                        // 释放锁
                        A.release();
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();

    }


}
