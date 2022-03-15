package sims;

import mvc.*;
import simstation.*;

class Car extends Agent {
    private int speed;
    public Car() {
        super();
        speed = 2;
    }
    public void update() {
        Car neighbor = (Car)world.getNeighbor(this, 20.0);
        if (neighbor != null) {
            // interact with neighbor
            Heading nbrHeading = neighbor.getHeading();
            switch (nbrHeading) {
                case NORTH: heading = Heading.SOUTH; break;
                case SOUTH: heading = Heading.NORTH; break;
                case EAST: heading = Heading.WEST; break;
                case WEST: heading = Heading.EAST; break;
            }
        }
        move(speed);
    }
    public synchronized int getSpeed() { return speed; }
}

class TrafficFactory extends SimulationFactory {
    //public Model makeModel() { return new TrafficSimulation(); }
    public TrafficFactory() {setSim(new TrafficSimulation());}
    public Model makeModel() { return getSim(); }
}
