package org.firstinspires.ftc.teamcode.testing;

import org.firstinspires.ftc.teamcode.core.SensorInterface;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class FakeDistanceSensor implements SensorInterface{
    private static final int SENSOR_HISTORY_SIZE = 1;
    private Deque<Double> sensorHistory;

    @Override
    public void init() {
        sensorHistory = new ArrayDeque(SENSOR_HISTORY_SIZE);
        for(int i = 0; i < sensorHistory.size(); i++) {
            sensorHistory.addFirst(readSensor());
        }
    }

    @Override
    public void update() {
        sensorHistory.removeLast();
        sensorHistory.addFirst(readSensor());
    }

    @Override
    public double getCMValue() {
        double sum = 0;
        for (Double x : sensorHistory) {
            sum += x;
        }
        double mean = sum/sensorHistory.size();

        double sumOfTheSquareDifferencesFromTheMean = 0;
        for (Object x : sensorHistory) {
            sumOfTheSquareDifferencesFromTheMean += Math.pow((double) x - mean, 2);
        }
        double stdDev = Math.sqrt(sumOfTheSquareDifferencesFromTheMean/sensorHistory.size());

        return 0;
    }

    @Override
    public double getRawValue() {
        return (double) sensorHistory.getFirst();
    }

    @Override
    public String getConfigName() {
        return "Fake Distance Sensor";
    }

    private double readSensor() {
         Random rand = new Random();
         return rand.nextGaussian()*15+60;
    }
}
