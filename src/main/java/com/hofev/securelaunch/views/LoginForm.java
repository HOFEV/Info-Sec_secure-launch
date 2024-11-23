package com.hofev.securelaunch.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    public static void main(String[] args) {
        // Запуск GUI в потоке Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(LoginForm::showLoginForm);
    }

    private static void showLoginForm() {
        // Создание основного окна
        JFrame frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

        JTextField loginField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        loginPanel.add(loginField, gbc);

        // Пароль
        JLabel passwordLabel = new JLabel("Пароль:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
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
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = loginField.getText();
                String password = new String(passwordField.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Введите логин и пароль!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    JOptionPane.showMessageDialog(frame, "Добро пожаловать, " + username + "!", "Успешный вход", JOptionPane.INFORMATION_MESSAGE);
                }


            }
        });

        // Обработчик событий для кнопки "Регистрация"
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Переход на форму регистрации...", "Регистрация", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Отображение окна
        frame.setVisible(true);
    }
}


