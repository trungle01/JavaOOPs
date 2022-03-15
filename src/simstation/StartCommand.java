/*
    Trung:
    4/4: Pretty much from TurtleGraphics
    4/11: No adjustments needed, done
 */
package simstation;

import mvc.Command;
import mvc.Model;

public class StartCommand extends Command {
    public StartCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation sim = (Simulation)model;
        sim.start();
    }
}
