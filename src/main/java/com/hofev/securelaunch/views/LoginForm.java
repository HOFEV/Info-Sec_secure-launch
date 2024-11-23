package com.hofev.securelaunch.views;

import com.hofev.securelaunch.controllers.UserController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class LoginForm {
    private JFrame frame;
    private JTextField loginField;
    private JPasswordField passwordField;

    public LoginForm() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Создание основного окна
        frame = new JFrame("Вход");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false); // Запрещаем изменять размер окна

        // Основная панель
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.DARK_GRAY);
        mainPanel.setLayout(new BorderLayout());
        frame.add(mainPanel);

        // Панель для формы входа
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.LIGHT_GRAY);
        loginPanel.setLayout(new GridBagLayout());
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Логин
        JLabel loginLabel = new JLabel("Логин:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(loginLabel, gbc);

        loginField = new JTextField("example@mail.com", 15); // Заполнение типовым значением
        limitTextField(loginField, "[a-zA-Z0-9@._-]*"); // Ограничение: только разрешённые символы
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(loginField, gbc);

        // Пароль
        JLabel passwordLabel = new JLabel("Пароль:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15); // Оставляем пустым
        gbc.gridx = 1;
        gbc.gridy = 1;
        loginPanel.add(passwordField, gbc);

        // Кнопка входа
        JButton loginButton = new JButton("Войти");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(loginButton, gbc);

        // Кнопка регистрации
        JButton registerButton = new JButton("Регистрация");
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setFocusPainted(false);
        mainPanel.add(registerButton, BorderLayout.SOUTH);

        // Обработчик событий для кнопки "Войти"
        loginButton.addActionListener(e -> {
            String username = loginField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Введите логин и пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                JOptionPane.showMessageDialog(frame, "Добро пожаловать, " + username + "!", "Успешный вход", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Обработчик событий для кнопки "Регистрация"
        registerButton.addActionListener(e -> {
            //JOptionPane.showMessageDialog(frame, "Переход на форму регистрации...", "Регистрация", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            UserController.getInstance().startRegistration(); // Переход на регистрацию
        });

        // Отображение окна
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

    public void show() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }
}
