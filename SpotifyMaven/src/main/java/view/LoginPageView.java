package view;
import model.UserManager;
import javax.swing.*;
import java.awt.*;

public class LoginPageView extends JFrame {
    private final UserManager userManager;

    public LoginPageView(UserManager userManager) {
        this.userManager = userManager;
        //main frame
        JFrame frame = new JFrame("Login or Register");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridLayout(1, 2));


        // login panel
        JPanel loginPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel loginLabel = new JLabel("Login", SwingConstants.CENTER);
        JTextField loginUsernameField = new JTextField();
        loginUsernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        JPasswordField loginPasswordField = new JPasswordField();
        loginPasswordField.setBorder(BorderFactory.createTitledBorder("Password"));
        JButton loginButton = new JButton("Login");

        loginPanel.add(loginLabel);
        loginPanel.add(loginUsernameField);
        loginPanel.add(loginPasswordField);
        loginPanel.add(loginButton);

        // create account panel
        JPanel createAccountPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        JLabel createAccountLabel = new JLabel("Create Account", SwingConstants.CENTER);
        JTextField newUsernameField = new JTextField();
        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBorder(BorderFactory.createTitledBorder("New Password"));
        JButton createAccountButton = new JButton("Create Account");

        createAccountPanel.add(createAccountLabel);
        createAccountPanel.add(newUsernameField);
        createAccountPanel.add(newPasswordField);
        createAccountPanel.add(createAccountButton);


        frame.add(loginPanel);
        frame.add(createAccountPanel);


        //styling

        Color background = Color.decode("#2B3040");
        Color fullButton = Color.decode("#5A24A6");
        Color textColor = Color.decode("#FEF9F2");
        Color button = Color.decode("#9F96D9");

        loginLabel.setForeground(textColor);
        loginLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        loginUsernameField.setForeground(textColor);
        loginPasswordField.setForeground(textColor);
        loginButton.setForeground(textColor);
        loginUsernameField.setBackground(button);
        loginPasswordField.setBackground(button);


        createAccountLabel.setForeground(textColor);
        createAccountLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        newUsernameField.setForeground(textColor);
        newPasswordField.setForeground(textColor);
        createAccountButton.setForeground(textColor);
        newUsernameField.setBackground(button);
        newPasswordField.setBackground(button);
        newUsernameField.setBorder(BorderFactory.createTitledBorder("New Username"));

        loginPanel.setBackground(background);
        createAccountPanel.setBackground(background);
        loginButton.setBackground(fullButton);
        createAccountButton.setBackground(fullButton);

        frame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = loginUsernameField.getText();
            String password = new String(loginPasswordField.getPassword());


            if (userManager.login(username, password)) {
                frame.dispose();
                new PlaylistsPageView(username);
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        createAccountButton.addActionListener(e -> {
            String newUsername = newUsernameField.getText();
            String newPassword = new String(newPasswordField.getPassword());

            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (userManager.createAccount(newUsername, newPassword)) {
                JOptionPane.showMessageDialog(frame, "Account created successfully for " + newUsername + "!", "Success", JOptionPane.INFORMATION_MESSAGE);

                frame.dispose();
                new PlaylistsPageView(newUsername);
            } else {
                JOptionPane.showMessageDialog(frame, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                newUsernameField.setText("");
                newPasswordField.setText("");
            }
        });
    }
}
