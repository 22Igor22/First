package threads.bay.task2;

import java.util.concurrent.Semaphore;

public class Machine extends Thread {
    String name;
    public boolean isStayed = false;
    Semaphore sem;
    int n;

    public Machine(int n, Semaphore sem) {
        this.n = n;
        this.sem = sem;
    }

    int num = 0;

    @Override
    public void run() {
        try {
            while (num < 3) {
                sem.acquire();
                System.out.println(this + " припарковался");
                Thread.sleep(400);
                num++;
                System.out.println(this + " освобождает");
                sem.release();
                Thread.sleep(300);
            }
        } catch (InterruptedException e) {}
    }
}
