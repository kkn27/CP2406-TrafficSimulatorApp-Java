package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SimulationPanel extends JPanel {

    public enum State {
        STOPPED, RUNNING, FINISHED
    }

    private State state = State.STOPPED;
    private int scale;
    private ArrayList<Road> roads;
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private ArrayList<TrafficLight> lights;
    private Timer timer;
    private Boolean stop = true;
    private Random random = new Random();
    private static int cycle = 0;
    private int vehiclesToSpawn = 2;
    private int vehiclesSpawned = 0;
    private int vehiclesRemoved = 0;
    private int numberOfCycles = 20;
    private int updateRate = 1000;


    public void loadMap(ArrayList<Road> roads, ArrayList<TrafficLight> lights) {
        this.roads = roads;
        this.lights = lights;
    }

    public void setVehicleSpawn(int spawns) {
        this.vehiclesToSpawn = spawns - 1;
        createVehicle();
    }

    public void setVehicleSpawnRate(int rate) {
        this.numberOfCycles = rate;
    }

    private void createVehicle() {
        int randomVehicle = random.nextInt(3);
        switch (randomVehicle) {
            case 0:
                vehicles.add(new Car(Integer.toString(cycle), roads.get(0)));
                break;
            case 1:
                vehicles.add(new Bus(Integer.toString(cycle), roads.get(0)));
                break;
            case 2:
                vehicles.add(new Motorbike(Integer.toString(cycle), roads.get(0)));
                break;
        }
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void simulate() {
        setLayout(new BorderLayout());
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(1, 0));
        infoPanel.setBorder(BorderFactory.createLoweredSoftBevelBorder());
        JLabel vehicleLabel = new JLabel("Vehicles: ");
        infoPanel.add(vehicleLabel);
        JLabel averageSpeedLabel = new JLabel("Average Speed: ");
        infoPanel.add(averageSpeedLabel);
        JLabel stateLabel = new JLabel("State: " + state);
        infoPanel.add(stateLabel);
        add(infoPanel, BorderLayout.SOUTH);

        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(updateRate / 60, e -> {
            cycle++;
            if (vehicles.size() == 0) {
                state = State.FINISHED;
            } else if (stop) {
                state = State.STOPPED;
            } else {
                state = State.RUNNING;
            }
            stateLabel.setText("State: " + state);
            vehicleLabel.setText("Vehicles: " + getTotalVehicles());
            averageSpeedLabel.setText("Average Speed:" + getAverageSpeed());
            if (vehicles.size() == 0 || stop) {
                timer.stop();
            }
            if (cycle % 30 == 0) { //light operates every x tics
                for (TrafficLight light : lights) {
                    light.operate(random.nextInt());
                    light.printLightStatus();
                }
            }
            for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator.hasNext(); ) {
                Vehicle vehicle = iterator.next();
//                vehicle.setLane(Model.Vehicle.Lane.LEFT);
                vehicle.move();
                vehicle.printStatus();
                if (vehicle.getPosition() + vehicle.getLength() + vehicle.getSpeed() >= vehicle.getCurrentRoad().getLength() && vehicle.getCurrentRoad().getConnectedRoads().isEmpty() && (vehicle.getSpeed() == 0)) {
                    vehicle.getCurrentRoad().getVehiclesOnRoad().remove(vehicle);
                    iterator.remove();
                    vehiclesRemoved++;
                }
            }

            if (cycle % numberOfCycles == 0 && vehiclesSpawned < vehiclesToSpawn) {
                createVehicle();
                vehiclesSpawned++;
            }
            repaint();
        });
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Road road : roads
        ) {
            road.draw(g, scale);
        }

        if (!vehicles.isEmpty()) {
            for (Vehicle vehicle : vehicles
            ) {
                vehicle.draw(g, scale);
            }
        }

        for (TrafficLight light : lights
        ) {
            light.draw(g, scale);
        }
    }

    private int getTotalVehicles() {
        return vehiclesSpawned + 1 - vehiclesRemoved;
    }

    private String getAverageSpeed() {
        int totalSpeed = 0;
        for (Vehicle vehicle : vehicles) {
            totalSpeed = totalSpeed + vehicle.getSpeed();
        }
        if (!vehicles.isEmpty()) {
            int average = totalSpeed / vehicles.size();
            return average * 3.6 + "km/h";
        } else {
            return "0";
        }
    }

    public void setUpdateRate(int updateRate) {
        this.updateRate = updateRate;
    }


    public void setStopSim(Boolean stop) {
        this.stop = stop;
    }


}
