package sims;

import java.awt.*;
import java.util.*;
import mvc.*;
import simstation.*;

import javax.swing.*;

// philosopher states:
enum Phase {EATING, THINKING}

class ChopStick { }

class Philosopher extends Agent {

    private ChopStick leftStick, rightStick;
    private Phase phase;
    private int eatCount;

    public Philosopher(String name) {
        super(name);
        phase = Phase.THINKING;
        eatCount = 0;
    }
    public Philosopher() { this(null); }
    public synchronized Phase getPhase() { return phase; }
    public synchronized void setPhase(Phase phase) {
        this.phase = phase;
        world.changed();
    }
    public ChopStick getLeftStick() { return leftStick; }
    public void setLeftStick(ChopStick leftStick) {
        this.leftStick = leftStick;
    }
    public ChopStick getRightStick() { return rightStick; }
    public void setRightStick(ChopStick rightStick) {
        this.rightStick = rightStick;
    }
    public int getEatCount() { return eatCount; }

    public void update() {
        if (getPhase().equals(Phase.THINKING)) {
            synchronized(leftStick) {
                synchronized(rightStick) {
                    setPhase(Phase.EATING);
                    eatCount++;
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ie) {
                        System.err.println(ie.getMessage());
                    }
                }
            }
        }
        setPhase(Phase.THINKING);
    }

    public int getSpeed(){return 0;}
}


class GoldenDragonView extends SimulationView {
    public GoldenDragonView(Model model) { super(model); }

    public void paintComponent(Graphics gc) {
        Color oldColor = gc.getColor();
        GoldenDragon sim = (GoldenDragon)model;
        Iterator<Agent> it = sim.iterator();
        while(it.hasNext()) {
            Agent a = it.next();
            if (((Philosopher)a).getPhase().equals(Phase.EATING))
                gc.setColor(Color.RED);
            else
                gc.setColor(Color.green);
            gc.fillOval(a.getX_Pos(), a.getY_Pos(), 8, 8);
        }
        gc.setColor(oldColor);
    }
}

class GoldenDragonFactory extends SimulationFactory {
    //public Model makeModel() { return new GoldenDragon(); }
    public GoldenDragonFactory() { setSim(new GoldenDragon());}
    public Model makeModel() { return getSim(); }
    public View getView(Model m) { return new GoldenDragonView(getSim());}

    //public View getView(Model model) {return new GoldenDragonView(model); }
    public String getTitle() { return "Dining Philosophers"; }
}

public class GoldenDragon extends Simulation {
    Philosopher[] philosopher;
    ChopStick[] chopstick;
    public static int NumPhilosophers = 5;
    public static String[] Name
            = new String[] {"Socrates", "Lao Tzu", "Wittgenstein", "Ramanuja", "Rumi"};
    // create philosophers & chopsticks
    public GoldenDragon() {
        chopstick = new ChopStick[NumPhilosophers];
        philosopher = new Philosopher[NumPhilosophers];
        for(int i = 0; i < NumPhilosophers; i++) {
            chopstick[i] = new ChopStick();
        }
        double angle = 2 * Math.PI / NumPhilosophers;
        for(int i = 0; i < NumPhilosophers; i++) {
            philosopher[i] = new Philosopher(Name[i]);
            philosopher[i].setRightStick(chopstick[(i == 0)? NumPhilosophers - 1: i - 1]);
            philosopher[i].setLeftStick(chopstick[i]);
            // position in a circle for asthetics:
            double xc = Math.cos(i * angle);
            double yc = Math.sin(i * angle);
            mvc.Point p = new mvc.Point(xc, yc);
            mvc.Point q = p.transform(Simulation.SIZE/2);
            philosopher[i].setX_Pos((int)q.getXc());
            philosopher[i].setY_Pos((int)q.getYc());
        }
    }

    public void populate() {
        for(int i = 0; i < NumPhilosophers; i++) {
            this.add(philosopher[i]);
        }
    }

    @Override
    public String[] stats() {
        //String[] basicStats = super.stats();
        String[] stats = new String[NumPhilosophers + 2];
        stats[0] = Integer.toString(NumPhilosophers);
        stats[1] = Integer.toString(clock);
        JFrame frame = new JFrame();
        for(int i = 2; i < stats.length; i++) {
            stats[i] = philosopher[i - 2].getName() + ".eatCount = " + philosopher[i - 2].getEatCount();
        }

        JOptionPane.showMessageDialog(frame, "#agents = " + NumPhilosophers
                + "\nclock = " + clock + "\n" + philosopher[0].getName() + ".eatCount = " + philosopher[0].getEatCount() +
                "\n" + philosopher[1].getName() + ".eatCount = " + philosopher[1].getEatCount() + "\n" +  philosopher[2].getName() +
                ".eatCount = " + philosopher[2].getEatCount() + "\n" + philosopher[3].getName() + ".eatCount = " + philosopher[3].getEatCount() +
                "\n" + philosopher[4].getName() + ".eatCount = " + philosopher[4].getEatCount());
        return stats;
    }

    public static void main(String[] args) {
        AppPanel panel = new SimulationPanel(new GoldenDragonFactory());
        panel.display();
    }

}
