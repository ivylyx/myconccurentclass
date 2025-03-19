package src.rejecthandler;

import src.MyThreadPool;

public interface RejectHandler {
    void handle(Runnable r, MyThreadPool myThreadPool);
}
