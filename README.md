# Java-Multi-Threading
Java-Multi-Threading题目

## base文件夹下
### 多线程知识：三个线程如何交替打印ABC循环100次 (单个JVM内的实现方法)

这是一个典型的多线程同步的问题，需要保证每个线程在打印字母之前，能够判断是否轮到自己执行，以及在打印字母之后，能够通知下一个线程执行。介绍以下5种方法：

    1. 使用synchronized和wait/notify -> 1
synchronized是Java中的一个关键字，用于实现对共享资源的互斥访问。wait和notify是Object类中的两个方法，用于实现线程间的通信。wait方法会让当前线程释放锁，并进入等待状态，直到被其他线程唤醒。notify方法会唤醒一个在同一个锁上等待的线程。


可以使用一个共享变量state来表示当前应该打印哪个字母，初始值为0。当state为0时，表示轮到A线程打印；当state为1时，表示轮到B线程打印；当state为2时，表示轮到C线程打印。每个线程在打印完字母后，需要将state加1，并对3取模，以便循环。同时，每个线程还需要唤醒下一个线程，并让自己进入等待状态。

    2. 使用ReentrantLock和Condition -> 2
ReentrantLock是Java中的一个类，用于实现可重入的互斥锁。Condition是ReentrantLock中的一个接口，用于实现线程间的条件等待和唤醒。ReentrantLock可以创建多个Condition对象，每个Condition对象可以绑定一个或多个线程，实现对不同线程的精确控制。

我们可以使用一个ReentrantLock对象作为锁，同时创建三个Condition对象，分别绑定A、B、C三个线程。每个线程在打印字母之前，需要调用对应的Condition对象的await方法，等待被唤醒。每个线程在打印字母之后，需要调用下一个Condition对象的signal方法，唤醒下一个线程。

    3. 使用Semaphore -> 3
Semaphore是Java中的一个类，用于实现信号量机制。信号量是一种计数器，用于控制对共享资源的访问。Semaphore可以创建多个信号量对象，每个信号量对象可以绑定一个或多个线程，实现对不同线程的精确控制。

我们可以使用三个Semaphore对象，分别初始化为1、0、0，表示A、B、C三个线程的初始许可数。每个线程在打印字母之前，需要调用对应的Semaphore对象的acquire方法，获取许可。每个线程在打印字母之后，需要调用下一个Semaphore对象的release方法，释放许可。



    4. 使用AtomicInteger和CAS -> 4
AtomicInteger是Java中的一个类，用于实现原子性的整数操作。CAS是一种无锁的算法，全称为Compare And Swap，即比较并交换。CAS操作需要三个参数：一个内存地址，一个期望值，一个新值。如果内存地址的值与期望值相等，就将其更新为新值，否则不做任何操作。

我们可以使用一个AtomicInteger对象来表示当前应该打印哪个字母，初始值为0。当state为0时，表示轮到A线程打印；当state为1时，表示轮到B线程打印；当state为2时，表示轮到C线程打印。每个线程在打印完字母后，需要使用CAS操作将state加1，并对3取模，以便循环。



    5. 使用CyclicBarrier -> 5
CyclicBarrier是Java中的一个类，用于实现多个线程之间的屏障。CyclicBarrier可以创建一个屏障对象，指定一个参与等待线程数和一个到达屏障点时得动作。当所有线程都到达屏障点时，会执行屏障动作，然后继续执行各自的任务。CyclicBarrier可以重复使用，即当所有线程都通过一次屏障后，可以再次等待所有线程到达下一次屏障。

我们可以使用一个CyclicBarrier对象，指定三个线程为参与等待数，以及一个打印字母的到达屏障点动作。每个线程在执行完自己的任务后，需要调用CyclicBarrier对象的await方法，等待其他线程到达屏障点。当所有线程都到达屏障点时，会执行打印字母的屏障动作，并根据state的值判断应该打印哪个字母。然后，每个线程继续执行自己的任务，直到循环结束。需要注意得就是由于打印操作在到达屏障点得动作内执行，所以三个线程得循环次数得乘以参与线程数量，也就是三。


## start文件夹下

AQS，全称是 AbstractQueuedSynchronizer，中文译为抽象队列式同步器。这个抽象类对于JUC并发包非常重要，JUC包中的ReentrantLock，，Semaphore，ReentrantReadWriteLock，CountDownLatch 等等几乎所有的类都是基于AQS实现的。


AQS 中有两个重要的东西，一个以Node为节点实现的链表的队列(CHL队列)，还有一个STATE标志，并且通过CAS来改变它的值。
### JAVA同时启动多个线程
    1. CountDownLatch实现
初始化CountDownLatch时会初始化其内部的计数器，然后每次调用countDown()方法时计数器会减1，当计数器减为0时线程才会开始执行。


CountDownLatch是使用AQS实现的，其使用AQS的状态值来存储计数器的值，在初始化的时候设置计数器的值，调用await()方法将当前线程放入AQS的阻塞队列等待计数器的值变为0后返回。多个线程调用countDown()方法时会原子性地对计数器的值递减1，当计数器的值变为0时，当前线程会调用AQS的doReleaseShared()方法释放资源，从而激活被await()方法阻塞的线程。

    2. CyclicBarrier实现
CyclicBarrier基于独占锁实现，本质底层还是基于AQS，其内部维护了count和parties两个变量，这也就是为什么CyclicBarrier能复用的原因，一开始的时候count等于parties，每当有线程调用await()方法时，count就减1，当count等于0时所有线程开始执行，同时会将parties的值赋给count，从而进行复用。
 