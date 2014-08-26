/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package NerdHerdUtil;

/**
 *
 * @author Mr. Mallory
 */    
public class TrapezoidalIntegrator {
   
    private double m_sampleTime, m_accumulation, m_accumulationLimit;
    private double m_lastValue = 0.0;
    private double m_lastTime = 0.0;
            
    //Constructor
    public TrapezoidalIntegrator(double sampleTimeIn) {
            m_sampleTime = sampleTimeIn;
    }
    
    //This is the default constructor
    public TrapezoidalIntegrator() {
            m_sampleTime = .04; 
    }

    /**
     * This function sets the sample time
     * @param sampleTimeIn
     */
    public void setSampleTime(double sampleTimeIn) {
            m_sampleTime = sampleTimeIn;
    }
    

    /**
     * This function returns the sample time
     * @return 
     */
    public double getsampleTime() {
        return m_sampleTime;
    }
    
    public void resetAccumulation() {
            m_accumulation = 0;
            m_lastValue = 0;
    }
    
    public double updateAccumulation(double currentValue){
        m_accumulation = m_accumulation + ((m_lastValue + currentValue)*m_sampleTime)/2;
        if (m_accumulation > m_accumulationLimit){
            m_accumulation = m_accumulationLimit; 
        }else if(m_accumulation < -m_accumulationLimit){
            m_accumulation = -m_accumulationLimit;
        }
        m_lastValue = currentValue;
        return m_accumulation;
    }
    
}
