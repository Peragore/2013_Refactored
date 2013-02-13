/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStationLCD.Line;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.templates.*;
//import edu.wpi.first.wpilibj.RobotDrive;
//import edu.wpi.first.wpilibj.SimpleRobot;
//import edu.wpi.first.wpilibj.templates.Shooter;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory. 
 */
public class Team3373 extends SimpleRobot{
    /**
     * This function is called once each time the robot enters autonomous mode.
     */

   Servo frontCameraServo = new Servo(10);//camera class?
   
   
   
   // what is this used for?
   
   DriverStationLCD LCD = DriverStationLCD.getInstance();
   //SmartDashboard smartDashboard;
   SuperJoystick driveStick = new SuperJoystick(1);
   SuperJoystick shooterController = new SuperJoystick(2);
   Shooter objShooter = new Shooter();
   //Deadband objDeadband = new Deadband();
   Timer robotTimer = new Timer();
   PickupArm arm = new PickupArm();
   Drive objDrive = new Drive();
   //Camera camera = new Camera();

   double rotateLimitMaximum = 4.8;//are these used?
   double rotateLimitMinimum = 0.2;//are these used?
   //drive Drive = new drive(this);

   boolean test;
   boolean solenidFlag=false;
   
  /*********************************
   * Math/Shooter Action Variables *
   *********************************/
   
   TableLookUp objTableLookUp = new TableLookUp();
   
   double ShooterSpeedStage2 = 0;//was StageTwoTalon.get()
   double percentageScaler = 0.75;
   double ShooterSpeedStage1 = ShooterSpeedStage2 * percentageScaler;//was StageOneTalon.get()
   
   double ShooterSpeedMax = 5300.0;
   double ShooterSpeedAccel = 250;
   double stageOneScaler = .5; //What stage one is multiplied by in order to make it a pecentage of stage 2
   double PWMMax = 1; //maximum voltage sent to motor
   double MaxScaler = PWMMax/5300;
   double ShooterSpeedScale = MaxScaler * ShooterSpeedMax; //Scaler for voltage to RPM. Highly experimental!!
   double target;
   double RPMModifier = 250;
   double idle = 1 * ShooterSpeedScale;
   double off = 0;
   double change;
   
   double startTime = 9000000;
   double backTime = 90000000;
   double aTime = 900000000;
   double bTime = 900000000;
   boolean manualToggle;
   double manualStatus;
   boolean armTestFlag;
   int LX = 1;
   int LY = 2;
   int Triggers = 3;
   int RX = 4;
   int RY = 5;
   int DP = 6;
   double rotateTest = 2.7;
   double frontBackDrive;
   double leftRightDrive;
   double rotateDrive;
   
   
   //public Team3373(){
     // camera.robotInit(LCD);
    //}
    
    public void autonomous() {
        for (int i = 0; i < 4; i++)  {

            }
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void disabled(){
        while (isDisabled()){
            manualToggle = false;
            armTestFlag = false;
            arm.demoOnFlag = false;
            arm.targetRotatePosition = arm.pot1.getVoltage(); 
            arm.demoStatus = 0;
            arm.robotTimer.start();
            objDrive.driverPerspective = 0;
        }
    }
    public void operatorControl() {
        robotTimer.start();
        while (isOperatorControl() & isDisabled()){ 
            manualToggle = false;
            armTestFlag = false;
            arm.demoOnFlag = false;
            arm.targetRotatePosition = arm.pot1.getVoltage(); 
            arm.demoStatus = 0;
            arm.robotTimer.start();
            objDrive.driverPerspective = 0;
        }
   while (isOperatorControl() & isEnabled()){
   if (!armTestFlag){
       //objTableLookUp.test();
   /****************
   **Shooter Code***
   ****************/
   //Resets the internal toggle flags when a previously pushed button has been released
       shooterController.clearButtons();
       arm.currentPosition = arm.pot1.getVoltage();
       objDrive.perspectiveControl(driveStick.isAPushed(), driveStick.isBPushed(), driveStick.isXPushed(), driveStick.isYPushed());
       
       LCD.println(Line.kUser2, 1, "running");
       /*if(shooterController.isStartPushed()){
           LCD.println(Line.kUser5, 1, "Inside");//TODO
           //camera.imageAnalysis();
           System.out.println("Inside");
           //objShooter.start();
           //arm.armUp();
       }*/
        /**********************
        * Drive Code *
        **********************/

       switch (objDrive.driverPerspective){
           case 0: //forward
               leftRightDrive = driveStick.getRawAxis(LX);
               frontBackDrive = driveStick.getRawAxis(LY);
               break;
           case 1: //left?
               leftRightDrive = driveStick.getRawAxis(LX);
               frontBackDrive = -driveStick.getRawAxis(LY);
               break;
           case 2: //reverse?
               leftRightDrive = -driveStick.getRawAxis(LY);
               frontBackDrive = -driveStick.getRawAxis(LX);
               break;
           case 3: //right?
               leftRightDrive = -driveStick.getRawAxis(LY);
               frontBackDrive = driveStick.getRawAxis(LX);
               break;
       }
       objDrive.setSpeed(driveStick.isRBHeld(), driveStick.isLBHeld()); //Sets the speed variable (Sniper vs. Turbo vs. Default)
       
       objDrive.drive(leftRightDrive,frontBackDrive,rotateDrive); //Calls the Drive function
       
       
       /**********************
        * Shooter Algorithms *
        **********************/
       
       if(shooterController.isAPushed()){
            objShooter.increaseSpeed();
       }
       if(shooterController.isBPushed()){
           objShooter.decreaseSpeed();
       }        
       if(shooterController.isXPushed()){
           objShooter.increasePercentage();
       }
       if(shooterController.isYPushed()){
           objShooter.decreasePercentage();
       }
       if(shooterController.isBackPushed()){
           arm.armUp();
           //objShooter.stop();
       }
       if (shooterController.isRBPushed()){
           arm.armDown();
       }
       /*if (shooterController.isStartPushed()){
           arm.armUp();
       }*/
       if(shooterController.isLStickPushed()){
           LCD.println(Line.kUser5, 1, "Inside");
           //camera.imageAnalysis();
           System.out.println("Inside");
       }
       //arm.rotate(arm.targetRotatePosition);
       objShooter.printLCD(LCD);
       //Arm.rotate(targetPosition);
       //objShooter.elevator();
       //Arm.grabFrisbee();
       //Arm.armUp();
       //Arm.armDown();
       //Arm.goToPosition(2.5);
       /*
       //try {Thread.sleep(1000);} catch(Exception e){}
       
       /*******************
        * Servo Test Code *
        ******************/
        
        /******************
         * Demo/Test Code *
         ******************/

       
        if (shooterController.isStartPushed() && !arm.demoOnFlag){
            arm.demoStatus = 0;
            arm.demoOnFlag = true;
        } else if (shooterController.isStartPushed() && arm.demoOnFlag){
            arm.demoOnFlag = false;
        }
       arm.rotate(arm.targetRotatePosition);
       arm.armDemo();
       arm.createVacuum(arm.vacuumTrigger);
       }

       /*SmartDashboard.putBoolean("ArmUp Bool:", arm.downFlag);
       SmartDashboard.putBoolean("ArmDown Bool: ", arm.upFlag);
       SmartDashboard.putNumber("CurrentPosition :", arm.currentPosition);
       SmartDashboard.putNumber("DemoSwitchStatus: ", arm.demoStatus);
       SmartDashboard.putBoolean("isAPushed", shooterController.isAPushed());
       SmartDashboard.putBoolean("isBPushed", shooterController.isBPushed());
       SmartDashboard.putBoolean("isXPushed", shooterController.isXPushed());
       SmartDashboard.putBoolean("isYPushed", shooterController.isYPushed());
       SmartDashboard.putBoolean("isLBPushed", shooterController.isLBPushed());
       SmartDashboard.putBoolean("isRBPushed", shooterController.isRBPushed());
       SmartDashboard.putBoolean("isLStickPushed", shooterController.isLStickPushed());
       SmartDashboard.putBoolean("isRStickPushed", shooterController.isRStickPushed());*/
       //arm.rotate(rotateTest);
       

       //if  (!armTestFlag) {
       //arm.rotateTalon.set(shooterController.getRawAxis(LX));
       //}
        if (shooterController.isAHeld() && shooterController.isXHeld() && !armTestFlag){ //allows the test mode for the arm assembly to start
            armTestFlag = true;
        } else if (shooterController.isAHeld() && shooterController.isXHeld() && shooterController.isYHeld() && armTestFlag){ //turns the test mode for arm off
            armTestFlag = false;
        }
        if (armTestFlag){

            
            if (shooterController.isLStickPushed() && manualToggle){
                manualToggle = false;
            } else if (shooterController.isRStickPushed() && !manualToggle){
                manualToggle = true;
            }
            //switch (manualStatus){ //controls whether automatic or manual control
                //case 0: //manual rotation control
              if (manualToggle){      
                    if (shooterController.isRBHeld() && !shooterController.isLBHeld()){
                        arm.rotateTalon.set(.5);
                    } else if (shooterController.isLBHeld() && !shooterController.isRBHeld()){
                        arm.rotateTalon.set(-.5);
                    } else{
                        arm.rotateTalon.set(0);
                    }
                    if (shooterController.isStartPushed()){
                        manualStatus = 1;
                    }
              } else if (!manualToggle) {
                //case 1: //automatic targeting
                    if (shooterController.isLBPushed()){
                        arm.targetRotatePosition = 2.7;
                    } 
                    if(shooterController.isRBPushed()){
                        arm.targetRotatePosition = 2.3;
                    }
                    arm.rotate(arm.targetRotatePosition);
                    if (shooterController.isBackPushed()){
                        manualStatus = 0;
                    }
              }
            //}

            shooterController.clearButtons();
            if (shooterController.isAPushed()) arm.armUp();
            if (shooterController.isBPushed()) arm.armDown();
            //Arm.grabFrisbee(shooterController.isStartPushed());

            shooterController.clearButtons();
            LiveWindow.run();
        }
        //SmartDashboard.putNumber("Target: ", arm.targetRotatePosition);
        //SmartDashboard.putNumber("manualStatus: ", manualStatus);
        SmartDashboard.putBoolean("ManualToggle: ", manualToggle);
        SmartDashboard.putBoolean ("Test Status: ", armTestFlag);
       
        String currentTime = Double.toString(robotTimer.get());
        LCD.println(Line.kUser6, 1, currentTime);
        
        //String potString = Double.toString(pot1.getVoltage());
        //LCD.println(Line.kUser2, 1, potString);
        LCD.updateLCD();
        System.out.println("Arm Test Flag: " + armTestFlag);
        System.out.println("Perspective: " + objDrive.driverPerspective);
        System.out.println("Driver A Button: " + driveStick.isAHeld());
        SmartDashboard.putNumber("Perspective: ", objDrive.driverPerspective);
        SmartDashboard.putBoolean("isAPushed", driveStick.isAHeld());
        SmartDashboard.putBoolean("isBPushed", driveStick.isBHeld());
        SmartDashboard.putBoolean("isXPushed", driveStick.isXHeld());
        SmartDashboard.putBoolean("isYPushed", driveStick.isYHeld());
        SmartDashboard.putNumber("LX", driveStick.getRawAxis(LX));
        SmartDashboard.putNumber("LY", driveStick.getRawAxis(LY));
        SmartDashboard.putNumber("RX", driveStick.getRawAxis(RX));
    
        

        }
    }
}

