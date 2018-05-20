import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    public static String getCurrentTime(){
        Date date=new Date();
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate=dateFormat.format(date);
        return formattedDate;
    }

    public static void log(String time,String location,String people,String operation,String result){
        File file = new File("./log");
        if (!file.exists()){
            file.mkdir();
        }
        File inforClientFile = new File("./log/" + people + ".txt");
        File inforServerFile = new File("./log/\" + server + \".txt");

        BufferedWriter clientBufferedWriter = null;
        BufferedWriter serverBufferedWriter = null;
        String clientInformation = time + ":" + operation + ": " + result + " on" + location;
        String serverInformation = time + ":" + people + " " + operation + ": " + result ;
        if (inforClientFile.exists()){
            try {
                clientBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./log/" + people + ".txt",  true )));

                clientBufferedWriter.write(clientInformation);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                inforClientFile.createNewFile();
                clientBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./log/" + people + ".txt",  true )));
                clientBufferedWriter.write(clientInformation);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inforClientFile.exists()){
            try {
                serverBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./log/" + location + ".txt",true)));
                try {
                    serverBufferedWriter.write(serverInformation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }else {
            try {
                inforServerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                serverBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./log/" + location + ".txt",true)));
                try {
                    serverBufferedWriter.write(serverInformation);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
