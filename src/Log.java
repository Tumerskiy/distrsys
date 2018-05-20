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
        File inforFile = new File("./log/" + people + ".txt");

        BufferedWriter bufferedWriter = null;
        String information = time + ":" + operation + ": " + result + " on" + location;
        if (inforFile.exists()){
            try {
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./log/" + people + ".txt",  true )));
                bufferedWriter.write(information);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                inforFile.createNewFile();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./log/" + people + ".txt",  true )));
                bufferedWriter.write(information);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
