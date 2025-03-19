package src;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class MyThreadPool {

    private int corePoolSize;
    private int maxPoolSize;
    private static long timeout=1000;
    private static TimeUnit unit=TimeUnit.MILLISECONDS;
    private BlockingQueue<Runnable> taskQueue;
    private List<Thread>  coreThreadList;
    private List<Thread> supportThreadList;

    public MyThreadPool(int corePoolSize,int maxPoolSize,int taskQueueSize){
        this.corePoolSize=corePoolSize;
        this.maxPoolSize=maxPoolSize;
        this.coreThreadList=new ArrayList<>(corePoolSize);
        this.supportThreadList=new ArrayList<>(maxPoolSize-corePoolSize);
        this.taskQueue=new ArrayBlockingQueue<>(taskQueueSize);
    }

    void execute(Runnable command){
        if(coreThreadList.size()<corePoolSize){
            CoreThread coreThread = new CoreThread();
            coreThread.start();
            coreThreadList.add(coreThread);
        }else if(coreThreadList.size()+supportThreadList.size()<maxPoolSize){
            SupportThread supportThread = new SupportThread();
            supportThread.start();
            supportThreadList.add(supportThread);
        }
        boolean offer = taskQueue.offer(command);
        //拒绝策略
        if(offer){

        }else{

        }
    }
    class CoreThread extends Thread{
        @Override
        public void run() {
            while(true){
                try {
                    Runnable take = taskQueue.take();
                    take.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    class SupportThread extends Thread{
        @Override
        public void run() {
            while(true){
                try {
                    Runnable take = taskQueue.poll(timeout,unit);
                    if (take != null) {
                        take.run();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
