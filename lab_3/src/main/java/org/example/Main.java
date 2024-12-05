package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

interface ICpu {
    void exec(Command c);
}

public class Main {
    public static void main(String[] args) {
        Program prog = new Program();
        prog.add(new Command("init", "10", "20"));
        prog.add(new Command("init", "11", "25"));
        prog.add(new Command("init", "12", "5"));
        prog.add(new Command("ld", "a", "10"));
        prog.add(new Command("ld", "b", "11"));
        prog.add(new Command("ld", "c", "12"));
        prog.add(new Command("add"));
        prog.add(new Command("print"));  // вывод: a=20, b=25, c=5, d=45
        prog.add(new Command("mv", "a", "d"));
        prog.add(new Command("mv", "b", "c"));
        prog.add(new Command("div"));
        prog.add(new Command("print"));  // вывод: a=45, b=5, c=5, d=9

        for (Command c : prog) {
            System.out.println(c);
        }

        System.out.println("Наиболее частая инструкция: " + prog.mostPopularInstruction());

        System.out.println(prog.getMemoryAddressRange());

        System.out.println("Инструкции по частоте появления:");
        List<String> instructionsByFrequency = prog.getInstructionsByFrequency();
        for (String instr : instructionsByFrequency) {
            System.out.println(instr);
        }

        ICpu cpu = new Cpu();
        Executer exec = new Executer(cpu);
        exec.run(prog);
    }
}