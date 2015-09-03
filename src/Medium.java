
public class Medium{

    private int node_ID;
    private int MTU, TX_CHAR;
    private char[] buff;
    private int time;
    private int length, count;
    private boolean isfull;
    int i = 0;
    //private String string = "";

    public Medium(int MTU, int TX){
        this.MTU = MTU;
        TX_CHAR = TX;
        buff = new char[MTU];
        clear();
        node_ID = 0;
        this.count = 0;
        isfull = false;
        //i = 0;
        length = 0;
    }



    public boolean isFull(){
        return isfull;

    }

    public void OpenConnection(int id){
        node_ID = id;
    }

    public void setLength(int length){
        this.length =  length;

    }

    public String print(){
        String string = "";
        for(int i =0; i<buff.length; i++){
            string = string + buff[i];
        }
        return string;
    }


    public void transmit(char msg){
        //int i =0;
        if(i<MTU){
            buff[i] = msg;
            i++;
            count = count +1;
        }
        if(i==MTU){
            isfull = true;
            i=0;

        }
        if(count==length){
            i=0;
            node_ID = 0;
            count = 0;
        }
    }


    public boolean isBusy(){
        if(node_ID != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public void CloseConnection(){
        node_ID = 0;
    }

    public void clear(){
        for(int i=0; i<MTU; i++){
            buff[i] = '\0';
        }
    }

    public int getID(){
        return node_ID;
    }


}