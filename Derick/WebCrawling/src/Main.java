import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.Buffer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        File fiveLetters = new File("data/5-letters.txt");
        File total_words = new File("wordle_words.txt");
        if(total_words.createNewFile()){
            System.out.println("New file made");
        }else{
            System.out.println("File already exists");
        }

        FileWriter name = new FileWriter("wordle_words.txt");
        Scanner reader = new Scanner(fiveLetters);
        while ((reader.hasNextLine())) {
            String word = reader.nextLine();
            HttpURLConnection httpClient = (HttpURLConnection) new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word).openConnection();
            httpClient.setRequestMethod("GET");
            int responsecode = httpClient.getResponseCode();
            if(responsecode == 200){
                name.write(word);
                name.write('\n');
                BufferedReader im = new BufferedReader(new InputStreamReader(httpClient.getInputStream()));
                String line = im.readLine();

                System.out.println(line);
                Thread.sleep(10);
            }else if(responsecode == 429){
                while(responsecode == 429){
                    Thread.sleep(1000 * 60 * 5);
                    httpClient = (HttpURLConnection) new URL("https://api.dictionaryapi.dev/api/v2/entries/en/" + word).openConnection();
                    httpClient.setRequestMethod("GET");
                    responsecode = httpClient.getResponseCode();
                }
            }else{
                System.out.println("getrequestfail");
            }
        }
        name.close();
    }
}
