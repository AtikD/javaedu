package lab3;

import java.util.*;
import java.util.stream.Collectors;

interface ICpu {
    void exec(Command c);
}


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


class Program implements Iterable<Command> {
    private final List<Command> commands = new ArrayList<>();

    // Добавление команды в программу
    public void add(Command c) {
        commands.add(c);
    }

    // Реализация Iterable
    @Override
    public Iterator<Command> iterator() {
        return commands.iterator();
    }

    // Метод для получения наиболее частой инструкции
    public String mostPopularInstruction() {
        return commands.stream()
                .collect(Collectors.groupingBy(Command::getInstruction, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Нет инструкций");
    }

    // Метод для получения диапазона используемых адресов памяти
    public String getMemoryAddressRange() {
        List<Integer> addresses = commands.stream()
                .filter(c -> c.instruction.equals("init") || c.instruction.equals("ld") || c.instruction.equals("st"))
                .map(c -> {
                    if (c.instruction.equals("init")) {
                        return Integer.parseInt(c.args[0]); // Для init адрес в args[0]
                    } else {
                        return Integer.parseInt(c.args[1]); // Для ld и st адрес в args[1]
                    }
                })
                .sorted()
                .toList();

        if (addresses.isEmpty()) {
            return "Нет используемых адресов памяти";
        }

        int minAddress = addresses.getFirst();
        int maxAddress = addresses.getLast();
        return "Диапазон адресов памяти: от " + minAddress + " до " + maxAddress;
    }

    // Метод для получения списка инструкций, упорядоченных по частоте появления
    public List<String> getInstructionsByFrequency() {
        return commands.stream()
                .collect(Collectors.groupingBy(Command::getInstruction, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}


class Cpu implements ICpu {
    private final int[] registers = new int[4]; // a = registers[0], b = registers[1], c = registers[2], d = registers[3]
    private final int[] memory = new int[1024];

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
}



// Главный класс для запуска программы
public class Main {
    public static void main(String[] args) {
        // Создаем программу
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

        // Итерируемся по программе и выводим команды
        for (Command c : prog) {
            System.out.println(c);
        }

        // Выводим наиболее частую инструкцию
        System.out.println("Наиболее частая инструкция: " + prog.mostPopularInstruction());

        // Выводим диапазон используемых адресов памяти
        System.out.println(prog.getMemoryAddressRange());

        // Выводим список инструкций, упорядоченный по частоте появления
        System.out.println("Инструкции по частоте появления:");
        List<String> instructionsByFrequency = prog.getInstructionsByFrequency();
        for (String instr : instructionsByFrequency) {
            System.out.println(instr);
        }

        // Запускаем выполнение программы
        ICpu cpu = new Cpu();
        Executer exec = new Executer(cpu);
        exec.run(prog);
    }
}