package com.hofev.securelaunch.views;

import com.hofev.securelaunch.controllers.UserController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class LoginForm {
    private JFrame frame;
    private JTextField loginField;
    private JPasswordField passwordField;
    private static JButton loginButton; // Делаем статической для доступа из контроллера

    public LoginForm() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Вход");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.LIGHT_GRAY);
        loginPanel.setLayout(new GridBagLayout());
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel loginLabel = new JLabel("Логин:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(loginLabel, gbc);

        loginField = new JTextField("admin", 15);
        limitTextField(loginField, "[a-zA-Z0-9@._-]*");
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(loginField, gbc);

        JLabel passwordLabel = new JLabel("Пароль:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        loginButton = new JButton("Войти");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Регистрация");
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setFocusPainted(false);
        mainPanel.add(registerButton, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String login = loginField.getText();
            String password = new String(passwordField.getPassword());
            UserController.getInstance().loginUser(login, password, frame);
        });

        registerButton.addActionListener(e -> {
            dispose();
            UserController.getInstance().startRegistration();
        });

        frame.setVisible(true);
    }

    private static void limitTextField(JTextField textField, String regex) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches(regex)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                if (text.matches(regex)) {
                    super.insertString(fb, offset, text, attr);
                }
            }
        });
    }

    public static void printErrorLogin(JFrame frame, int attempt) {
        JOptionPane.showMessageDialog(frame, "Неверный логин или пароль, осталось " + attempt + " попыток.", "Ошибка входа", JOptionPane.ERROR_MESSAGE);
    }

    public static void setLoginButtonEnabled(boolean enabled) {
        if (loginButton != null) {
            loginButton.setEnabled(enabled);
        }
    }

    public void dispose() {
        frame.dispose();
    }
}
