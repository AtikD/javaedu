/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author atik
 */
class Executer {
    private final ICpu cpu;

    public Executer(ICpu cpu) {
        this.cpu = cpu;
    }

    public void run(Iterable<Command> program) {
        for (Command c : program) {
            cpu.exec(c);
        }
    }
    
    public void run(Command c) {
        cpu.exec(c);
    }
}