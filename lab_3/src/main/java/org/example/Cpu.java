package org.example;

class Cpu implements ICpu {
    private final int[] registers = new int[4]; // a = registers[0], b = registers[1], c = registers[2], d = registers[3]
    private final int[] memory = new int[50];

    @Override
    public void exec(Command c) {
        switch (c.instruction) {
            case "init":
                init(c.args);
                break;
            case "ld":
                ld(c.args);
                break;
            case "st":
                st(c.args);
                break;
            case "mv":
                mv(c.args);
                break;
            case "add":
                add();
                break;
            case "sub":
                sub();
                break;
            case "mult":
                mult();
                break;
            case "div":
                div();
                break;
            case "print":
                print();
                break;
            default:
                System.out.println("Неизвестная инструкция: " + c.instruction);
        }
    }

    private int getRegisterIndex(String reg) {
        return switch (reg) {
            case "a" -> 0;
            case "b" -> 1;
            case "c" -> 2;
            case "d" -> 3;
            default -> throw new IllegalArgumentException("Неизвестный регистр: " + reg);
        };
    }

    private void init(String[] args) {
        int address = Integer.parseInt(args[0]);
        int value = Integer.parseInt(args[1]);
        if (address >= 0 && address < memory.length) {
            memory[address] = value;
        } else {
            System.out.println("Неверный адрес памяти: " + address);
        }
    }

    private void ld(String[] args) {
        int regIndex = getRegisterIndex(args[0]);
        int address = Integer.parseInt(args[1]);
        if (address >= 0 && address < memory.length) {
            registers[regIndex] = memory[address];
        } else {
            System.out.println("Неверный адрес памяти: " + address);
        }
    }

    private void st(String[] args) {
        int regIndex = getRegisterIndex(args[0]);
        int address = Integer.parseInt(args[1]);
        if (address >= 0 && address < memory.length) {
            memory[address] = registers[regIndex];
        } else {
            System.out.println("Неверный адрес памяти: " + address);
        }
    }

    private void mv(String[] args) {
        int regIndex1 = getRegisterIndex(args[0]);
        int regIndex2 = getRegisterIndex(args[1]);
        registers[regIndex1] = registers[regIndex2];
    }

    private void add() {
        registers[3] = registers[0] + registers[1];
    }

    private void sub() {
        registers[3] = registers[0] - registers[1];
    }

    private void mult() {
        registers[3] = registers[0] * registers[1];
    }

    private void div() {
        if (registers[1] != 0) {
            registers[3] = registers[0] / registers[1];
        } else {
            System.out.println("Ошибка: деление на ноль");
        }
    }

    private void print() {
        System.out.println("Регистры:");
        System.out.println("a = " + registers[0]);
        System.out.println("b = " + registers[1]);
        System.out.println("c = " + registers[2]);
        System.out.println("d = " + registers[3]);
    }
}
