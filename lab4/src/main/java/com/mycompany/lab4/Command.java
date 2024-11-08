/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author atik
 */
class Command {
    String instruction;
    String[] args;

    public Command(String instruction, String... args) {
        this.instruction = instruction;
        this.args = args;
    }

    public String getInstruction() {
        return instruction;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return instruction + " " + String.join(" ", args);
    }
}
