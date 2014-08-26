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
public class NerdyAccel {
    
    private I2C m_accel;
    private double x_axis = 0.0, y_axis = 0.0, z_axis = 0.0;
    private final double kGsPerLSB = 0.004;
    
    /*
    Constructs an instance of the ADXL345 accelerometer via I2C
    */
    public NerdyAccel(int moduleNumber){
        DigitalModule module = DigitalModule.getInstance(moduleNumber);
        //Address is shifted one bit to the left based on WPI I2C Protocol
        //Addresses, therefore, are twice the value that is noted in documentation
        m_accel = module.getI2C(0xA6);
        m_accel.write(0x2C, 0x18);//Configures the BW_Rate to operate at 25 Hertz
        m_accel.write(0x31, 0x0B);//Configures the DATA_FORMAT to operate in FULL_RES with a range of +/- 16g
        m_accel.write(0x2D, 0x08);//Configures the POWER_CTL to start measuring
    }

    /*
    Constructs an instance of the ADXL345 accelerometer via I2C
    */
    public NerdyAccel(){
        DigitalModule module = DigitalModule.getInstance(1);
        //Address is shifted one bit to the left based on WPI I2C Protocol
        //Addresses, therefore, are twice the value that is noted in documentation
        m_accel = module.getI2C(0xA6);
        m_accel.write(0x2C, 0x18);//Configures the BW_Rate to operate at 25 Hertz
        m_accel.write(0x31, 0x0B);//Configures the DATA_FORMAT to operate in FULL_RES with a range of +/- 16g
        m_accel.write(0x2D, 0x08);//Configures the POWER_CTL to start measuring
    }
    
    public void read(){
        byte[] buffer = new byte[6];
        m_accel.read(0x32, 6, buffer);
        //Note : CRIO is little endian. 
        //Even Numbers represent LSB and Odd Numbers represent MSB
        x_axis = (((((short)buffer[1]) << 8) & 0xff00) | buffer[0]&0xff) * kGsPerLSB;
        y_axis = (((((short)buffer[3]) << 8) & 0xff00) | buffer[2]&0xff) * kGsPerLSB;
        z_axis = (((((short)buffer[5]) << 8) & 0xff00) | buffer[4]&0xff) * kGsPerLSB;
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
