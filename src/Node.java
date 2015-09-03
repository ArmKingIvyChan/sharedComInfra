

public class Node implements Runnable{

    private int ID;
    private String msg;
    private int MTU;
    private Medium medium;


    public Node(int ID){
        this.ID = ID;
    }

    public void receive(String message){
        msg = message;

    }

    public void run(){
        System.out.println(msg);
    }

}
