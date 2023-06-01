import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TransactionHistoryWindow extends JFrame {
  public JPanel contentPane;
  public JTable table;
  public JButton filterButton;

  public TransactionHistoryWindow(BankAccount account) {
    setTitle("Transaction History");
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    setBounds(0, 0, 600, 400);
    contentPane = new JPanel();
    setContentPane(contentPane);
    contentPane.setLayout(null);

    ArrayList<Transaction> transactions = account.getTransactionHistory();
    String[] columnNames = { "Date", "Time", "Type", "Amount"};
    Object[][] data = new Object[transactions.size()][4];

    for (int i = 0; i < transactions.size(); i++) {
      Transaction transaction = transactions.get(i);
      String formattedDate = transaction.getTimestamp().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
      String formattedTime = transaction.getTimestamp().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
      String type = transaction.description;

      data[i][0] = formattedDate;
      data[i][1] = formattedTime;
      data[i][2] = type;
      data[i][3] = transaction.getAmount();
  
    }

    DefaultTableModel model = new DefaultTableModel(data, columnNames);
    table = new JTable(model);
    table.setBounds(10, 10, 560, 300);
    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setBounds(10, 10, 560, 300);
    contentPane.add(scrollPane);

    filterButton = new JButton("Filter by Date");
    filterButton.setBounds(90, 320, 200, 40);
    contentPane.add(filterButton);

    filterButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String startDateString = JOptionPane.showInputDialog("Enter the start date (mm/dd/yyyy):");
        String endDateString = JOptionPane.showInputDialog("Enter the end date (mm/dd/yyyy):");

        if (startDateString != null && !startDateString.isEmpty() && endDateString != null && !endDateString.isEmpty()) {
          LocalDateTime startDate = LocalDateTime.parse(startDateString + "T00:00:00", DateTimeFormatter.ofPattern("MM/dd/yyyy'T'HH:mm:ss"));
            LocalDateTime endDate = LocalDateTime.parse(endDateString + "T23:59:59", DateTimeFormatter.ofPattern("MM/dd/yyyy'T'HH:mm:ss"));

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); 
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
          DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

          for (Transaction transaction : transactions) {
            LocalDateTime transactionDateTime = transaction.getTimestamp();

            if (!transactionDateTime.isBefore(startDate) && !transactionDateTime.isAfter(endDate)) {
              String type = transaction.getAmount() < 0 ? "Withdrawl" : "Deposit";
              Object[] row = { transactionDateTime.format(dateFormatter), transactionDateTime.format(timeFormatter), type, transaction.getAmount() };

              model.addRow(row);
                
            }
          }
        }
      }
    });

    JButton returnButton = new JButton("Return");
    returnButton.setBounds(310, 320, 200, 40);
    contentPane.add(returnButton);

    returnButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });


    
  }
}