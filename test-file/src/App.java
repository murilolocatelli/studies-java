import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");

        byte[] contentBytes = Files.readAllBytes(Paths.get("/Users/user/projects/test/test-file/files/BG_Zoom_Simplicidade.png"));
        //byte[] contentBytes = Files.readAllBytes(Paths.get("/Users/user/projects/test/test-file/files/tasks.txt"));

        String contentBytesBase64 = Base64.getEncoder().encodeToString(contentBytes);

        System.out.println(contentBytesBase64);
        //System.out.println(new String(Base64.getDecoder().decode(contentBytesBase64)));
    }
}
