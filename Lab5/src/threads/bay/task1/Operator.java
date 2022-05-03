package threads.bay.task1;

public class Operator {
    String name;
    public void CallAnsw() {
        name = Thread.currentThread().getName();
        System.out.println(name + " GoodBye");
    }
}
