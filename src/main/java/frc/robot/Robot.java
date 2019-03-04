/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.hal.DIOJNI;
import edu.wpi.first.hal.sim.DIOSim;
import edu.wpi.first.hal.sim.mockdata.DIODataJNI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.POVButton;

public class Robot extends TimedRobot {
  private XboxController stick;
  private XboxController stick2;

  private WPI_VictorSPX baseleft1;
  private WPI_VictorSPX baseleft2;
  private WPI_VictorSPX baseright1;
  private WPI_VictorSPX baseright2;

  
  private WPI_VictorSPX hatchpanel;

  private WPI_VictorSPX vertical1;        
  private WPI_VictorSPX vertical2;        

  private WPI_VictorSPX standard;         
  private SpeedControllerGroup baseleft, baseright;

  private WPI_VictorSPX gate1;
  private WPI_VictorSPX gate2;
  private SpeedControllerGroup gate;

  private Encoder enc;

  public static Compressor comp;
  public static DoubleSolenoid sol;
  public static DoubleSolenoid sol2;

  public DigitalInput limitswitch;

  public DigitalOutput light1;
  public DigitalOutput light2;
  public DigitalOutput light3;

  private POVButton pov0;
  private POVButton pov90;
  private POVButton pov180;
  private POVButton pov270;

  int gearbox = 1;
  int gearbox2 = 1;
  int level = 1;
  double speed = 1.0;
  double speed2 = 1.0;
  double baseleftspeed;
  double baserightspeed;
  int pov;
  int compressorstatic1 = 1;
  int compressorstatic2 = 1;
  boolean limit = false;

  @Override
  public void robotInit() {
    stick = new XboxController(0);
    stick2 = new XboxController(1);
    
    baseleft1 = new WPI_VictorSPX(0);
    baseleft2 = new WPI_VictorSPX(2);
    baseright1 = new WPI_VictorSPX(1);
    baseright2 = new WPI_VictorSPX(3);
    
    hatchpanel = new WPI_VictorSPX(6);

    vertical1 = new WPI_VictorSPX(5);
    vertical2 = new WPI_VictorSPX(7);

    standard = new WPI_VictorSPX(4);

    gate1 = new WPI_VictorSPX(9);
    gate2 = new WPI_VictorSPX(8);
    gate = new SpeedControllerGroup(gate1, gate2);
    
    baseleft = new SpeedControllerGroup(baseleft1, baseleft2);
    baseright = new SpeedControllerGroup(baseright1, baseright2);

    pov = stick.getPOV();

    comp = new Compressor(49);
    sol = new DoubleSolenoid(49, 0, 1);
    sol2 = new DoubleSolenoid(49 , 2, 3);

    enc = new Encoder(3, 2);

    limitswitch = new DigitalInput(1);

    light1 = new DigitalOutput(4);
    light2 = new DigitalOutput(5);
    light3 = new DigitalOutput(6);

    pov0 = new POVButton(stick, 0);
    pov90 = new POVButton(stick, 90);
    pov180 = new POVButton(stick, 180);
    pov270 = new POVButton(stick, 270);
  }

  @Override
  public void robotPeriodic() {

  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
    
  }

  @Override
  public void teleopInit(){
    comp.start();
  }

  @Override
  public void teleopPeriodic() {
    limit = limitswitch.get();
    baseleftspeed = stick.getY(Hand.kLeft)*speed;
    baserightspeed = stick.getY(Hand.kRight)*speed;
    if(Math.abs(stick.getY(Hand.kRight)) > 0.1){
      baseright.set(baserightspeed);
    }
    else{
      baseright.set(0.0);
    }
    if(Math.abs(stick.getY(Hand.kLeft)) > 0.1){
      baseleft.set(-baseleftspeed);
    }
    else{
      baseleft.set(0.0);
    }

    if(gearbox == 0){
      gearbox = 1;
    }
    else if(gearbox == 4){
      gearbox = 3;
    }
    else if(stick.getBumperPressed(Hand.kRight)){
      gearbox++;
    }
    else if(stick.getBumperPressed(Hand.kLeft)){
      gearbox--;
    }

    if(gearbox == 1){
      speed = 0.1;
    }
    else if(gearbox == 2){
      speed = 0.2;
    }
    else if(gearbox == 3){
      speed = 0.4;
    }

    if(gearbox2 == 3){
      gearbox2 = 2;
    }
    else if(gearbox2 == 0){
      gearbox2 = 1;
    }
    else if(stick.getStickButton(Hand.kRight)){
      gearbox2++;
    }
    else if(stick.getStickButton(Hand.kLeft)){
      gearbox2--;
    }

    if(gearbox2 == 1){
      speed2 = 0.5;
    }
    else if(gearbox2 == 2){
      speed2 = 1.0;
    }
    //
    //
    //
    //Left and Right 
    if(pov90.get()){
      standard.set(ControlMode.PercentOutput , 1.0);
    }
    else if(pov270.get()){
      standard.set(ControlMode.PercentOutput , -1.0);
    }
    else{
      standard.set(ControlMode.PercentOutput , 0.0);
    }
    //
    //
    //
    //
    //Up and Down
    if(pov0.get()){                                   //Up
      vertical1.set(ControlMode.PercentOutput , 0.2);
      vertical2.set(ControlMode.PercentOutput , 0.2);
    }
    else if(stick.getXButton() && enc.get() < 7){    //second level
      vertical1.set(ControlMode.PercentOutput , 0.8);
      vertical2.set(ControlMode.PercentOutput , 0.8);
    }
    else if(stick.getYButton() && enc.get() < 13){    //Third lavel
      vertical1.set(ControlMode.PercentOutput , 0.8);
      vertical2.set(ControlMode.PercentOutput , 0.8);
    }
    else if(pov180.get() && limit == false){           //Down
      vertical1.set(ControlMode.PercentOutput , -0.1);
      vertical2.set(ControlMode.PercentOutput , -0.1);
    }
    else if(stick.getBackButton() && limit == false){
      vertical1.set(ControlMode.PercentOutput , -0.8);
      vertical2.set(ControlMode.PercentOutput , -0.8);
    }
    else{                                             //Stop
      vertical1.set(ControlMode.PercentOutput , 0.0);
      vertical2.set(ControlMode.PercentOutput , 0.0);
    }

    //Up and Down
    //
    //
    //
    //Hatch Panel
    if(stick.getTriggerAxis(Hand.kLeft) > 0.1){
      hatchpanel.set(ControlMode.PercentOutput , 0.2);
    }
    else if(stick.getTriggerAxis(Hand.kRight) > 0.1){
      hatchpanel.set(ControlMode.PercentOutput , -0.5*speed2);
    }
    else{
      hatchpanel.set(ControlMode.PercentOutput , 0.0);
    }
    //Hatch Panel
    //
    //
    //

    if(stick.getAButton()){
      gate.set(1.0);
    }
    else if(stick.getBButton()){
      gate.set(-1.0);
    }
    else{
      gate.set(0.0);
    }
    
    
      
    if(limit){
      enc.reset();   
    }

    if(stick.getStartButton()){
      enc.reset();
    }

    if(gearbox == 1) light1.set(false);
    else light1.set(true);

    if(gearbox == 2) light2.set(false);
    else light2.set(true);

    if(gearbox == 3) light3.set(false);
    else light3.set(true);

      System.out.println(enc.get());
  }



  @Override
  public void testPeriodic() {
  }

  
}
