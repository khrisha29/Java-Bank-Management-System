import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private double balance;

    public BankAccount(String accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

public class BankManagementSystemGUI extends JFrame implements ActionListener {

    private JTextField txtAccNo, txtHolder, txtAmount;
    private JTextArea outputArea;
    private JButton btnCreate, btnDeposit, btnWithdraw, btnCheck;
    private HashMap<String, BankAccount> accounts = new HashMap<>();

    public BankManagementSystemGUI() {
        setTitle("🏦 Bank Management System");
        setSize(500, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(240, 248, 255));

        JLabel lblTitle = new JLabel("BANK MANAGEMENT SYSTEM", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setBounds(50, 10, 380, 30);
        add(lblTitle);

        JLabel lblAccNo = new JLabel("Account Number:");
        lblAccNo.setBounds(50, 60, 120, 25);
        add(lblAccNo);

        txtAccNo = new JTextField();
        txtAccNo.setBounds(180, 60, 200, 25);
        add(txtAccNo);

        JLabel lblHolder = new JLabel("Holder Name:");
        lblHolder.setBounds(50, 100, 120, 25);
        add(lblHolder);

        txtHolder = new JTextField();
        txtHolder.setBounds(180, 100, 200, 25);
        add(txtHolder);

        JLabel lblAmount = new JLabel("Amount:");
        lblAmount.setBounds(50, 140, 120, 25);
        add(lblAmount);

        txtAmount = new JTextField();
        txtAmount.setBounds(180, 140, 200, 25);
        add(txtAmount);

        btnCreate = new JButton("Create Account");
        btnCreate.setBounds(50, 190, 150, 30);
        btnCreate.addActionListener(this);
        add(btnCreate);

        btnDeposit = new JButton("Deposit");
        btnDeposit.setBounds(220, 190, 100, 30);
        btnDeposit.addActionListener(this);
        add(btnDeposit);

        btnWithdraw = new JButton("Withdraw");
        btnWithdraw.setBounds(340, 190, 100, 30);
        btnWithdraw.addActionListener(this);
        add(btnWithdraw);

        btnCheck = new JButton("Check Balance");
        btnCheck.setBounds(160, 230, 150, 30);
        btnCheck.addActionListener(this);
        add(btnCheck);

        outputArea = new JTextArea();
        outputArea.setBounds(50, 280, 380, 100);
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        add(outputArea);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String accNo = txtAccNo.getText().trim();
        String holder = txtHolder.getText().trim();
        double amount = 0;

        if (txtAmount.getText().trim().length() > 0) {
            try {
                amount = Double.parseDouble(txtAmount.getText());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount entered!");
                return;
            }
        }

        if (e.getSource() == btnCreate) {
            if (accNo.isEmpty() || holder.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter account number and holder name!");
                return;
            }
            if (accounts.containsKey(accNo)) {
                JOptionPane.showMessageDialog(this, "Account already exists!");
            } else {
                accounts.put(accNo, new BankAccount(accNo, holder, amount));
                JOptionPane.showMessageDialog(this, "Account created successfully!");
            }
        }

        else if (e.getSource() == btnDeposit) {
            BankAccount acc = accounts.get(accNo);
            if (acc != null) {
                acc.deposit(amount);
                JOptionPane.showMessageDialog(this, "Deposited ₹" + amount + " successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Account not found!");
            }
        }

        else if (e.getSource() == btnWithdraw) {
            BankAccount acc = accounts.get(accNo);
            if (acc != null) {
                if (acc.withdraw(amount)) {
                    JOptionPane.showMessageDialog(this, "Withdrawn ₹" + amount + " successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance or invalid amount!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Account not found!");
            }
        }

        else if (e.getSource() == btnCheck) {
            BankAccount acc = accounts.get(accNo);
            if (acc != null) {
                outputArea.setText("Account Holder: " + acc.getAccountHolder()
                        + "\nAccount Number: " + acc.getAccountNumber()
                        + "\nBalance: ₹" + acc.getBalance());
            } else {
                JOptionPane.showMessageDialog(this, "Account not found!");
            }
        }

        // Clear input fields after each operation
        txtAccNo.setText("");
        txtHolder.setText("");
        txtAmount.setText("");
    }

    public static void main(String[] args) {
        new BankManagementSystemGUI();
    }
}
