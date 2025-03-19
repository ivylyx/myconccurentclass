package src;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final AtomicInteger count=new AtomicInteger(0);
    private final static int max=1000;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("希望开辟的线程数：");
        final int n=sc.nextInt();
        MyThreadPool myThreadPool = new MyThreadPool(16,16,16);

        for(int i=0;i<n;i++){
            final int curI = i;
            Runnable command=()->{
                while(count.get()<=max){
                    synchronized (count) {
                        if(count.get()%n==curI&&count.get()<=max){
                            System.out.println(Thread.currentThread().getName() + " " + count);
                            count.incrementAndGet();
                        }
                    }
                }

            };
            myThreadPool.execute(command);
        }
    }
}
