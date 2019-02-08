/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot3;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.util.RangeMapper;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private XboxController stick;
    
    private Encoder enc;
    private WPI_VictorSPX motor;
    private PIDController pid;

    private double spd=0.0;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        stick = new XboxController(0);
        
        enc = new Encoder(0,1,true,EncodingType.k4X);
        motor = new WPI_VictorSPX(4);
        pid = new PIDController(0.0015,0.003,0.0001875,enc,motor);
        enc.setPIDSourceType(PIDSourceType.kRate);
        pid.setPercentTolerance(10);
        pid.setInputRange(-750, 750);
        pid.setOutputRange(-1.0, 1.0);
    }

    @Override
    public void disabledInit() {
        pid.disable();
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want ran during disabled,
     * autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic functions, but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        spd = SmartDashboard.getNumber("Speed", 0.0);
        SmartDashboard.putNumber("Speed", spd);
        SmartDashboard.putNumber("Encoder Speed", enc.getRate());
        SmartDashboard.putNumber("Encoder PID", enc.pidGet());
        SmartDashboard.putNumber("PID out", pid.get());
        SmartDashboard.putBoolean("PID State", pid.isEnabled());
        SmartDashboard.putData("PID",pid);
        SmartDashboard.putNumber("PID Error", pid.getError());
        if(SmartDashboard.getBoolean("PID Reset", false)){
            pid.reset();
        }
        SmartDashboard.putBoolean("PID Reset", false);
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() {

    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void teleopInit() {
        pid.enable();
    }
    /**
     * This function is called periodically during operator control.
     */
    private RangeMapper pidMap = new RangeMapper(-750.0,750.0);
    @Override
    public void teleopPeriodic() {
        pid.setSetpoint(pidMap.mapRange(spd));
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }
}
