/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;
import java.util.Hashtable;

/**
 *
 * @author RoboHawks
 */
public class TableLookUp {
    double[] distanceArray = {10,15,20,25,30,35,40};//these are just demonstration code, not actual code, TODO: test!
    double[] angleArray = {50,45,40,35,30,25,20};//these are just demonstration code, not actual code, TODO: test!
    double[] rpmArray = {100,150,200,250,300,350};//these are just demonstration code, not actual code, TODO: test!
    int match;
    double anglePercentage;
    double difference;
    double rpmPercentage;
    double result;
    public void test(){
    Hashtable hash = new Hashtable();
    hash.put(new Double(1.0), new Double(1.0));
    hash.put(new Double(2.0), new Double(2.0));
    hash.put(new Double(3.0), new Double(3.0));
    hash.put(new Double(4.0), new Double(4.0));
    hash.put(new Double(5.0), new Double(5.0));
    //double hashDouble = hash.getValue(1.0);
    System.out.println(hash.toString());
    }

    public double lookUpAngle(double currentDistance){
        int i;
        if(currentDistance < distanceArray[distanceArray.length - 1]){
            for (i = 0; i < distanceArray.length; i++){//searches for a match of distance in angle array
                if (currentDistance == distanceArray[i]){
                    return angleArray[i];
                } else if(currentDistance>distanceArray[i] && currentDistance < distanceArray[i+1]){//used to find two values to interperlate 
                    difference = distanceArray[i+1] - distanceArray[i];
                    anglePercentage = (currentDistance-distanceArray[i])/difference;
                    result = angleArray[i]+((angleArray[i+1]-angleArray[i]) * anglePercentage);
                    return result;
                }   
        }
        } else{
            return 0;
        }
        //we will never get here
        return 0;
    }
    public double lookUpRpm(double currentDistance){
        int i;
        if(currentDistance < distanceArray[distanceArray.length - 1]){
            for (i = 0; i < distanceArray.length; i++){//searches for a match of distance in RPM array
                if (currentDistance == distanceArray[i]){
                    return rpmArray[i];
                } else if(currentDistance>distanceArray[i] && currentDistance < distanceArray[i+1]){//used to find two values to interperlate 
                    difference = distanceArray[i+1] - distanceArray[i];
                    rpmPercentage = (currentDistance-distanceArray[i])/difference;
                    result = rpmArray[i]+((rpmArray[i+1]-rpmArray[i]) * rpmPercentage);
                    return result;
                }   
        }
        } else{
            return 0;
        }
        //we will never get here
        return 0;
    }
}