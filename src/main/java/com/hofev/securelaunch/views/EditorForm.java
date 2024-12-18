package com.hofev.securelaunch.views;

import com.hofev.securelaunch.controllers.UserController;

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

        // Обработка событий

        // Действие выбора файла
        loadButton.addActionListener(e -> {
            String filePath = promptForFilePath();
            if (filePath != null) UserController.getInstance().openFileObj((new File(filePath)), this);
        });

        // Действие редактирования файла
        editButton.addActionListener(e -> {
            if (currentFile == null) showError("Нет загруженного файла для редактирования.");
            UserController.getInstance().editFileObj(this);
        });

        // Действие сохрание файла
        saveButton.addActionListener(e -> {
            if (currentFile == null) {
                showError("Нет загруженного файла для сохранения.");
                return;
            }

            UserController.getInstance().saveFileObj(currentFile, this);
        });
    }

    // Активация кнопки редактирования
    public void setEnableEditButton(boolean isActivate) {
        this.editButton.setEnabled(isActivate);
    }

    // Активация кнопки сохранения
    public void setEnableSaveButton(boolean isActivate) {
        this.saveButton.setEnabled(isActivate);
    }

    // Открытие доступа к редактированию файла
    public void enableEditing(boolean enable) {
        textArea.setEditable(enable);
        if (enable) {
            textArea.requestFocus();
        }
    }

    // Обновление файла, над которым ведется работа
    public void updateCurrentFile(File file) {
        this.currentFile = file;
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
