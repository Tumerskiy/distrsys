import java.io.*;

public class Log {

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
