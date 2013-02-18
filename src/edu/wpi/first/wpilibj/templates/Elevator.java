/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Jamie
 */
public class Elevator {
    // Since elevator is tied directly to PWM hardware ports, allow only one
    // instance of Elevator to ever be created.  Provide a getInstance() method
    // to return the one and only Elevator object to other classes that
    // need to use it. This is known as a Singleton pattern.
    private static final Elevator instance = new Elevator();
    private Elevator() {}  //Prevents other classes from declaring new Elevator()
    public static Elevator getInstance() {
        return instance;
    }
    
    //Talon elevatorTalon2 = new Talon(8);
    Talon elevatorTalon1 = new Talon(7);
    Talon elevatorTalon2 = new Talon(8);

    // Used by voltage averaging/ smoothing method
    int arraySize = 500;
    double runningTotalVoltage[] = new double[arraySize];
    int bufferCount = 0;
    int currentIndex=0;
    double currentTotalVoltage = 0.0;
    double currentVoltage;
    double lastReading = 0.0;

    AnalogChannel angleMeter = new AnalogChannel(1);
    
    double minLimit = 2.45;
    double maxLimit = 3;
    double basePWM = .4;
    double pwmModifier = 0.85;
    double elevatorTarget;
    double currentAngle = angleMeter.getVoltage();
    double elevationTarget = angleMeter.getVoltage();;
    boolean goToFlag = false;
    //double angle = 41(voltage - 2.5) + 21.9;
    public void raise(){
        elevatorTalon1.set(basePWM);
        elevatorTalon2.set(basePWM * pwmModifier);
    }
    
    public void lower(){
        elevatorTalon1.set(-basePWM);
        elevatorTalon2.set(-basePWM * pwmModifier);
    }
    public void off(){
        elevatorTalon1.set(0);
        elevatorTalon2.set(0);
    }
       public double getAverageVoltage2() {
        
 
           currentVoltage = angleMeter.getVoltage(); //gets the non-average voltage of the sensor

       currentTotalVoltage = currentTotalVoltage - runningTotalVoltage[currentIndex] + currentVoltage; //adds the new data point while deleting the old
       runningTotalVoltage[currentIndex] = currentVoltage;//store the new data point
       currentIndex = (currentIndex + 1) %  arraySize;//currentIndex is the index to be changed
       if (bufferCount < arraySize) {
           bufferCount++;//checks to see jf the array is full of data points
       }
       return currentTotalVoltage/bufferCount;
        
    }
    
    public void goToAngle(){
        //at the moment elevatorTarget is a voltage, 
        //TODO: make some sort of conversion from voltage to angle
        currentAngle = getAverageVoltage2(); 
        if (Math.abs(elevationTarget - currentAngle) <= .15){//TODO: check angle
            off();
            System.out.println("off");
        } else if (elevationTarget > currentAngle && elevationTarget < maxLimit){
            raise();
            System.out.println("raise");
        } else if (elevationTarget < currentAngle && elevationTarget > minLimit){
            lower();
            System.out.println("lower");
        } 
        
    }
    public void setTarget(double a){
        elevationTarget = a;
        goToFlag = true;
    }
    public void goTo(){
        final Thread thread = new Thread(new Runnable() {
        public void run(){
                goToFlag = false;
                while(elevationTarget > currentAngle&& elevationTarget < maxLimit){
                    System.out.println("raise");
                    if(elevationTarget < currentAngle){
                        break;
                    }
                }
                while(elevationTarget < currentAngle&& elevationTarget > minLimit){
                    System.out.println("lower");
                    if(elevationTarget > currentAngle){
                        break;
                    }
                }
                System.out.println("off");
                }
            });
                thread.start();
    }
    /*public void automaticElevatorTarget(boolean addTarget, boolean decreaseTarget){
        if (addTarget  && elevatorTarget <= 4.7){
            elevatorTarget += .1;
        } if (decreaseTarget && elevatorTarget >= 1.3){
            elevatorTarget += -.1;
        }
    }*/
}
