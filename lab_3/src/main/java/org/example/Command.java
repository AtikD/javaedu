package org.example;

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
