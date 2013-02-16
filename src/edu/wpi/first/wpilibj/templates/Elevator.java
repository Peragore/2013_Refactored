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

    //Talon elevatorTalon2 = new Talon(8);
    Talon elevatorTalon1 = new Talon(7);
    Talon elevatorTalon2 = new Talon(8);



    AnalogChannel angleMeter = new AnalogChannel(6);
    
    double minLimit = 0;
    double maxLimit = 5;
    double basePWM = .6;
    double pwmModifier = 0.85;
    double currentAngle; //= angleMeter.getVoltage();
    
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
    
    public void goToAngle(){
        //at the moment elevatorTarget is a voltage, 
        //TODO: make some sort of conversion from voltage to angle
        if (elevatorTarget < maxLimit && elevatorTarget > minLimit){
            currentAngle = angleMeter.getVoltage();
            if (Math.abs(elevatorTarget - currentAngle) <= .1){//TODO: check angle
              off();
            } else if (elevatorTarget > currentAngle){
                raise();
            } else if (elevatorTarget < currentAngle){
                lower();
            }
             
        }
    }
    public void automaticElevatorTarget(boolean addTarget, boolean decreaseTarget){
        if (addTarget){
            elevatorTarget += .1;
        } else if (decreaseTarget){
            elevatorTarget -= .1;
        }
    }
}
