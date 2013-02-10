/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

public class Drive {
    
    RobotDrive mechanum = new RobotDrive(1,2,3,4);
    double speed = 0.75; //Default Speed
    
    public void drive(double driveLX, double driveLY, double driveRX){ 
        
    
    mechanum.mecanumDrive_Cartesian(driveLX * speed, driveLY * speed, driveRX * speed, 0); //Sets the motor speeds
    
    }
    
    public void setSpeed(boolean sniper, boolean turbo){
        
        if(turbo && !sniper){
            speed = 1.00; //In turbo mode
        } else if(!turbo && sniper){
            speed = 0.50; //In sniper mode
        } else {
            speed = 0.75; //In default mode
        }
    }
    
    public void drivePrint(DriverStationLCD LCD){
        
        LCD.println(DriverStationLCD.Line.kUser1, 1, Double.toString(speed));
        
    }
    
}
