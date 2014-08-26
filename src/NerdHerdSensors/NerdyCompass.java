/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NerdHerdSensors;

import edu.wpi.first.wpilibj.DigitalModule;
import edu.wpi.first.wpilibj.I2C;

/**
 *
 * @author Mr. Mallory
 */
public class NerdyCompass {
    
    private I2C m_compass;
    private double x_axis = 0.0, y_axis = 0.0, z_axis = 0.0;
    
    /*
    Constructs an instance of the HMC5883L compass via I2C
    */
    public NerdyCompass(int moduleNumber){
        DigitalModule module = DigitalModule.getInstance(moduleNumber);
        //Address is shifted one bit to the left based on WPI I2C Protocol
        //Addresses, therefore, are twice the value that is noted in documentation
            m_compass = module.getI2C(0x3c);
        
        
        m_compass.write(0x00, 0x14);//Configures the sensor to operate at 30 Hertz
        m_compass.write(0x01, 0x20);//Configures the sensor to operate at default sensitivity. +/- 1.3 Gauss
        m_compass.write(0x02, 0x00);//Configures the sensor to operate in continuous-measurement mode
    }

    /*
    Constructs an instance of the HMC5883L compass via I2C
    */
    public NerdyCompass(){
        DigitalModule module = DigitalModule.getInstance(1);
        //Address is shifted one bit to the left based on WPI I2C Protocol
        //Addresses, therefore, are twice the value that is noted in documentation
        m_compass = module.getI2C(0x3c);
    }
    
    public void read(){
        byte[] buffer = new byte[6];
        m_compass.read(0x03, 6, buffer);
        //Note : CRIO is little endian. 
        //Even Numbers represent MSB and Odd Numbers represent LSB
        x_axis = (((((short)(buffer[0])) << 8) & 0xff00) | buffer[1]&0xff);
        //Z and Y axis are switched based on manufacturer error.
        z_axis = (((((short)(buffer[2])) << 8) & 0xff00) | buffer[3]&0xff);
        y_axis = (((((short)(buffer[4])) << 8) & 0xff00) | buffer[5]&0xff);
    }
    
    public double getX(){
        return x_axis;
    }
    
    public double getY(){
        return y_axis;
    }
    
    public double getZ(){
        return z_axis;
    }
}
