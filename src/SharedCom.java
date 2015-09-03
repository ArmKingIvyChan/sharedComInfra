/**
 * Created by Tong Chen on 15-8-31.
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SharedCom {


    public static String txt2String(File file){
        String result = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));     //构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){                             //使用readLine方法，一次读一行
                result = result +s + "\n";
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        File file = new File("Input.txt");
        File writename = new File("output.txt");
        String s = txt2String(file);
        //System.out.println(s);
        ReadConfig config = new ReadConfig(s);
        config.init();
        ArrayList<Integer> time = new ArrayList<Integer>();
        ArrayList<Integer> sender = new ArrayList<Integer>();
        ArrayList<String> message = new ArrayList<String>();
        ArrayList<Integer> receiver = new ArrayList<Integer>();
        while (config.hasNext()){
            config.command();
            time.add(config.getTime());
            sender.add(config.getSender());
            message.add(config.getMessage());
            receiver.add(config.getReceiver());
        }
        int MTU = config.getMTU();
        int TX_CHAR = config.getTX();
        Medium medium = new Medium(MTU, TX_CHAR);

        int n =3;
        Node[] node = new Node[n];
        Thread[] thread = new Thread[n];
        for(int i=0; i<n; i++ ){
            node[i] = new Node(i);
            thread[i] = new Thread(node[i], Integer.toString(i));
        }

        int timer = 0;
        int count = 0;
        int index = 0;
        int pos = 0;
        boolean execute = false;
        while(true){
            for(int i = 0; i<time.size(); i++){
                if(timer == time.get(i) && !medium.isBusy()){
                    medium.OpenConnection(sender.get(i));
                    execute = true;
                    index = i;
                    continue;

                }
                if(timer == time.get(i) && medium.isBusy()){
                    time.set(i, time.get(i)+10);
                    continue;

                }

            }

            if(execute){
                if(10*count <= TX_CHAR ){
                    count++;
                }
                if(10*count > TX_CHAR){
                    medium.setLength(message.get(index).length());

                    if(pos<message.get(index).length()){

                        medium.transmit(message.get(index).charAt(pos));
                        count = 1;
                        //System.out.println(timer+" "+medium.print());
                        if((pos+1)%MTU==0 ){
                            try{
                                writename = writeFile(sender.get(index)+"      "+(timer-MTU*TX_CHAR)+" START_TX    "+medium.print(),writename);
                                writename = writeFile(sender.get(index)+"      "+timer+" END_TX     "+ medium.print(),writename);
                                writename = writeFile(sender.get(index)+"      "+timer+" RX         "+ medium.print()+"\n",writename);}
                            catch (IOException ex){
                                ex.fillInStackTrace();
                            }
                            System.out.println(sender.get(index)+"      "+(timer-MTU*TX_CHAR)+" START_TX    "+medium.print());
                            System.out.println(sender.get(index)+"      "+timer+" END_TX      "+ medium.print());
                            System.out.println(sender.get(index)+"      "+timer+" RX          "+ medium.print()+"\n");
                            medium.clear();
                        }
                        pos++;

                    }

                    if(pos == message.get(index).length()){
                        medium.clear();
                        medium.CloseConnection();
                        pos = 0;
                        count = 1;
                        execute = false;
                    }

                }
            }

            timer = timer + 10;

            if(timer > 8000){
                break;
            }

        }


    }

    public static File writeFile(String output,File writename) throws IOException{
        //writename.createNewFile();
        BufferedWriter out = new BufferedWriter(new FileWriter(writename));
        out.write(output+"\r\n");
        out.flush();
        out.close();
        return writename;
    }
}
