package frames;

import services.SpecialSet;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Frame with the processing result and field to check if Ip is in the file
 */

public class ResultFrame extends JFrame implements ActionListener {

    private final long rows;
    private final SpecialSet set;
    private final JLabel resultLabel;
    JTextField textField;

    public ResultFrame(long rows, SpecialSet set){
        this.rows = rows;
        this.set = set;
        JLabel counterLabel = new JLabel(String.format("В файле содержалось %d IP адресов", rows));
        JLabel distLabel = new JLabel(String.format("Из них %d уникальных", set.size()));
        resultLabel = new JLabel();
        counterLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        distLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 24));

        JPanel row1Panel = new JPanel();
        row1Panel.add(counterLabel);

        JPanel row2Panel = new JPanel();
        row2Panel.add(distLabel);

        JPanel resultPanel = new JPanel();
        resultPanel.add(resultLabel);

        JPanel testPanel = new JPanel();

        textField = new JTextField(15);
        JButton button = new JButton("Проверить Ip");
        button.addActionListener(this);

        testPanel.add(textField);
        testPanel.add(button);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.add(row1Panel);
        mainPanel.add(row2Panel);
        mainPanel.add(testPanel);
        mainPanel.add(resultPanel);


        this.setTitle("Результаты обработки");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500,300));
        this.setLayout(new BorderLayout());
        this.add(mainPanel);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String pattern = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        String testIp = textField.getText();

        if(testIp.matches(pattern)) {
            resultLabel.setText(set.contains(testIp)
                    ? String.format("Ip %s есть в файле", testIp)
                    : String.format("Ip %s нет в файле", testIp));
        } else {
            resultLabel.setText("Неправильный формат Ip адреса");
        }
    }
}
