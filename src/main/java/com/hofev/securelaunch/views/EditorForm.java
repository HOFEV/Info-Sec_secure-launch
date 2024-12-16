package com.hofev.securelaunch.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class EditorForm extends JFrame {

    private JTextArea textArea;
    private JButton loadButton;
    private JButton editButton;
    private JButton saveButton;
    private File currentFile; // Переменная для хранения текущего файла

    public EditorForm() {
        setTitle("Редактор текста");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        setLocationRelativeTo(null); // Центрирование окна
        setVisible(true);
    }

    private void initComponents() {
        // Основная панель с BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Текстовая область в центре
        textArea = new JTextArea();
        textArea.setEditable(false); // По умолчанию не редактируемая
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Панель кнопок внизу
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Кнопка "Выбрать файл"
        loadButton = new JButton("Выбрать файл");
        buttonsPanel.add(loadButton);

        // Кнопка "Редактировать"
        editButton = new JButton("Редактировать");
        editButton.setEnabled(false); // Деактивирована до выбора файла
        buttonsPanel.add(editButton);

        // Кнопка "Сохранить"
        saveButton = new JButton("Сохранить");
        saveButton.setEnabled(false); // Деактивирована до начала редактирования
        buttonsPanel.add(saveButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Добавление основной панели в окно
        add(mainPanel);

        // Обработчики событий для кнопок
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String filePath = promptForFilePath();
                if (filePath != null) {
                    File file = new File(filePath);
                    if (file.exists() && file.isFile() && file.canRead()) {
                        try {
                            String content = readFileContent(file);
                            showText(content);
                            currentFile = file; // Сохраняем текущий файл
                            editButton.setEnabled(true); // Активируем кнопку редактирования
                            saveButton.setEnabled(false); // Сохранить пока не доступно
                            enableEditing(false); // Блокируем редактирование
                        } catch (IOException ex) {
                            showError("Ошибка при чтении файла: " + ex.getMessage());
                        }
                    } else {
                        showError("Выбранный файл недоступен для чтения.");
                    }
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null) {
                    enableEditing(true);
                    saveButton.setEnabled(true); // Разрешаем сохранение
                } else {
                    showError("Нет загруженного файла для редактирования.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentFile != null) {
                    String updatedText = getText();
                    try {
                        writeFileContent(currentFile, updatedText);
                        enableEditing(false);
                        saveButton.setEnabled(false);
                        JOptionPane.showMessageDialog(EditorForm.this, "Файл успешно сохранён.", "Успех", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        showError("Ошибка при сохранении файла: " + ex.getMessage());
                    }
                } else {
                    showError("Нет загруженного файла для сохранения.");
                }
            }
        });
    }

    /**
     * Чтение содержимого файла.
     *
     * @param file Файл для чтения.
     * @return Содержимое файла в виде строки.
     * @throws IOException Если происходит ошибка ввода/вывода.
     */
    private String readFileContent(File file) throws IOException {
        // Используем Files.readString для удобства и надёжности
        return Files.readString(file.toPath(), StandardCharsets.UTF_8);
    }

    /**
     * Запись содержимого в файл.
     *
     * @param file    Файл для записи.
     * @param content Содержимое для записи.
     * @throws IOException Если происходит ошибка ввода/вывода.
     */
    private void writeFileContent(File file, String content) throws IOException {
        // Используем Files.write с опцией TRUNCATE_EXISTING для перезаписи файла
        Files.writeString(file.toPath(), content, StandardCharsets.UTF_8, java.nio.file.StandardOpenOption.TRUNCATE_EXISTING);
    }

    /**
     * Отображение текста в текстовой области.
     *
     * @param text Текст для отображения.
     */
    public void showText(String text) {
        textArea.setText(text);
    }

    /**
     * Включение или отключение возможности редактирования текстовой области.
     *
     * @param enable true для включения редактирования, false для отключения.
     */
    public void enableEditing(boolean enable) {
        textArea.setEditable(enable);
        if (enable) {
            textArea.requestFocus();
        }
    }

    /**
     * Отображение сообщения об ошибке.
     *
     * @param message Сообщение об ошибке.
     */
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Ошибка", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Открытие диалога выбора файла и получение пути к нему.
     *
     * @return Путь к выбранному файлу или null, если файл не выбран.
     */
    public String promptForFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите текстовый файл");
        // Установка фильтра для текстовых файлов
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Текстовые файлы", "txt", "text"));
        int result = fileChooser.showOpenDialog(this);
        if(result == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    /**
     * Получение текста из текстовой области.
     *
     * @return Текст из текстовой области.
     */
    public String getText() {
        return textArea.getText();
    }
}
