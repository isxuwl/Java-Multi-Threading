package base;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方法四：使用AtomicInteger和CAS
 * @Author : isxuwl
 * @Date: 2024/11/9 11:47
 * @Model Description:
 * @Description:
 */
public class PrintABC4 {
    // 共享变量，表示当前应该打印那个字母
    private  static AtomicInteger state = new AtomicInteger(0);

    public static void main(String[] args) {
        // 创建三个线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                // 循环100次
                for (int i = 0; i < 100; ){
                    // 判断是否轮到自己执行
                    if (state.get() % 3 == 0){
                        // 打印字母
                        System.out.print("A");
                        // 修改状态, 使用CAS操作保证原子性
                        state.compareAndSet(state.get(), state.get() + 1);
                        // 计数器 + 1
                        i ++;
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                // 循环100次
                for (int i = 0; i < 100; ){
                    // 判断是否轮到自己执行
                    if (state.get() % 3 == 1){
                        // 打印字母
                        System.out.print("B");
                        // 修改状态, 使用CAS操作保证原子性
                        state.compareAndSet(state.get(), state.get() + 1);
                        // 计数器 + 1
                        i ++;
                    }
                }
            }
        });


        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                // 循环100次
                for (int i = 0; i < 100; ){
                    // 判断是否轮到自己执行
                    if (state.get() % 3 == 2){
                        // 打印字母
                        System.out.print("C");
                        // 修改状态, 使用CAS操作保证原子性
                        state.compareAndSet(state.get(), state.get() + 1);
                        // 计数器 + 1
                        i ++;
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
        threadC.start();
    }
}
