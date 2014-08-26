/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package NerdHerdMain;


import NerdHerdSensors.*;
import NerdHerdUtil.*;
import com.sun.squawk.util.MathUtils;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Main extends IterativeRobot {
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    private NerdyAccel m_accel;
    private NerdyCompass m_compass;
    private NerdyGyro m_gyro;
    //private Filer sensorDataLog;
    private double heading;
    private double gyroHeading;
    private double distance;
    private TrapezoidalIntegrator accelerationIntegrator;
    private TrapezoidalIntegrator distanceIntegrator;
    private long lastTime = 0;
    
    public void robotInit() {
        m_accel = new NerdyAccel(1);
        m_compass = new NerdyCompass(1);
        m_gyro = new NerdyGyro(1);
        heading = 0.0;
        gyroHeading = 0.0;
        distance = 0.0;
        accelerationIntegrator = new TrapezoidalIntegrator();
        distanceIntegrator = new TrapezoidalIntegrator();
    }

    
    public void autonomousInit(){
        //sensorDataLog = new Filer("Sensor_Data");
        //sensorDataLog.println("Accel_X\tAccel_Y\tAccel_Z\tCompass_X\tCompass_Y\tCompass_Z\tGyro_X\tGyro_Y\tGyro_Z\tHeading\tGyroHeading\tDistance\t");
        heading = 0.0;
        gyroHeading = 0.0;
        distance = 0.0;
        accelerationIntegrator.resetAccumulation();
        distanceIntegrator.resetAccumulation();
        
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        m_accel.read();
        m_compass.read();
        m_gyro.read();
        heading = Math.toDegrees(MathUtils.atan2(m_compass.getY(), m_compass.getX())+Math.PI);
        gyroHeading += m_gyro.getZ() * 0.040;
        distance = distanceIntegrator.updateAccumulation(accelerationIntegrator.updateAccumulation(Math.sqrt(sqr(m_accel.getX()*32.174) + sqr(m_accel.getY()*32.174))));
        //sensorDataLog.println(m_accel.getX() + "\t" + m_accel.getY() + "\t" + m_accel.getZ() + "\t" + m_compass.getX() + "\t" + m_compass.getY() + "\t" + m_compass.getZ() + "\t" + m_gyro.getX() + "\t" + m_gyro.getY() + "\t" + m_gyro.getZ() + "\t" + heading + "\t" + gyroHeading + "\t" + distance + "\t");
        
        SmartDashboard.putDouble("Accel_X", m_accel.getX());
        SmartDashboard.putDouble("Accel_Y", m_accel.getY());
        SmartDashboard.putDouble("Accel_Z", m_accel.getZ());
        SmartDashboard.putDouble("Compass_X", m_compass.getX());
        SmartDashboard.putDouble("Compass_Y", m_compass.getY());
        SmartDashboard.putDouble("Compass_Z", m_compass.getZ());
        SmartDashboard.putDouble("Gyro_X", m_gyro.getX());
        SmartDashboard.putDouble("Gyro_Y", m_gyro.getY());
        SmartDashboard.putDouble("Gyro_Z", m_gyro.getZ());
        SmartDashboard.putDouble("heading", heading);
        SmartDashboard.putDouble("gyroHeading", gyroHeading);
        SmartDashboard.putDouble("distance", distance);
        
        
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
    private double sqr(double value){
        return value*value;
    }
}
