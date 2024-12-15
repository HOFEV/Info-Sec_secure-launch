package com.hofev.securelaunch.views;

import javax.swing.*;
import java.awt.*;

public class UserAccountForm {
    private JFrame frame;
    private JTextField nameField, surnameField, phoneField, emailField;
    private JLabel accessLevelLabel;

    public UserAccountForm(String name, String surname, String phone, String email, String accessLevel) {
        createAndShowGUI(name, surname, phone, email, accessLevel);
    }

    public UserAccountForm(String[] dataUser) {
        this(dataUser[0], dataUser[1], dataUser[2], dataUser[3], dataUser[4]);
    }

    private void createAndShowGUI(String name, String surname, String phone, String email, String accessLevel) {
        frame = new JFrame("Личный кабинет");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 300);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Основная панель
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(mainPanel);

        // Заголовок
        JLabel headerLabel = new JLabel("Личный кабинет", JLabel.LEFT);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Панель данных
        JPanel dataPanel = new JPanel();
        dataPanel.setLayout(new GridLayout(5, 2, 10, 10));
        dataPanel.setBorder(BorderFactory.createTitledBorder("Личные данные"));
        mainPanel.add(dataPanel, BorderLayout.CENTER);

        // Данные пользователя
        JLabel nameLabel = new JLabel("Имя:");
        nameField = new JTextField(name);
        nameField.setEditable(false);
        dataPanel.add(nameLabel);
        dataPanel.add(nameField);

        JLabel surnameLabel = new JLabel("Фамилия:");
        surnameField = new JTextField(surname);
        surnameField.setEditable(false);
        dataPanel.add(surnameLabel);
        dataPanel.add(surnameField);

        JLabel phoneLabel = new JLabel("Телефон:");
        phoneField = new JTextField(phone);
        phoneField.setEditable(false);
        dataPanel.add(phoneLabel);
        dataPanel.add(phoneField);

        JLabel emailLabel = new JLabel("Почта:");
        emailField = new JTextField(email);
        emailField.setEditable(false);
        dataPanel.add(emailLabel);
        dataPanel.add(emailField);

        JLabel accessLevelTitleLabel = new JLabel("Уровень доступа:");
        accessLevelLabel = new JLabel(accessLevel); // Уровень доступа отображается как метка
        dataPanel.add(accessLevelTitleLabel);
        dataPanel.add(accessLevelLabel);

        // Кнопки управления
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton editButton = new JButton("Редактировать");
        JButton saveButton = new JButton("Сохранить");
        saveButton.setEnabled(false); // Пока не нажата "Редактировать"

        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        // Обработчики событий
        editButton.addActionListener(e -> {
            nameField.setEditable(true);
            surnameField.setEditable(true);
            phoneField.setEditable(true);
            emailField.setEditable(true);
            saveButton.setEnabled(true);
        });

        saveButton.addActionListener(e -> {
            String updatedName = nameField.getText();
            String updatedSurname = surnameField.getText();
            String updatedPhone = phoneField.getText();
            String updatedEmail = emailField.getText();
            String updatedAccessLevel = accessLevelLabel.getText();

            // Вывод сохранённых данных
            JOptionPane.showMessageDialog(frame, String.format(
                    "Данные сохранены:\nИмя: %s\nФамилия: %s\nТелефон: %s\nПочта: %s\nУровень доступа: %s",
                    updatedName, updatedSurname, updatedPhone, updatedEmail, updatedAccessLevel));

            // Отключить редактирование
            nameField.setEditable(false);
            surnameField.setEditable(false);
            phoneField.setEditable(false);
            emailField.setEditable(false);
            saveButton.setEnabled(false);
        });

        // Отображение окна
        frame.setVisible(true);
    }

    public void show() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

}
