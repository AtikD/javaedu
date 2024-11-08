/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author atik
 */
public class NewJFrame extends javax.swing.JFrame {
    
    private javax.swing.JLabel[] memoryLabels;
    private Program prog;
    private Cpu cpu;
    private Executer exec;
    private int currentCommandIndex;
    private boolean currentRegisterIndex[];
    private boolean currentMemoryIndex[];
    
    
    private void initCpu() {
        prog = new Program();
        cpu = new Cpu();
        exec = new Executer(cpu);
        currentCommandIndex = -1;
        clearQueue();
    }
    
    private void clearQueue(){
        currentRegisterIndex = new boolean[4];
        currentMemoryIndex = new boolean[50];
    }
            
    private void updateInstructionFrequencyPanel() {
        System.out.println(1);
        instruction_frequency.removeAll(); // Очищаем панель перед обновлением
        instruction_frequency.setLayout(new java.awt.GridLayout(10, 1));
        // Получаем список инструкций с их частотой
        java.util.Map<String, Long> instructionFrequency = prog.getInstructionFrequency();
        System.out.println(2);
        // Создаем метку заголовка
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Частота появления инструкций");
        titleLabel.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 14));
        instruction_frequency.add(titleLabel);
        System.out.println(3);
        // Проходим по каждой инструкции и создаем метки для них
        for (java.util.Map.Entry<String, Long> entry : instructionFrequency.entrySet()) {
            String instruction = entry.getKey();
            Long frequency = entry.getValue();

            javax.swing.JLabel instructionLabel = new javax.swing.JLabel(instruction + ": " + frequency);
            instructionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            instructionLabel.setFont(new java.awt.Font("sansserif", java.awt.Font.BOLD, 12));
            instruction_frequency.add(instructionLabel);
        }
        System.out.println(4);
        // Обновляем панель
        instruction_frequency.revalidate();
        instruction_frequency.repaint();
    }
    
    protected void initMemoryLabels() {
        memory_values.removeAll();
        memory_values.setLayout(new java.awt.GridLayout(10, 5));

        memoryLabels = new javax.swing.JLabel[50];
        for (int i = 0; i < 50; i++) {
            memoryLabels[i] = new javax.swing.JLabel((i + 1) + ": " + cpu.getMemory(i));
            memoryLabels[i].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            if (currentMemoryIndex[i] == true){
                memoryLabels[i].setForeground(java.awt.Color.red);
            }    
            memory_values.add(memoryLabels[i]);
        }
        memory_values.revalidate();
        memory_values.repaint();
    }
    
    protected void initCommandPanels() {
        commands.getViewport().removeAll();

        javax.swing.JPanel commandContainer = new javax.swing.JPanel();
        commandContainer.setLayout(new javax.swing.BoxLayout(commandContainer, javax.swing.BoxLayout.Y_AXIS));


        for (int index = 0; index < prog.size(); index++) {
            Command command = prog.getCommandAt(index);
            javax.swing.JPanel commandPanel = new javax.swing.JPanel(new java.awt.FlowLayout());

            javax.swing.JLabel commandLabel = new javax.swing.JLabel(command.getInstruction());
            if (index == currentCommandIndex){
                commandLabel.setForeground(java.awt.Color.red);
            }
            commandPanel.add(commandLabel);

            for (String arg : command.getArgs()) {
                javax.swing.JTextField argField = new javax.swing.JTextField(arg, 5);
                argField.setEditable(false);
                commandPanel.add(argField);
            }

            javax.swing.JButton deleteButton = new javax.swing.JButton("X");
            int finalIndex = index;
            deleteButton.addActionListener(e -> {
                prog.remove(finalIndex);
                initCommandPanels();
                updateInstructionFrequencyPanel();
            });
            commandPanel.add(deleteButton);

            javax.swing.JButton moveUpButton = new javax.swing.JButton("<");
            moveUpButton.addActionListener(e -> {
                if (finalIndex > 0) {
                    prog.swapInstructions(finalIndex, finalIndex - 1);
                    initCommandPanels();
                }
            });
            commandPanel.add(moveUpButton);

            javax.swing.JButton moveDownButton = new javax.swing.JButton(">");
            moveDownButton.addActionListener(e -> {
                if (finalIndex < prog.size() - 1) {
                    prog.swapInstructions(finalIndex, finalIndex + 1);
                    initCommandPanels();
                }
            });
            commandPanel.add(moveDownButton);

            commandContainer.add(commandPanel);
        }

        commands.setViewportView(commandContainer);
        commands.revalidate();
        commands.repaint();
    }
    
    private void updateRegisterLabels() {
        VA.setText(String.valueOf(cpu.getMemory(cpu.getRegisterValue(0))));
        VB.setText(String.valueOf(cpu.getMemory(cpu.getRegisterValue(1))));
        VC.setText(String.valueOf(cpu.getMemory(cpu.getRegisterValue(2))));
        VD.setText(String.valueOf(cpu.getRegisterValue(3)));

        // Сбрасываем цвет текста
        VA.setForeground(java.awt.Color.BLACK);
        VB.setForeground(java.awt.Color.BLACK);
        VC.setForeground(java.awt.Color.BLACK);
        VD.setForeground(java.awt.Color.BLACK);

        // Подсвечиваем измененные регистры
        if (currentRegisterIndex[0]) {
            VA.setForeground(java.awt.Color.RED);
        }
        if (currentRegisterIndex[1]) {
            VB.setForeground(java.awt.Color.RED);
        }
        if (currentRegisterIndex[2]) {
            VC.setForeground(java.awt.Color.RED);
        }
        if (currentRegisterIndex[3]) {
            VD.setForeground(java.awt.Color.RED);
        }
    }
    
    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        initCpu();
        initMemoryLabels();
        initCommandPanels();
        updateRegisterLabels();
        updateInstructionFrequencyPanel();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttons = new javax.swing.JPanel();
        restart = new javax.swing.JButton();
        add = new javax.swing.JButton();
        run = new javax.swing.JButton();
        registers_status = new javax.swing.JPanel();
        Rlabel = new javax.swing.JLabel();
        LA = new javax.swing.JLabel();
        LB = new javax.swing.JLabel();
        LC = new javax.swing.JLabel();
        LD = new javax.swing.JLabel();
        VA = new javax.swing.JLabel();
        VB = new javax.swing.JLabel();
        VC = new javax.swing.JLabel();
        VD = new javax.swing.JLabel();
        commands = new javax.swing.JScrollPane();
        memory_status = new javax.swing.JPanel();
        Mlabel = new javax.swing.JLabel();
        memory_values = new javax.swing.JPanel();
        instruction_frequency = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        buttons.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        buttons.setToolTipText("");

        restart.setText("Сбросить выполнение программы");
        restart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                restartActionPerformed(evt);
            }
        });

        add.setText("Добавить инструкцию");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        run.setText("Выполнить очередную инструкцию");
        run.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsLayout = new javax.swing.GroupLayout(buttons);
        buttons.setLayout(buttonsLayout);
        buttonsLayout.setHorizontalGroup(
            buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(add)
                .addGap(18, 18, 18)
                .addGroup(buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(run, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(restart))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        buttonsLayout.setVerticalGroup(
            buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(buttonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(restart)
                    .addComponent(add))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(run)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        registers_status.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Rlabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Rlabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Rlabel.setText("Значение регистров");

        LA.setText("A");

        LB.setText("B");

        LC.setText("C");

        LD.setText("D");

        VA.setText("*");

        VB.setText("*");

        VC.setText("*");

        VD.setText("*");

        javax.swing.GroupLayout registers_statusLayout = new javax.swing.GroupLayout(registers_status);
        registers_status.setLayout(registers_statusLayout);
        registers_statusLayout.setHorizontalGroup(
            registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registers_statusLayout.createSequentialGroup()
                .addGroup(registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(registers_statusLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LA)
                            .addComponent(VA, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LB)
                            .addComponent(VB, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(registers_statusLayout.createSequentialGroup()
                                .addComponent(LC)
                                .addGap(36, 36, 36))
                            .addGroup(registers_statusLayout.createSequentialGroup()
                                .addComponent(VC, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(22, 22, 22)))
                        .addGroup(registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LD, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VD, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(registers_statusLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Rlabel)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        registers_statusLayout.setVerticalGroup(
            registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(registers_statusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Rlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LA)
                    .addComponent(LB)
                    .addComponent(LC)
                    .addComponent(LD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(registers_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(VA)
                    .addComponent(VB)
                    .addComponent(VC)
                    .addComponent(VD))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        memory_status.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        Mlabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Mlabel.setText("Состояние памяти");

        javax.swing.GroupLayout memory_valuesLayout = new javax.swing.GroupLayout(memory_values);
        memory_values.setLayout(memory_valuesLayout);
        memory_valuesLayout.setHorizontalGroup(
            memory_valuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        memory_valuesLayout.setVerticalGroup(
            memory_valuesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout memory_statusLayout = new javax.swing.GroupLayout(memory_status);
        memory_status.setLayout(memory_statusLayout);
        memory_statusLayout.setHorizontalGroup(
            memory_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memory_statusLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Mlabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(memory_values, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        memory_statusLayout.setVerticalGroup(
            memory_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(memory_statusLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Mlabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memory_values, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        instruction_frequency.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout instruction_frequencyLayout = new javax.swing.GroupLayout(instruction_frequency);
        instruction_frequency.setLayout(instruction_frequencyLayout);
        instruction_frequencyLayout.setHorizontalGroup(
            instruction_frequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        instruction_frequencyLayout.setVerticalGroup(
            instruction_frequencyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(commands))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(memory_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registers_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(instruction_frequency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(buttons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(registers_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commands, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(memory_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(instruction_frequency, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void runActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runActionPerformed
        if (currentCommandIndex + 1 < prog.size()) {
            clearQueue();
            currentCommandIndex++;
            Command command = prog.getCommandAt(currentCommandIndex);

            exec.run(command);

            switch(command.getInstruction()){
                case "ld":
                    currentRegisterIndex[cpu.getRegisterIndex(command.getArgs()[0])] = true;
                    break;
                case "mv":
                    currentRegisterIndex[cpu.getRegisterIndex(command.getArgs()[1])] = true;
                    break;
                case "st":
                    currentMemoryIndex[Integer.parseInt(command.getArgs()[1])] = true;
                    break;
                case "init":
                    currentMemoryIndex[Integer.parseInt(command.getArgs()[0])] = true;
                    break;
                case "add":
                case "sub":
                case "mult":
                case "div":
                    System.out.println(cpu.getRegisterValue(3));
                    currentRegisterIndex[3] = true;
                    break;
                case "print":
                    currentMemoryIndex[cpu.getRegisterValue(0)] = true;
                    currentMemoryIndex[cpu.getRegisterValue(1)] = true;
                    currentMemoryIndex[cpu.getRegisterValue(2)] = true;
                    break;
            }
            initMemoryLabels();
            initCommandPanels();
            updateRegisterLabels();
            updateInstructionFrequencyPanel();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Все команды выполнены.", "Информация", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_runActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        AddInstructionDialog dialog = new AddInstructionDialog(this, prog);
        dialog.setVisible(true);
        updateInstructionFrequencyPanel();
    }//GEN-LAST:event_addActionPerformed

    private void restartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_restartActionPerformed
        initCpu();
        initMemoryLabels();
        initCommandPanels();
        updateRegisterLabels();
        updateInstructionFrequencyPanel();
    }//GEN-LAST:event_restartActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LA;
    private javax.swing.JLabel LB;
    private javax.swing.JLabel LC;
    private javax.swing.JLabel LD;
    private javax.swing.JLabel Mlabel;
    private javax.swing.JLabel Rlabel;
    private javax.swing.JLabel VA;
    private javax.swing.JLabel VB;
    private javax.swing.JLabel VC;
    private javax.swing.JLabel VD;
    private javax.swing.JButton add;
    private javax.swing.JPanel buttons;
    private javax.swing.JScrollPane commands;
    private javax.swing.JPanel instruction_frequency;
    private javax.swing.JPanel memory_status;
    private javax.swing.JPanel memory_values;
    private javax.swing.JPanel registers_status;
    private javax.swing.JButton restart;
    private javax.swing.JButton run;
    // End of variables declaration//GEN-END:variables
}
