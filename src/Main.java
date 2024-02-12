import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        String filename = "src/ip_test.txt";        // file name
        var set = new HashSet<Integer>(){
            public boolean contains(String ipStr){          // Overloaded method to check IP as a string
                int check = 0;
                int i = 0;
                String[] octetStr = ipStr.split("\\.");
                for (String octet : octetStr){
                    check |= Integer.parseInt(octet) << (8 * (3 - i++) );
                }
                return super.contains(check);
            }

            @Override
            public String toString() {                          // to see a list of distinct IPs
                StringBuilder answer = new StringBuilder();
                for (int record : this) {
                    for (int i = 3; i >= 0; i--) {
                        answer.append((record >> 8 * i) & 0xFF) ;
                        answer.append(i == 0 ? '\n' : '.');
                    }
                }
                answer.deleteCharAt(answer.length() - 1);
                return answer.toString();
            }

        };

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
        System.out.printf("File contains %d distinct IPs \n", set.size());
     //   System.out.printf("Set contains: \n%s\n",set.toString());
        System.out.printf("It contains 192.0.2.14 = %b\n",set.contains("192.0.2.14"));
        System.out.printf("It contains 192.0.2.17 = %b",set.contains("192.0.2.17"));

    }
}
