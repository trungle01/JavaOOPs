/*
    Trung:
    4/5: Code from TurtleGraphics
    4/11: No changes needed, done
 */
package simstation;

import mvc.Command;
import mvc.Model;

public class StopCommand extends Command {
    public StopCommand(Model model) {
        super(model);
    }

    @Override
    public void execute() {
        Simulation simulation = (Simulation) model;
        simulation.stop();
    }
}
