//Ashab Naveed
//Computer Science 20
//Henry Wise Wood High School
//2022-2023 Semester 2
//BankAccountGUI

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowListener; 
import java.awt.event.WindowEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent; 
import java.awt.Color;


public class WindowOne extends JFrame implements WindowListener {

  private JPanel contentPane;
  private JButton show;
  private WindowTwo two;
  private WindowThree three;
  JComboBox<String> cb;

  BankAccount savings = new BankAccount("savings");
  BankAccount cheque = new BankAccount("cheque");

  public static void main (String [] args) {
    EventQueue.invokeLater(new Runnable () {
      public void run() {
        try {
          WindowOne frame = new WindowOne();
          frame.setVisible(true);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  public WindowOne() {
    setTitle("Bank");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(0, 0, 600, 400);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null);
    setContentPane(contentPane);

    JButton buttonWithdraw = new JButton("Withdraw");
    buttonWithdraw.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        openWindowThree();
      }
    });
    
    buttonWithdraw.setBounds(100, 240, 200, 40);
    contentPane.add(buttonWithdraw);
    JButton buttonDeposit = new JButton("Deposit");
    buttonDeposit.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        openNewWindow();
      }
    });

    buttonDeposit.setBounds(300, 240, 200, 40);
    contentPane.add(buttonDeposit);

    JButton buttonTransactionHistory = new JButton("Transaction History");
    buttonTransactionHistory.addMouseListener(new MouseAdapter () {
      public void mouseClicked(MouseEvent e) {
        openTransactionHistoryWindow();
      }
    });
    buttonTransactionHistory.setBounds(200, 300, 200, 40);
    contentPane.add(buttonTransactionHistory);

    JLabel lblAccount = new JLabel("Account");
    lblAccount.setBounds(275, 20, 150, 21);
    contentPane.add(lblAccount);

    JLabel lblBalance = new JLabel("Current Balance");
    lblBalance.setBounds(165, 180, 150, 21);
    contentPane.add(lblBalance);

    JLabel lblAcnt = new JLabel("Select an Account");
    lblAcnt.setBounds(315, 180, 150, 21);
    contentPane.add(lblAcnt);

    String[] account = {"Savings", "Chequing"};
    cb = new JComboBox<>(account);
    cb.setBounds(200,60,200,30);
    contentPane.add(cb);

    show = new JButton("Show Balance");
    show.setBounds(200, 130, 200, 20);
    contentPane.add(show);

    show.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {

        if (cb.getItemAt(cb.getSelectedIndex()).equals("Savings")) {
          lblAcnt.setText(Double.toString(savings.getBalance()));
          if (savings.getBalance() < 0) {
            lblAcnt.setForeground(Color.RED);
          } else {
            lblAcnt.setForeground(Color.BLACK);
          }
        } 

        if (cb.getItemAt(cb.getSelectedIndex()).equals("Chequing")) {
          lblAcnt.setText(Double.toString(cheque.getBalance()));

          if (cheque.getBalance() < 0) {
            lblAcnt.setForeground(Color.RED);
          } else {
            lblAcnt.setForeground(Color.BLACK);
          }
        }
      }
    });
  }
  
    public void openNewWindow(){
      two = new WindowTwo();
      two.addWindowListener(this);
      two.setVisible(true);
    }

    public void openWindowThree(){
      three = new WindowThree();
      three.addWindowListener(this);
      three.setVisible(true);
    }

    public void openTransactionHistoryWindow() {
      String selectedAccount = cb.getItemAt(cb.getSelectedIndex());
      BankAccount account;

      if (selectedAccount.equals("Savings")) {
        account = savings;
      } else {
        account = cheque;
      }

      TransactionHistoryWindow historyWindow = new TransactionHistoryWindow(account);
      historyWindow.setVisible(true);
    }

    public void windowOpened(WindowEvent e) {
      
    }

    public void windowClosing(WindowEvent e) {
      savings.saveBalance();
      cheque.saveBalance();
    }

   public void windowClosed(WindowEvent e){

     if (e.getWindow() == three) {
       String withdrawAmount = three.textField.getText();

       if (cb.getItemAt(cb.getSelectedIndex()).equals("Savings")) {
         savings.withdraw(Double.parseDouble(withdrawAmount), "Withdrawal");
       }

       if (cb.getItemAt(cb.getSelectedIndex()).equals("Chequing")) {
         cheque.withdraw(Double.parseDouble(withdrawAmount), "Withdrawal");
       }
     } else if (e.getWindow() == two) {
       String depositAmount = two.textField.getText();

       if (cb.getItemAt(cb.getSelectedIndex()).equals("Savings")) {
         savings.deposit(Double.parseDouble(depositAmount), "Deposit");
       }
             if (cb.getItemAt(cb.getSelectedIndex()).equals("Chequing")) {
         cheque.deposit(Double.parseDouble(depositAmount), "Deposit");
       }
     }
     
   }

  public void windowIconified(WindowEvent e) {
    
  }

  public void windowDeiconified(WindowEvent e) {
    
  }
  
  public void windowActivated(WindowEvent e) {
    
  }
  
  public void windowDeactivated(WindowEvent e) {
    
  }
  
}