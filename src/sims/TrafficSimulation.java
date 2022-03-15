package sims;

import mvc.AppPanel;
import simstation.Simulation;
import simstation.SimulationPanel;

public class TrafficSimulation extends Simulation {
    public void populate() {
        for(int i = 0; i < 50; i++) {
            this.add(new Car());
        }
    }
    public static void main(String[] args) {
        AppPanel panel = new SimulationPanel(new TrafficFactory());
        panel.display();
    }
}