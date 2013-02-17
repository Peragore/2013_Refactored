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



    AnalogChannel angleMeter = new AnalogChannel(1);
    
    double minLimit = 2.45;
    double maxLimit = 3;
    double basePWM = .6;
    double pwmModifier = 0.85;
    double elevatorTarget;
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
    
    public void goToAngle(double elevationTarget){
        //at the moment elevatorTarget is a voltage, 
        //TODO: make some sort of conversion from voltage to angle
            currentAngle = angleMeter.getVoltage();
            if (Math.abs(elevationTarget - currentAngle) <= .15){//TODO: check angle
              off();
            } else if (elevationTarget > currentAngle && elevationTarget < maxLimit){
                raise();
            } else if (elevationTarget < currentAngle && elevationTarget > minLimit){
                lower();
            }
             
        
    }
    public void automaticElevatorTarget(boolean addTarget, boolean decreaseTarget){
        if (addTarget  && elevatorTarget <= 4.7){
            elevatorTarget += .1;
        } if (decreaseTarget && elevatorTarget >= 1.3){
            elevatorTarget += -.1;
        }
    }
}
