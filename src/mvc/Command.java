package mvc;

//Simple abstract class as a basis for all commands
public abstract class Command {
    protected Model model;

    public Command(Model model)
    {
        super();
        this.model = model;
    }

    public abstract void execute();
}
