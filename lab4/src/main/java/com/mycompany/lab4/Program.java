/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.lab4;

/**
 *
 * @author atik
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Program implements Iterable<Command> {
    private final List<Command> commands = new ArrayList<>();

    public void add(Command c) {
        commands.add(c);
    }
    
    public Map<String, Long> getInstructionFrequency() {
        return commands.stream()
                .collect(Collectors.groupingBy(Command::getInstruction, Collectors.counting()));
    }
    
    public void remove(int index) {
        if (index >= 0 && index < commands.size()) {
            commands.remove(index);
        }
    }
    
    public Command getCommandAt(int index) {
        return commands.get(index);
    }

    public int size() {
        return commands.size();
    }
    
    @Override
    public Iterator<Command> iterator() {
        return commands.iterator();
    }

    public void swapInstructions(int index1, int index2) {
        if (index1 >= 0 && index2 >= 0 && index1 < commands.size() && index2 < commands.size()) {
            Command temp = commands.get(index1);
            commands.set(index1, commands.get(index2));
            commands.set(index2, temp);
        } else {
            throw new IndexOutOfBoundsException("Индексы вне допустимого диапазона");
        }
    }
    public String mostPopularInstruction() {
        return commands.stream()
                .collect(Collectors.groupingBy(Command::getInstruction, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("Нет инструкций");
    }

    public String getMemoryAddressRange() {
        List<Integer> addresses = commands.stream()
                .filter(c -> c.instruction.equals("init") || c.instruction.equals("ld") || c.instruction.equals("st"))
                .map(c -> {
                    if (c.instruction.equals("init")) {
                        return Integer.parseInt(c.args[0]);
                    } else {
                        return Integer.parseInt(c.args[1]); 
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
