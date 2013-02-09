/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Talon;

public class PickupArm {
    
    
    Relay grabSpike = new Relay(1);
    Relay vacuumSpike = new Relay(2);
    Talon rotateTalon = new Talon(1, 3);
    AnalogChannel pot1 = new AnalogChannel(7);
    DigitalInput armLimit = new DigitalInput(3); //returns true if clicked
    
    int grabStatus = 0;
    int rotateStatus = 0;
    String grabString;
    double lastTime;
    double previousTime;
    boolean pickUpFlag = true;
    boolean rotateFlag = false;
    double currentPosition;
    
    Team3373 team;
    
    public PickupArm(Team3373 t){
       team = t;
    }

    public void rotateEnabled(){
        rotateFlag = true;
    }
    public void rotateDisabled(){
        rotateFlag = false;
    }
    public void rotate (double targetPosition){
        /*
        currentPosition =  pot1.getVoltage(); //manual control
        //if ( shootA &&  flagA && currentPosition <= 3.334){ //a control (moving positive)
             targetPosition = (currentPosition + .25);
             flagA = false;
        } else if ( shootB &&  flagB && currentPosition >= 1.5){ //b control (moving negative)
             targetPosition = (currentPosition - .25);
             flagB = false;
        } else if (currentPosition <= 3.334 && currentPosition >= 1.5 ) { //manual control using Lstick X axis (positive and negative)                      
             StageOneTalon.set( shootLX);
        }
         smartDashboard.putNumber("Target Position: ",  targetPosition);
         smartDashboard.putNumber("Rotate Status: ", rotateStatus);
         smartDashboard.putNumber("Current Position: ", currentPosition);
        //
        * */
          switch(rotateStatus){
                case 0:
                   if(rotateFlag){
                      rotateStatus = 1;
                   }
                    break;
              case 1:
                  if( targetPosition > currentPosition && currentPosition <= 3.334){
                      rotateTalon.set(0.75);
                  } else if (targetPosition < currentPosition && currentPosition >= 1.5){
                      rotateTalon.set(-0.75);
                 } 
                 if (currentPosition > ( targetPosition - 0.05) && currentPosition < ( targetPosition + 0.05)){
                      rotateStatus = 2;
                    }
                  break;
               case 2:
                    rotateTalon.set(0);
                    rotateStatus = 0;
                    rotateDisabled();
                  break;
        }
    }
    public void armUp(){//TODO: make thread and sleep for 2 seconds while running
            final Thread thread = new Thread(new Runnable() {
           public void run(){
               grabSpike.set(Relay.Value.kForward);
               try{
               Thread.sleep(2000);
               }
               catch(InterruptedException e){
            
                }
               grabSpike.set(Relay.Value.kOff);
           } 
        });
            thread.start();
        /*if (pickUpFlag){//so we don't go up and go down
            lastTime =  team.robotTimer.get();
             grabSpike.set(Relay.Value.kForward);
            pickUpFlag = false;
        } 
        if (( team.robotTimer.get() - lastTime) >= 2){
             grabSpike.set(Relay.Value.kOff);
            pickUpFlag = true;
        }*/
    }
    public void armDown(){//TODO: make thread and sleep for 2 seconds while running
            final Thread thread = new Thread(new Runnable() {
           public void run(){
               grabSpike.set(Relay.Value.kReverse);
               try{
               Thread.sleep(2000);
               }
               catch(InterruptedException e){
            
                }
               grabSpike.set(Relay.Value.kOff);
           } 
        });
            thread.start();
        
    /*     if ( shootBack && pickUpFlag){
            lastTime =  robotTimer.get();
             grabSpike.set(Relay.Value.kReverse);
            pickUpFlag = false;
        } 
        if (( robotTimer.get() - lastTime) >= 2){
             grabSpike.set(Relay.Value.kOff);
            pickUpFlag = true;
        }
    */ 
    }
    
    /*public void createVacuum() {//used to create suction after arm goes down to grab frisbee

        
        grabString = Integer.toString(grabStatus);
         LCD.println(DriverStationLCD.Line.kUser1, 1, grabString);       
        
        switch(grabStatus){
            case 0: //init stage, not running and no signal to run
            if ( shootY &&  flagY){
                 vacuumSpike.set(Relay.Value.kOff);
                grabStatus = 1;
                 flagY = false;
            }
                break;
            case 1:// parked and signal to run
                 vacuumSpike.set(Relay.Value.kReverse);
                 boolean solenidFlag = true;
                grabStatus = 2;
                break;
            case 2: //running
                 vacuumSpike.set(Relay.Value.kReverse);
                double startTime =  team.robotTimer.get();
                if( armLimit.get() && (( team.robotTimer.get() - startTime) >= .25)){
                    grabStatus = 3;  
                }
                break;
            case 3: //back home, off
                 vacuumSpike.set(Relay.Value.kOff);
                grabStatus = 0;
            }
        
         LCD.updateLCD();

*/ }
