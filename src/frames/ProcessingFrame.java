package frames;

import services.IpCounter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame shows the progress of file parsing
 */

public class ProcessingFrame extends JFrame {

    int counter = 0;
    JLabel counterLabel;

    int frequency = 30;

    public ProcessingFrame(String fileName) {


        counterLabel = new JLabel("Counter: " + counter);
        counterLabel.setHorizontalAlignment(JLabel.CENTER);
        counterLabel.setFont(new Font("Arial", Font.PLAIN, 24));


        Timer timer = new Timer(frequency, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counterLabel.setText("Прочитано строк: " + IpCounter.getRowCounter());
            }
        });
        timer.start();

        this.setTitle("Обработка файла " + fileName);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setLayout(new BorderLayout());
        this.add(counterLabel, BorderLayout.CENTER);

        this.setVisible(true);
    }

    public void extDispose(){
        this.dispose();
    }

}

