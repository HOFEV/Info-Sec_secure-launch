package com.hofev.securelaunch.modules.activation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ActivationForm {

    private ActivationForm() {}

    // Класс для ограничения количества символов в текстовом поле
    static class LimitDocument extends PlainDocument {
        private int limit;

        LimitDocument(int limit) {
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) return;
            // Проверяем, не превышает ли количество символов лимит
            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }

    public static void showLoginForm() {
        // Создаем основное окно (frame)
        JFrame frame = new JFrame("Активация");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLayout(new GridBagLayout());
        frame.setResizable(false);

        // Используем GridBagLayout для выравнивания компонентов
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Отступы между компонентами
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;

        // Создаем метку для сообщения об активации
        JLabel activationMessage = new JLabel("Не активировано приложение!");
        activationMessage.setFont(new Font("Arial", Font.BOLD, 18));
        frame.add(activationMessage, gbc);

        // Создаем метку для ввода ключа активации
        gbc.gridy++;
        JLabel keyPrompt = new JLabel("Введите ключ активации");
        keyPrompt.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(keyPrompt, gbc);

        // Создаем текстовые поля для ввода частей ключа активации
        gbc.gridy++;
        gbc.gridwidth = 1;
        JTextField part1 = new JTextField(4); // Первое текстовое поле для ключа
        part1.setDocument(new LimitDocument(4)); // Ограничиваем ввод до 4 символов
        part1.setFont(new Font("Arial", Font.PLAIN, 16));
        part1.setPreferredSize(new Dimension(60, 40));
        frame.add(part1, gbc);

        gbc.gridx++;
        JTextField part2 = new JTextField(4); // Второе текстовое поле для ключа
        part2.setDocument(new LimitDocument(4)); // Ограничиваем ввод до 4 символов
        part2.setFont(new Font("Arial", Font.PLAIN, 16));
        part2.setPreferredSize(new Dimension(60, 40));
        frame.add(part2, gbc);

        gbc.gridx++;
        JTextField part3 = new JTextField(4); // Третье текстовое поле для ключа
        part3.setDocument(new LimitDocument(4)); // Ограничиваем ввод до 4 символов
        part3.setFont(new Font("Arial", Font.PLAIN, 16));
        part3.setPreferredSize(new Dimension(60, 40));
        frame.add(part3, gbc);

        gbc.gridx++;
        JTextField part4 = new JTextField(4); // Четвертое текстовое поле для ключа
        part4.setDocument(new LimitDocument(4)); // Ограничиваем ввод до 4 символов
        part4.setFont(new Font("Arial", Font.PLAIN, 16));
        part4.setPreferredSize(new Dimension(60, 40));
        frame.add(part4, gbc);

        // Добавляем DocumentListener для автоматического перехода на следующее поле после ввода 4 символов
        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { moveFocus(e); }
            @Override
            public void removeUpdate(DocumentEvent e) { }
            @Override
            public void changedUpdate(DocumentEvent e) { }

            // Метод для перемещения фокуса на следующее поле
            private void moveFocus(DocumentEvent e) {
                JTextField source = (JTextField) e.getDocument().getProperty("owner");
                if (source.getText().length() == 4) {
                    if (source == part1) {
                        part2.requestFocus();
                    } else if (source == part2) {
                        part3.requestFocus();
                    } else if (source == part3) {
                        part4.requestFocus();
                    }
                }
            }
        };

        // Привязываем DocumentListener к каждому текстовому полю
        part1.getDocument().putProperty("owner", part1);
        part1.getDocument().addDocumentListener(docListener);
        part2.getDocument().putProperty("owner", part2);
        part2.getDocument().addDocumentListener(docListener);
        part3.getDocument().putProperty("owner", part3);
        part3.getDocument().addDocumentListener(docListener);
        part4.getDocument().putProperty("owner", part4);
        part4.getDocument().addDocumentListener(docListener);

        // Создаем кнопку "Активировать"
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        JButton activateButton = new JButton("Активировать");
        activateButton.setFont(new Font("Arial", Font.BOLD, 16));
        activateButton.setPreferredSize(new Dimension(180, 40));
        frame.add(activateButton, gbc);

        // Создаем кнопку "Выйти"
        gbc.gridy++;
        JButton exitButton = new JButton("Выйти");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setPreferredSize(new Dimension(180, 40));
        frame.add(exitButton, gbc);

        // Добавляем обработчик события для кнопки "Выйти"
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Закрыть приложение
            }
        });

        // Добавляем обработчик события для кнопки "Активировать"
        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Проверяем, что все части ключа заполнены
                if (part1.getText().length() < 4 || part2.getText().length() < 4 || part3.getText().length() < 4 || part4.getText().length() < 4) {
                    JOptionPane.showMessageDialog(frame, "Ошибка: ключ введён неправильно. Пожалуйста, введите все 4 символа в каждое поле.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Собираем ключ активации из всех частей
                String key = part1.getText() + "-" + part2.getText() + "-" + part3.getText() + "-" + part4.getText();
                // Здесь можно добавить логику проверки ключа активации
                JOptionPane.showMessageDialog(frame, "Ключ введён: " + key);
            }
        });

        // Устанавливаем окно по центру экрана и делаем его видимым
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}


