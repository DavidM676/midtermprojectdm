import java.io.*;

public class Main {
    public static void main(String[] args) {
        String url ="https://www.youtube.com/watch?v=LXb3EKWsInQ";
        ProcessBuilder p = new ProcessBuilder();
        p.command (
                "C:\\Users\\student\\IdeaProjects\\midtermprojectdm\\src\\yt-dlp_x86.exe ",
                 url,// also works with "youtube-dl"
                "/c", "dir C:\\Users\\student/Downloads"
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

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
