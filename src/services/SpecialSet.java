package services;

import java.util.HashSet;

/**
 * A HashSet with two modified methods
 */

public class SpecialSet extends HashSet<Integer> {

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
    public String toString() {                          // To see a list of distinct IPs
        if(this.size() == 0){
            return "";
        }
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
}
