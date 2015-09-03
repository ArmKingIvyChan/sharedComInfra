/**
 * Created by Ivy on 15-9-3.
 */
import java.util.StringTokenizer;

public class ReadConfig {

    private int MTU;
    private int TX_CHAR;
    private StringTokenizer st;          //Reading configuration file and splitting words by spaces
    private int time, sender, receiver;
    private String message;              //Every single word

    public ReadConfig(String str){
        st = new StringTokenizer(str);
    }

    // Set up  transfer time for each character and maximum transfer size
    public void init(){
        if (st.nextToken().equals("TX_CHAR")){
            TX_CHAR = Integer.parseInt(st.nextToken());
        }
        else{
            System.out.println("TX_CHAR wrong!");
            System.exit(-1);
        }

        if (st.nextToken().equals("MTU")){
            MTU = Integer.parseInt(st.nextToken());
        }
        else{
            System.out.println("MTU wrong!");
            System.exit(-1);
        }
    }

    public boolean hasNext(){
        if(st.hasMoreTokens()){
            return true;
        }
        else{
            return false;
        }
    }

    public void command(){
        time = Integer.parseInt(st.nextToken());
        sender = Integer.parseInt(st.nextToken());
        message = st.nextToken();
        receiver = Integer.parseInt(st.nextToken());
    }

    public int getTime(){
        return time;
    }

    public int getSender(){
        return sender;
    }

    public int getReceiver(){
        return receiver;
    }

    public String getMessage(){
        return message;
    }

    public int getTX(){
        return TX_CHAR;
    }

    public int getMTU(){
        return MTU;
    }

    public static void GetWord(String str){
        StringTokenizer st =  new StringTokenizer(str);
        while(st.hasMoreTokens()){
            System.out.println(st.nextToken());
        }
    }

}

