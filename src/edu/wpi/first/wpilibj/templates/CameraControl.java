/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author Nick
 */
public class CameraControl {

    Servo cameraServo = new Servo(9);

    public void move(double joystick) {

        //Change this variable to change the speed that the servo moves. 1000 is most likely too low.
        double change = joystick / 1000;//This number is to compensate for it running this code millions of times per second

        if (!((cameraServo.get() + change) < 0.00) & !((cameraServo.get() + change) > 1.00)) {//Checks for correct values in the range of the servo

            cameraServo.set(cameraServo.get() + change);//Sets the servo to move

        }
    }
}