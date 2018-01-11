/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team5678.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	//Limit Setup
	DigitalInput Limitup = new DigitalInput(1);
	DigitalInput Limitdw = new DigitalInput(2);
	
	//Motor Setup
	Talon L1 = new Talon(0);
	Talon R1 = new Talon(1); 
	Spark updw = new Spark(2);
	Spark claw = new Spark(3);
	
	//Smart Dashboard Setup
	private static final String kDefaultAuto = "Default";//Default Auto
	private static final String Middle = "Middle";//set for starting in 2 pestion
	private static final String kCustomAuto = "My Auto";//Custom Auto
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	//Drive Setup
	DifferentialDrive myRobot = new DifferentialDrive(L1, R1);
	
	//Control Setup
	Joystick stick = new Joystick(0);
	Joystick stick2 = new Joystick(1);	
	double sn = (stick.getThrottle()+1)/2;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		m_chooser.addObject("Middle", Middle);
		SmartDashboard.putData("Auto choices", m_chooser);
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
		m_autoSelected = m_chooser.getSelected();
		// m_autoSelected = SmartDashboard.getString("Auto Selector",
		// 		kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kCustomAuto:
				// Put custom auto code here
				break;
			case kDefaultAuto:
			default:
				//myRobot.drive(1, 0);
				Timer.delay(5);
				myRobot.stopMotor();
				break;
			case Middle:
				
				break;
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		myRobot.setSafetyEnabled(false);
		if(myRobot.isAlive()) {
			//checks to make sure robot is on and in telop mode
			myRobot.setMaxOutput((stick.getThrottle()*-1+1)/2);
			//sets max speed based off of throtle
			//myRobot.setSensitivity(sn);
			myRobot.arcadeDrive(-stick.getY(), stick.getZ());
			//sets movment bassed off of - y axis and z axis
			Timer.delay(.005);//motor update
			if(stick.getRawButton(4)&&Limitup.equals(1)) {
				//if the 4 buttun is pressed and the top limit switch isent dosen moves claw upwords
				updw.set(1);
			}
			if(stick.getRawButton(5)&&Limitdw.equals(1)) {
				//if button 5 is pressend and bottom limit switch isent the thing will go down
				updw.set(-1);
			}			
			
			
			Timer.delay(.005);
		}
		
		Timer.delay(.005);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		//System.out.println(sn);
		System.out.println("Limit UP"+Limitup.get());
		//System.out.println("Button 4"+stick.getRawButton(4));
		System.out.println("Limit Down"+Limitdw.get());
		//System.out.println("Button 5"+stick.getRawButton(5));
	}
}
