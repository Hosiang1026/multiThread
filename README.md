# multiThread
This a simple multiThread project.

多线程总结

1、继承Thread
2、实现Runnable接口
3、实现Callable接口
4、同步辅助类CountDownLatch、AtomicInteger
5、加锁、线程安全

1. synchronized关键字
2. Java.util.concurrent包中的lock接口和ReentrantLock实现类

使用锁机制 synchronize、lock方式：为资源加锁 

synchronize 全局锁、代码段锁 （锁对象）

 使用 java.util.concurrent 下面的类库：有JDK提供的线程安全的集合类
 
 解决线程不安全问题
  
 在操作系统中，线程是不拥有资源的，进程是拥有资源的。而线程是由进程创建的，一个进程可以创建多个线程，这些线程共享着进程中的资源。所以，当线程一起并发运行时，同时对一个数据进行修改，就可能会造成数据的不一致性
 

 1、给共享的资源加把锁，保证每个资源变量每时每刻至多被一个线程占用。
 2、让线程也拥有资源，不用去共享进程中的资源。
 
 ArrayList与LinkedList 线程不安全
 
 ArrayList更适合读取数据，linkedList更多的时候添加或删除数据
 1.ArrayList是实现了基于动态数组的数据结构，LinkedList基于链表的数据结构。 
 2.对于随机访问get和set，ArrayList觉得优于LinkedList，因为LinkedList要移动指针。 
 3.对于新增和删除操作add和remove，LinedList比较占优势，因为ArrayList要移动数据。 
 
 ArrayList.size() int 
 
 vector、list、map的最大容量是多大
 https://www.cnblogs.com/dingjiaoyang/p/6251419.html
 
 
 实现多线程安全的3种方式
 https://blog.csdn.net/jinggod/article/details/78275763
