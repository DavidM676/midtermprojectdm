import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Welcome to the MidTerm project");
        System.out.println("Would you like to use a video link(1, default) or a already downloaded video(2): ");

        if (scan.nextLine().equals("2")) {
            System.out.println("Enter Video Path");
        } else {
            System.out.println("Enter a video url: ");
            String url = scan.nextLine();
            YoutubeDownloader.Download(url, getOs());
        }

    }




    public static String getOs() {
        String os = (System.getProperty("os.name")).toLowerCase(Locale.ROOT);
        if (os.contains("windows")) {
            return ".exe";
        }
        return "";

    }
}
