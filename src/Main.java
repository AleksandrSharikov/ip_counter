import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String filename = "src/ip_test.txt";        // file name
        Set<Integer> set = new HashSet<>();

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filename))) {
            int octet = 0;
            int ch;
            int ipInt = 0;

            while ((ch = bis.read()) != -1) {
                if (ch == '\n') {
                    ipInt = (ipInt << 8) | octet;
                    set.add(ipInt);
                    ipInt = 0;
                    octet = 0;
                } else if (ch == '.') {
                    ipInt = (ipInt << 8) | octet;
                    octet = 0;
                } else if(ch != 13){
                    octet = octet * 10 + ch - 48;
                }
            }
            ipInt = (ipInt << 8) | octet;       // assume there is not '/n' after the last row
            set.add(ipInt);

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(set.size());
    }
}
