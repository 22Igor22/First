package threads.bay.task1;

public class Client extends Thread implements Runnable {
    String name;
    Operator operator;
    Boolean speach = false;
    long waitTime = 5;

    public Client(Operator operator, String name) {
        this.operator = operator;
        this.name = name;
    }

    public void Speach() {
        String speach = "";
        for (int i = 0; i < (int) (3 + Math.random() * 7); i++) {
            speach += "BlaBla";
        }
        this.speach = true;
    }

    @Override
    public void run() {
        if(Math.random()*10 < waitTime)
        synchronized (operator) {
            Speach();
            operator.CallAnsw();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        else
        if (!speach) {
            System.out.println(Thread.currentThread().getName() + " waiting");
            try {
                sleep(1000);run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
