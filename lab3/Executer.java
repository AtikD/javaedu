package lab3;

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
