import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MyFrame extends JFrame implements ActionListener {
    private User user;
    private MyMap map;
    private JButton[] buttons;
    private JPanel titlePanel = new JPanel();
    private JPanel buttonPanel = new JPanel();
    private JLabel textField = new JLabel();
    private boolean userTurn;

    MyFrame(User u, MyMap m) {
        map = m;
        buttons = createButtons();
        user = u;
        userTurn = true;

        ImageIcon image = new ImageIcon("src\\logo.png");
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(0, 0, 0));

        this.setSize(500, 600);
        this.setTitle("Virus war");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        textField.setFont(new Font("Ink Free", Font.BOLD, 35));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Virus war");
        textField.setOpaque(false);

        titlePanel.setBackground(Color.GREEN);
        titlePanel.setBounds(0, 0, 500, 100);

        buttonPanel.setLayout(new GridLayout(10, 10));
        buttonPanel.setBackground(Color.GRAY);
        for (int i = 0; i < 100; i++) {
            buttonPanel.add(buttons[i]);
        }

        buttons[0].setText("x");

        titlePanel.add(textField);

        this.add(titlePanel, BorderLayout.NORTH);
        this.add(buttonPanel);

    }


    public JButton[] createButtons() {
        int id = 0;
        JButton[] buttons = new JButton[100];
        for (int i = 0; i < 100; i++) {
            JButton b = new JButton();
            b.setBackground(Color.cyan);
            b.setName(String.valueOf(id));
            b.setFont(new Font("Georgia", Font.BOLD, 20));
            id++;
            b.addActionListener(this);
            buttons[i] = b;
        }
        return buttons;
    }

    public JButton getButton(int n) {
        return buttons[n];
    }

    public void setTitle(String msg) {
        textField.setText(msg);
        titlePanel.add(textField);
    }

    public void disableButtons(){
        for (JButton b:buttons) {
            b.setEnabled(false);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < 100; i++) {
            if (e.getSource() == buttons[i]) {
                if (map.userPossibleSteps().contains(Integer.valueOf(buttons[i].getName()))) {
                    if (buttons[i].getText().equals("o")) {
                        buttons[i].setBackground(Color.RED);
                        buttons[i].setEnabled(false);
                    } else {
                        buttons[i].setText("x");
                    }
                    user.setUserStepCoordinate(i);
                    user.Move();
                }
            }
        }
    }

}