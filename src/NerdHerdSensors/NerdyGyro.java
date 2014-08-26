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
public class NerdyGyro {
    
    private I2C m_gyro;
    private double x_axis = 0.0, y_axis = 0.0, z_axis = 0.0;
    private final double kDegreesPerSecond_PerLSB = 0.0696;
    
    /*
    Constructs an instance of the ITG3200 gyro via I2C
    */
    public NerdyGyro(int moduleNumber){
        DigitalModule module = DigitalModule.getInstance(moduleNumber);
        //Address is shifted one bit to the left based on WPI I2C Protocol
        //Addresses, therefore, are twice the value that is noted in documentation
        m_gyro = module.getI2C(0xd0);
        m_gyro.write(0x15, 0x27);//Configures the Sample Rate to 25 Hertz
        m_gyro.write(0x16, 0x1C);//Configures the sensor to operate in FULL_RES and configures a lowpass-filter at 20 Hertz
        m_gyro.write(0x3E, 0x00);//Configures the sensor to power on.
    }

    /*
    Constructs an instance of the ITG3200 gyro via I2C
    */
    public NerdyGyro(){
        DigitalModule module = DigitalModule.getInstance(1);
        //Address is shifted one bit to the left based on WPI I2C Protocol
        //Addresses, therefore, are twice the value that is noted in documentation
        m_gyro = module.getI2C(0xd0);
        m_gyro.write(0x15, 0x27);//Configures the Sample Rate to 25 Hertz
        m_gyro.write(0x16, 0x1C);//Configures the sensor to operate in FULL_RES and configures a lowpass-filter at 20 Hertz
        m_gyro.write(0x3E, 0x00);//Configures the sensor to power on.
    }
    
    public void read(){
        byte[] buffer = new byte[6];
        m_gyro.read(0x1D, 6, buffer);
        //Note : CRIO is little endian. 
        //Even Numbers represent MSB and Odd Numbers represent LSB
        x_axis = (((((short)(buffer[0])) << 8) & 0xff00) | buffer[1]&0xff) * kDegreesPerSecond_PerLSB;
        y_axis = (((((short)(buffer[2])) << 8) & 0xff00) | buffer[3]&0xff) * kDegreesPerSecond_PerLSB;
        z_axis = (((((short)(buffer[4])) << 8) & 0xff00) | buffer[5]&0xff) * kDegreesPerSecond_PerLSB;
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
