package services;

import frames.ProcessingFrame;
import frames.ResultFrame;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Main logic of the file parsing
 */


public class IpCounter implements Runnable{

    private static final SpecialSet set = new SpecialSet();
    private static long rowCounter;
    private final String filePath;
    private final ProcessingFrame processingFrame;

    public IpCounter(String filePath, ProcessingFrame processingFrame){

        this.filePath = filePath;
        this.processingFrame = processingFrame;
    }



    @Override
    public void run() {


        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(filePath))) {
            int octet = 0;
            int ch;
            int ipInt = 0;

            if (bis.available() > 0){
                while ((ch = bis.read()) != -1) {
                    if (ch == '\n') {
                        ipInt = (ipInt << 8) | octet;
                        set.add(ipInt);
                        ipInt = 0;
                        octet = 0;
                        rowCounter++;
                    } else if (ch == '.') {
                        ipInt = (ipInt << 8) | octet;
                        octet = 0;
                    } else if(ch != 13){
                        octet = octet * 10 + ch - 48;
                    }
                }
                ipInt = (ipInt << 8) | octet;       // Assume there is not '\n' after the last row
                set.add(ipInt);
                rowCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ResultFrame rf = new ResultFrame(rowCounter, set);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            processingFrame.extDispose();
        }
    }


    public static long getRowCounter() {
        return rowCounter;
    }
}
