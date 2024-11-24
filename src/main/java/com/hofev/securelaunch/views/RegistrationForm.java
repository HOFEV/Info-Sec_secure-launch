package com.hofev.securelaunch.views;

import com.hofev.securelaunch.controllers.UserController;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class RegistrationForm {
    private JFrame frame;
    private JTextField loginField, nameField, surnameField, phoneField, emailField;
    private JPasswordField passwordField, confirmPasswordField;

    public RegistrationForm() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Регистрация");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 450);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Основная панель
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(mainPanel);

        // Заголовок
        JLabel headerLabel = new JLabel("Форма регистрации", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Панель данных
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10));
        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Поля ввода
        JLabel loginLabel = new JLabel("Логин:");
        loginField = new JTextField("user123");
        limitTextField(loginField, "[a-zA-Z0-9._-]*"); // Только допустимые символы для логина
        formPanel.add(loginLabel);
        formPanel.add(loginField);

        JLabel nameLabel = new JLabel("Имя:");
        nameField = new JTextField("Иван");
        limitTextField(nameField, "[а-яА-Яa-zA-Z ]*"); // Только буквы и пробелы
        formPanel.add(nameLabel);
        formPanel.add(nameField);

        JLabel surnameLabel = new JLabel("Фамилия:");
        surnameField = new JTextField("Иванов");
        limitTextField(surnameField, "[а-яА-Яa-zA-Z ]*"); // Только буквы и пробелы
        formPanel.add(surnameLabel);
        formPanel.add(surnameField);

        JLabel phoneLabel = new JLabel("Телефон:");
        phoneField = new JTextField("+7 123 456 7890");
        limitTextField(phoneField, "[0-9+() -]*"); // Только цифры и символы
        formPanel.add(phoneLabel);
        formPanel.add(phoneField);

        JLabel emailLabel = new JLabel("Почта:");
        emailField = new JTextField("ivanov@mail.ru");
        limitTextField(emailField, "[a-zA-Z0-9@._-]*"); // Только разрешённые символы
        formPanel.add(emailLabel);
        formPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Пароль:");
        passwordField = new JPasswordField();
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Повторите пароль:");
        confirmPasswordField = new JPasswordField();
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);

        // Панель кнопок
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Кнопка "Назад"
        JButton backButton = new JButton("Назад");
        backButton.setBackground(Color.LIGHT_GRAY);
        backButton.addActionListener(e -> {
            frame.dispose(); // Закрыть текущую форму
            UserController.getInstance().startLoginUser(); // Открыть форму входа
        });
        buttonPanel.add(backButton);

        // Кнопка "Зарегистрироваться"
        JButton registerButton = new JButton("Зарегистрироваться");
        registerButton.setBackground(new Color(70, 130, 180));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.addActionListener(e -> {
            String enteredLogin = loginField.getText();
            String enteredName = nameField.getText();
            String enteredSurname = surnameField.getText();
            String enteredPhone = phoneField.getText();
            String enteredEmail = emailField.getText();
            String enteredPassword = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            // Проверка данных
            if (enteredLogin.isEmpty() || enteredName.isEmpty() || enteredSurname.isEmpty() || enteredPhone.isEmpty() || enteredEmail.isEmpty() || enteredPassword.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Все поля должны быть заполнены!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!enteredPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(frame, "Пароли не совпадают!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JOptionPane.showMessageDialog(frame, "Регистрация прошла успешно!", "Успех", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose(); // Закрыть форму регистрации
            UserController.getInstance().startLoginUser(); // Открытие форма входа
        });
        buttonPanel.add(registerButton);

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
