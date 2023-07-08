import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        HttpURLConnection httpClient = (HttpURLConnection) new URL("https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101" ).openConnection();
        httpClient.setRequestMethod("GET");
        int responsecode = httpClient.getResponseCode();
        System.out.println(responsecode);
        if(responsecode == 200) {
            BufferedReader im = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
            String line = im.readLine();


            while (line != null) {
                System.out.println(line);
                line = im.readLine();
                if (line == null) {
                    System.exit(1);
                }// 헤드라인 뉴스 -> 19000 / 18700
            }
            Thread.sleep(10);
        }
    }
}
