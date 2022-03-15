/*
    Trung:
    4/5:    Coded added from Turtle Graphics, to be modified for Simulation
    4/10:   Added code from simulation.java from the assignments page
    4/11:   Added code to help with saving and opening
    4/13:   Added code so pressing start over and over doesn't keep populating the canvas
            with more and more agents
    4/15:   Added code so that the Simulation doesn't break when performing saves and opens
    4/15:   Added code to fix saving and loading
 */

package simstation;

import java.util.*;
import java.util.Timer;
import mvc.*;

import javax.swing.*;

public class Simulation extends Model {

    public static Integer SIZE = 250;
    public static Integer DOT_SIZE = 5;

    private transient Timer timer;  //Transient because Timer is not serializable
    protected int clock;

    public ArrayList<Agent> agentList;
    public Integer NUM_OF_AGENTS= 250;

    /*constructor*/
    public Simulation () {
        agentList = new ArrayList<>();
        clock = 0;
    }

    public void add(Agent a) {
        agentList.add(a);
        a.setWorld(this);
    }
    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new ClockUpdater(), 1000, 1000);
    }

    private void stopTimer() {
        timer.cancel();
        timer.purge();
    }


    private class ClockUpdater extends TimerTask  {
        public void run() {
            clock++;
        }
    }

    public void start()
    {
        System.out.println("start");
        clock = 0;
        if(agentList.size() != 0) { //Account for an empty agentList
            if(timer != null) {     //Accounting for the null timer when a save is loaded
                stopTimer();
            }
            for (Agent a : agentList)
                a.stop();
            agentList = new ArrayList<Agent>();
        }
        populate();
        startTimer();
        for (Agent a : agentList)
            a.start();
    }

    public void stop()
    {
        for(Agent a : agentList)
            a.stop();
        if(timer != null)   //Can't stop a null timer...
            stopTimer();
    }

    public void resume() {
        for (Agent a : agentList)
            a.resume();
        startTimer();

    }
    public synchronized void suspend() {
        for (Agent a : agentList)
            a.suspend();
        if(timer != null)
            stopTimer();
    }

    //public void stats() {
    //    JFrame frame = new JFrame();
    //    JOptionPane.showMessageDialog(frame, "#agents = " + NUM_OF_AGENTS
    //    + "\nclock = " + clock);
    //}

    public String[] stats(){
        JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "#agents = " + NUM_OF_AGENTS
            + "\nclock = " + clock);
        String[] statistics = {NUM_OF_AGENTS.toString(), Integer.toString(clock)};
        return statistics;
    }

    public synchronized Agent getNeighbor(Agent a) {

        double distance;
        double x;
        double y;
        int radius = 10;    //Radius to check nearest neighbor
        ArrayList<Agent> neighborsList = (ArrayList<Agent>)agentList.clone(); //copy agentList
        int random;

        for(Agent b : agentList) {
            if(b != a)
            {
                //Distance formula
                x = Math.pow(Math.abs((a.getX_Pos()-b.getX_Pos())), 2);
                y = Math.pow(Math.abs((a.getY_Pos()-b.getY_Pos())), 2);
                distance = Math.sqrt(x + y);
                if(distance > radius)
                {
                   neighborsList.remove(b); // remove from copied list if it's outside of radius
                }
            }
        }
        //System.out.println(neighborsList.size());
        if(neighborsList.size() != 0) {
            random = Utilities.rng.nextInt(neighborsList.size());  // randomly pick neighbor
            return neighborsList.get(random);
        }
        else
        {
            return null;
        }
    }

    public synchronized Agent getNeighbor(Agent a, double rad) {

        double distance;
        double x;
        double y;
        double radius = rad;    //Radius to check nearest neighbor
        ArrayList<Agent> neighborsList = (ArrayList<Agent>)agentList.clone(); //copy agentList
        int random;

        for(Agent b : agentList) {
            if(b != a)
            {
                //Distance formula
                x = Math.pow(Math.abs((a.getX_Pos()-b.getX_Pos())), 2);
                y = Math.pow(Math.abs((a.getY_Pos()-b.getY_Pos())), 2);
                distance = Math.sqrt(x + y);
                if(distance > radius)
                {
                    neighborsList.remove(b); // remove from copied list if it's outside of radius
                }
            }
        }
        //System.out.println(neighborsList.size());
        if(neighborsList.size() != 0) {
            random = Utilities.rng.nextInt(neighborsList.size());  // randomly pick neighbor
            return neighborsList.get(random);
        }
        else
        {
            return null;
        }
    }

    public void populate() {} // empty method, specified in subclasses. it's called by start

    public Iterator<Agent> iterator() {
        return agentList.iterator();
    }
    public ArrayList<Agent> getAgentList() {
        return agentList;
    }
    public int getClock() { return clock; }



}
