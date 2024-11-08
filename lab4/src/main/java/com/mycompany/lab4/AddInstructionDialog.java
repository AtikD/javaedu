/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author atik
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddInstructionDialog extends JDialog {
    private JComboBox<String> commandComboBox;
    private JPanel dynamicFieldsPanel;
    private JButton okButton, cancelButton;
    private JTextField addressField, valueField, registerField1, registerField2;
    
    private NewJFrame mainFrame;
    private Program prog;

    public AddInstructionDialog(NewJFrame owner, Program prog) {
        super(owner, "Добавить инструкцию", true);
        
        this.prog = prog;
        this.mainFrame = owner;
        String[] commands = {"init", "ld", "st", "mv", "add", "sub", "mult", "div", "print"};

        commandComboBox = new JComboBox<>(commands);
        commandComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDynamicFields();
            }
        });

        dynamicFieldsPanel = new JPanel();
        dynamicFieldsPanel.setLayout(new GridLayout(2, 2, 5, 5));

        okButton = new JButton("OK");
        cancelButton = new JButton("Отмена");

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addInstructionToProgram();
                dispose();
                mainFrame.initMemoryLabels();
                mainFrame.initCommandPanels();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setLayout(new BorderLayout(10, 10));
        add(commandComboBox, BorderLayout.NORTH);
        add(dynamicFieldsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        initDynamicFields();
        updateDynamicFields();

        pack();
        setLocationRelativeTo(owner);
    }

    
private void addInstructionToProgram() {
    String command = (String) commandComboBox.getSelectedItem();

    switch (command) {
        case "init":
            if (addressField.getText().trim().isEmpty() || valueField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Заполните все необходимые поля для команды init.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            prog.add(new Command("init", addressField.getText(), valueField.getText()));
            break;
            
        case "ld":
        case "st":
            if (registerField1.getText().trim().isEmpty() || addressField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Заполните все необходимые поля для команды " + command + ".", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            prog.add(new Command(command, registerField1.getText(), addressField.getText()));
            break;
            
        case "mv":
            if (registerField1.getText().trim().isEmpty() || registerField2.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Заполните все необходимые поля для команды mv.", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return;
            }
            prog.add(new Command("mv", registerField1.getText(), registerField2.getText()));
            break;
            
        case "add":
        case "sub":
        case "mult":
        case "div":
        case "print":
            prog.add(new Command(command));
            break;
    }
}
    
    private void initDynamicFields() {
        addressField = new JTextField(10);
        valueField = new JTextField(10);
        registerField1 = new JTextField(10);
        registerField2 = new JTextField(10);
    }

    private void updateDynamicFields() {
        dynamicFieldsPanel.removeAll();

        String selectedCommand = (String) commandComboBox.getSelectedItem();

        switch (selectedCommand) {
            case "init":
                dynamicFieldsPanel.add(new JLabel("Адрес:"));
                dynamicFieldsPanel.add(addressField);
                dynamicFieldsPanel.add(new JLabel("Значение:"));
                dynamicFieldsPanel.add(valueField);
                break;
            case "ld":
            case "st":
                dynamicFieldsPanel.add(new JLabel("Регистр:"));
                dynamicFieldsPanel.add(registerField1);
                dynamicFieldsPanel.add(new JLabel("Адрес:"));
                dynamicFieldsPanel.add(addressField);
                break;
            case "mv":
                dynamicFieldsPanel.add(new JLabel("Из регистра:"));
                dynamicFieldsPanel.add(registerField1);
                dynamicFieldsPanel.add(new JLabel("В регистр:"));
                dynamicFieldsPanel.add(registerField2);
                break;
            case "add":
            case "sub":
            case "mult":
            case "div":
            case "print":
                break;
        }

        dynamicFieldsPanel.revalidate();
        dynamicFieldsPanel.repaint();
        pack();
    }
}
