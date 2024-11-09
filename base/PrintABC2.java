package base;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 方法二：使用ReentrantLock和Condition
 * @Author : isxuwl
 * @Date: 2024/11/9 11:28
 * @Model Description:
 * @Description:
 */
public class PrintABC2 {
    // 共享变量， 表示当前应该打印哪个字母
    private static int state = 0;

    // 可重入锁
    private static final ReentrantLock lock = new ReentrantLock();

    // 三个条件对象， 分别绑定A B C三个线程
    private static final Condition A = lock.newCondition();
    private static final Condition B = lock.newCondition();
    private static final Condition C = lock.newCondition();

    public static void main(String[] args) {
        Thread threadA= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //循环100次
                    for (int i = 0; i < 100; i++){
                        //获取锁
                        lock.lock();
                        try {
                            //判断是否轮到自己执行
                            while (state % 3 != 0){
                                //不是则等待
                                A.await();
                            }
                            //打印字母
                            System.out.print("A");
                            //修改状态
                            state ++;
                            //唤醒下一个线程
                            B.signal();
                        }finally {
                            //释放锁
                            lock.unlock();
                        }

                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        Thread threadB= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //循环100次
                    for (int i = 0; i < 100; i++){
                        //获取锁
                        lock.lock();
                        try {
                            //判断是否轮到自己执行
                            while (state % 3 != 1){
                                //不是则等待
                                B.await();
                            }
                            //打印字母
                            System.out.print("B");
                            //修改状态
                            state ++;
                            //唤醒下一个线程
                            C.signal();
                        }finally {
                            //释放锁
                            lock.unlock();
                        }

                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });


        Thread threadC= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //循环100次
                    for (int i = 0; i < 100; i++){
                        //获取锁
                        lock.lock();
                        try {
                            //判断是否轮到自己执行
                            while (state % 3 != 2){
                                //不是则等待
                                C.await();
                            }
                            //打印字母
                            System.out.print("C");
                            //修改状态
                            state ++;
                            //唤醒下一个线程
                            A.signal();
                        }finally {
                            //释放锁
                            lock.unlock();
                        }

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
