package mvc;

//Class to execute commands
public class CommandProcessor {
    public static void execute(Command cmmd)
    {
        cmmd.execute();
    }
}
