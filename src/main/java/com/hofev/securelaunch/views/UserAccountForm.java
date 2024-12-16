package com.hofev.securelaunch.views;

import com.hofev.securelaunch.controllers.UserController;
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

    private void createAndShowGUI(String name, String surname, String phone, String email, String userAccessLevel) {
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
        accessLevelLabel = new JLabel(userAccessLevel); // Уровень доступа отображается как метка
        dataPanel.add(accessLevelTitleLabel);
        dataPanel.add(accessLevelLabel);

        // Кнопки управления
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JButton ruleButton = new JButton("Выдать роль");
        JButton editorButton = new JButton("Редактор");
        JButton editButton = new JButton("Редактировать");
        JButton saveButton = new JButton("Сохранить");
        saveButton.setEnabled(false); // Пока не нажата "Редактировать"

        // Если у пользователя нет прав для редактора - кнопка будет заблокирована
        if(!UserController.getInstance().getAccessForEdit(userAccessLevel)) editorButton.setEnabled(false);

        // скрывает кнопку выдачи ролей, в случае если это не админ
        if (!userAccessLevel.equals("ADMIN")) ruleButton.setVisible(false);

        // Заглушка, кнопка пока не имеет практической реализации
        editButton.setEnabled(false);

        buttonPanel.add(ruleButton);
        buttonPanel.add(editorButton);
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        // Обработчики событий
        ruleButton.addActionListener(e -> {
            // Создание списка
            JPopupMenu userMenu = new JPopupMenu();

            // Получение списка пользователей
            String[] userList = UserController.getInstance().getUserList();

            // Добавление пользователей в список
            for (String user : userList) {
                if (user.equals("admin")) continue;
                JMenuItem menuItem = new JMenuItem(user);

                menuItem.addActionListener(e1 -> {
                    String selectedRole = showRoleSelectionDialog(user);

                    if (selectedRole != null) {
                        System.out.println("Выбрана роль: " + selectedRole);
                    }
                });

                userMenu.add(menuItem);
            }

            userMenu.show(ruleButton, 0, ruleButton.getHeight());

        });

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

    // Метод для отображения диалога выбора роли
    private String showRoleSelectionDialog(String user) {
        // Определяем доступные роли
        String[] roles = UserController.getInstance().getAvailableUserRules();

        // Показываем диалог выбора роли
        String selectedRole = (String) JOptionPane.showInputDialog(
                frame,
                "Выберите роль для пользователя: " + user,
                "Выбор роли",
                JOptionPane.PLAIN_MESSAGE,
                null,
                roles,
                roles[0]
        );

        return selectedRole; // Возвращает выбранную роль или null, если пользователь отменил выбор
    }

    public void show() {
        frame.setVisible(true);
    }

    public void dispose() {
        frame.dispose();
    }

}
