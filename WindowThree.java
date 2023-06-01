//Ashab Naveed
//Computer Science 20
//Henry Wise Wood High School
//2022-2023 Semester 2
//BankAccountGUI

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class WindowThree extends JFrame {

  private JPanel contentPane;
  protected JTextField textField;


  public void done(){
    dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
  }
  
  public WindowThree() {
    setTitle("Deposit");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(0, 0, 600, 400);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null);
    setContentPane(contentPane);

    JLabel lblAmount = new JLabel("Enter a Withdrawal Amount:");
    lblAmount.setBounds(200, 50, 200, 50);
    contentPane.add(lblAmount);

    textField = new JTextField();
    textField.setBounds(200, 100, 200, 50);
    textField.setColumns(10);
    contentPane.add(textField);

    JButton btnDone = new JButton ("Done");
    btnDone.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        done();
      }
    });
    btnDone.setBounds(200, 200, 200, 50);
    contentPane.add(btnDone);
    
  }
  
}