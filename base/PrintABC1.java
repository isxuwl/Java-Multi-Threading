package base;

/**
 * 方法一：使用synchronized和wait/notify
 * @Author : isxuwl
 * @Date: 2024/11/9 11:06
 * @Model Description:
 * @Description:
 */
public class PrintABC1 {
    // 共享变量， 表示当前改打印哪个字母
    private static int state = 0;

    //共享变量， 作为锁和通信的媒介
    private static final Object lock = new Object();

    public static void main(String[] args) {
        // 创建三个线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //循环100次
                    for (int i = 0; i < 100; i++){
                        //获取锁
                        synchronized (lock){
                            //判断是否轮到自己执行
                            while (state % 3 != 0){
                                //不是则等待
                                lock.wait();
                            }
                            //打印字母
                            System.out.print("A");
                            //修改状态
                            state ++;
                            //唤醒下一个线程
                            lock.notifyAll();
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
                        synchronized (lock){
                            //判断是否轮到自己执行
                            while (state % 3 != 1){
                                //不是则等待
                                lock.wait();
                            }
                            //打印字母
                            System.out.print("B");
                            //修改状态
                            state ++;
                            //唤醒下一个线程
                            lock.notifyAll();
                        }
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
                    //循环100次
                    for (int i = 0; i < 100; i++){
                        //获取锁
                        synchronized (lock){
                            //判断是否轮到自己执行
                            while (state % 3 != 2){
                                //不是则等待
                                lock.wait();
                            }
                            //打印字母
                            System.out.print("C");
                            //修改状态
                            state ++;
                            //唤醒下一个线程
                            lock.notifyAll();
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
