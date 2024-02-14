package frames;

import services.IpCounter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Starting frame with only one button
 */

public class StartFrame extends JFrame implements ActionListener{

    AbstractButton theOnlyButton;
    JPanel panel;

    public StartFrame(){


    theOnlyButton = new JButton("Выберете файл");
    theOnlyButton.addActionListener(this);
    theOnlyButton.setSize(150,30);
    theOnlyButton.setBackground(Color.white);
    theOnlyButton.setFont(new Font("Arial", Font.PLAIN, 24));

        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(theOnlyButton, gbc);

    panel.setSize(280,190);
    panel.setBackground(Color.lightGray);

    panel.add(theOnlyButton);


        this.setTitle("Выбор файла");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,200);
        this.setBackground(Color.white);
        this.add(panel);
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == theOnlyButton){
            JFileChooser chooser = new JFileChooser();
            int flag = chooser.showOpenDialog(null);
            if(flag == JFileChooser.APPROVE_OPTION) {
                ProcessingFrame p = new ProcessingFrame(chooser.getSelectedFile().getName());
                IpCounter ipCounter = new IpCounter(chooser.getSelectedFile().getAbsolutePath(), p);
                Thread thread = new Thread(ipCounter);

                thread.start();

                this.dispose();
            }
        }
    }
}
