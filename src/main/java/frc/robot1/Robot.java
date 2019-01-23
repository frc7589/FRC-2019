/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot1;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.util.RangeMapper;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    private Joystick stick = new Joystick(0);
    private WPI_VictorSPX left1 = new WPI_VictorSPX(1);
    private WPI_VictorSPX left2 = new WPI_VictorSPX(3);
    private WPI_VictorSPX right1 = new WPI_VictorSPX(0);
    private WPI_VictorSPX right2 = new WPI_VictorSPX(2);
    
    private SpeedControllerGroup left = new SpeedControllerGroup(left1, left2);
    private SpeedControllerGroup right = new SpeedControllerGroup(right1, right2);

    private DifferentialDrive drive = new DifferentialDrive(left, right);
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {

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
        
    }
    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        double rotate = new RangeMapper(-0.35, 0.35).mapRange(stick.getX());		
		//Set forward speed while stick push 
		double speedct = new RangeMapper(0.5,-0.5).mapRange(stick.getY()) ;
		
		if (Math.abs(stick.getX()) > 0.1 || Math.abs(stick.getY()) > 0.1) {
			
			//Calculate move speed and move
			System.out.printf("RUN => Speed : %f , Rotate : %f \n", speedct,rotate);
			drive.arcadeDrive(speedct, rotate, false);
		}
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {

    }
}
