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
//import jaci.pathfinder.Pathfinder;
//import jaci.pathfinder.Trajectory;
//import jaci.pathfinder.Waypoint;

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
	Spark L1 = new Spark(0);
	Spark R1 = new Spark(1); 
	Spark updw = new Spark(2);
	Spark claw = new Spark(3);
	
	//Smart Dashboard Setup
	private static final String kDefaultAuto = "Default";//Default Auto
	private static final String Middle = "Middle";//set for starting in 2 position
	private static final String kCustomAuto = "My Auto";//Custom Auto
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	//Drive Setup
	DifferentialDrive myRobot = new DifferentialDrive(L1, R1);
	
	//Control Setup
	Joystick stick = new Joystick(0);
	Joystick stick2 = new Joystick(1);	
	double sn = (stick.getThrottle()+1)/2;
	
	//Variables 
	boolean reversed =(false);
	String GameSide;
	int Startingp;
	
	/*Waypoint[] points = new Waypoint[] {
		    new Waypoint(-4, -1, Pathfinder.d2r(-45)),      // Waypoint @ x=-4, y=-1, exit angle=-45 degrees
		    new Waypoint(-2, -2, 0),                        // Waypoint @ x=-2, y=-2, exit angle=0 radians
		    new Waypoint(0, 0, 0)                           // Waypoint @ x=0, y=0,   exit angle=0 radians
		};

		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 1.7, 2.0, 60.0);
		Trajectory trajectory = Pathfinder.generate(points, config);
	*/

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
		autonomousInit();//int autonomous
		Startingp = DriverStation.getInstance().getLocation();
		GameSide = DriverStation.getInstance().getGameSpecificMessage();//get witch side of the switch is friendly
		myRobot.setSafetyEnabled(isDisabled());
		if(GameSide.charAt(0) == 'L'){
			if(Startingp==1) {
			myRobot.arcadeDrive(-1,- 0);
			Timer.delay(3);
			myRobot.stopMotor();
			Timer.delay(.005);
			myRobot.arcadeDrive(0, 0);
			Timer.delay(12);
			}
			else if(Startingp(2)){
				System.out.println();
			}
			else if(Startingp(3)){
				
			}
		}
		else if(GameSide.charAt(0) == 'R'){
			if(Startingp(1)) {
				
			}
			else if(Startingp(2)){
				
			}
			else if(Startingp(3)){
				
			}
		}
		
		/*switch (m_autoSelected) {
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
		}*/
	}

	private void elseif(boolean b) {
		// TODO Auto-generated method stub
		
	}

	private boolean Startingp(int i) {
		// TODO Auto-generated method stub
		return false;
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
			
			if(stick.getRawButton(7)) {reversed=true;}//if button 7 is pressed reversed=true
			else if(stick.getRawButton(8)) {reversed=false;}//if button 8 is pressed reversed=false
			
			if(reversed){myRobot.arcadeDrive(-stick.getY(), -stick.getX());}//reverse direction
			else {myRobot.arcadeDrive(stick.getY(), stick.getX());}//non reverse direction
			
			
			//sets movement based off of - y axis and z axis
			Timer.delay(.005);//motor update
			if(stick.getRawButton(4)&&Limitup.get()) {updw.set(0.25);}//if the 4 button is pressed and the top limit switch isn't dosen't moves claw upwards
			else if(stick.getRawButton(5)&&Limitdw.get()) {updw.set(-0.25);}//if button 5 is pressed and bottom limit switch isn't the thing will go down
			else{updw.set(0);}
			Timer.delay(.005);
		}
		Timer.delay(.005);
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		Startingp = DriverStation.getInstance().getLocation();
		GameSide = DriverStation.getInstance().getGameSpecificMessage();//get witch side of the switch is friendly
		//System.out.println("Limit UP"+Limitup.get());
		//System.out.println("Button 4"+stick.getRawButton(4));
		//System.out.println("Limit Down"+Limitdw.get());
		//System.out.println("Button 5"+stick.getRawButton(5));
		//updw.set(1);
		System.out.println(GameSide.charAt(0));
		System.out.println(Startingp);
	}
}
