package lab3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
