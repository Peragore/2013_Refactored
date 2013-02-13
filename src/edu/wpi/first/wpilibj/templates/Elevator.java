/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Talon;

/**
 *
 * @author Jamie
 */
public class Elevator {

    Talon elevatorTalon2 = new Talon(8);
    Talon elevatorTalon1 = new Talon(7);



    AnalogChannel angleMeter = new AnalogChannel(5);
    
    double minLimit = 0;
    double maxLimit = 5;
    double currentAngle; //= angleMeter.getVoltage();
    
    public void raise(){
        elevatorTalon1.set(0.1);
        elevatorTalon2.set(0.1);
    }
    
    public void lower(){
        elevatorTalon1.set(-0.1);
        elevatorTalon2.set(-0.1);
    }
    public void off(){
        elevatorTalon1.set(0);
        elevatorTalon2.set(0);
    }
    
    public void goToAngle(double targetAngle){
        //at the moment targetAngle is a voltage, 
        //TODO: make some sort of conversion from voltage to angle
        if (targetAngle < maxLimit && targetAngle > minLimit){
            currentAngle = angleMeter.getVoltage();
            if (Math.abs(targetAngle - currentAngle) <= .1){//TODO: check angle
              off();
            } else if (targetAngle > currentAngle){
                raise();
            } else{
                lower();
            }
             
        }
    }
}
