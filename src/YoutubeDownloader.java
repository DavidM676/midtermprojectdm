import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class YoutubeDownloader {

    public static void Download(String url, String os) {
        ProcessBuilder p = new ProcessBuilder();
        p.command (
                "src/yt-dlp"+os,
                url

            );
        try {

        Process process = p.start();

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line + "\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Success!");
            System.out.println(output);
            System.exit(0);
        } else {
            //abnormal...
        }

        } catch (
        IOException e) {
        e.printStackTrace();
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
    }}


