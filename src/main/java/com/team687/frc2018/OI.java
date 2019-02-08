package com.team687.frc2018;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.nerdherd.lib.drivetrain.auto.DriveDistanceMotionMagic;
import com.nerdherd.lib.drivetrain.auto.ResetDriveEncoders;
import com.nerdherd.lib.drivetrain.characterization.DriveCharacterizationTest;
import com.nerdherd.lib.motor.commands.SetMotorPower;
import com.nerdherd.lib.oi.DefaultOI;
import com.team687.frc2018.commands.intake.ClawClose;
import com.team687.frc2018.commands.intake.ClawOpen;
import com.team687.frc2018.commands.superstructure.AdjustForwardsScale;
import com.team687.frc2018.commands.superstructure.DefaultIntake;
import com.team687.frc2018.commands.superstructure.DefaultStow;
import com.team687.frc2018.commands.superstructure.IntakeSequenceCurrent;
import com.team687.frc2018.commands.superstructure.StowToForwardsScale;
import com.team687.frc2018.commands.superstructure.SwitchScorePositionTeleop;
import com.team687.frc2018.constants.SuperstructureConstants;
import com.team687.frc2018.utilities.NerdyMath;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */

public class OI extends DefaultOI {

    private String m_filePath1 = "/media/sda1/logs/";
    private String m_filePath2 = "/home/lvuser/logs/";
    private File m_file;
    public FileWriter m_writer;
    private boolean writeException = false;
    private double m_logStartTime;


    // public Joystick gamepadJoy = new Joystick(0);

    
    public JoystickButton intake_1;
    public JoystickButton outtake_2;
    public JoystickButton stopIntake_3;
    public JoystickButton openCloseClaw_4;
    // public JoystickButton intakePosition_4;

    public JoystickButton switchPosition_11;
    public JoystickButton intakeRollers_9;
    public JoystickButton stowToForwards_7;
    public JoystickButton adjustMiddle_8;
    public JoystickButton defaultStow_10;
    
    public JoystickButton openClaw_6;
    public JoystickButton closeClaw_5;
    
    // public JoystickButton flipCube_12;
    public JoystickButton backupStow_12;
    
    // public JoystickButton sketchyStowToBackwards_12;

    public OI() {
        super();
        // SmartDashboard.putData("Intake 3 volts", new SetMotorPower(Robot.intake, 0.5));
        // SmartDashboard.putData("Open Claw", new ExtendPiston(Robot.claw));
        // SmartDashboard.putData("Close Claw", new RetractPiston(Robot.claw));
        // SmartDashboard.putData("Reset Arm Encoder", new ResetArmEncoder());
        // SmartDashboard.putData("Reset Wrist Encoder", new ResetWristEncoder());
        // SmartDashboard.putData("Stow", new DefaultStow());
        SmartDashboard.putData("Reset Drive Encoders", new ResetDriveEncoders(Robot.drive));
        // SmartDashboard.putData("Reset Gyro", new ResetGyro());
        // SmartDashboard.putData("Intake 3V ", new SetMotorPower(Robot.intake, .25));
        // SmartDashboard.putData("Intake 6V ", new SetMotorPower(Robot.intake, .5));
        // SmartDashboard.putData("Intake 9V ", new SetMotorPower(Robot.intake, 0.75));
        // SmartDashboard.putData("Intake 12V ", new SetMotorPower(Robot.intake, 1));
        // SmartDashboard.putData("Intake -3V ", new SetMotorPower(Robot.intake, -0.25));

        SmartDashboard.putData("Drive Characterization", new DriveCharacterizationTest(Robot.drive, 0.25));
        // SmartDashboard.putData("6 V open loop", new OpenLoopDrive(0.5));
        SmartDashboard.putData("Drive Motion Magic", new DriveDistanceMotionMagic(Robot.drive, 15000, 500, 500));
        // SmartDashboard.putData("Drive Trajectory", new DriveTrajectory(AutoConstants.testTraj, 3, true, 0.3, 0));
        // SmartDashboard.putData("Backwards Trajectory", new DriveTrajectory(AutoConstants.BackwardsTraj, 3, false, 0.3, 0));

        
        intake_1 = new JoystickButton(super.operatorJoy, 1);
        intake_1.whenPressed(new DefaultIntake());
        
        outtake_2 = new JoystickButton(super.operatorJoy, 2);
        outtake_2.whenPressed(new SetMotorPower(Robot.intake, -0.5));
        stopIntake_3 = new JoystickButton(super.operatorJoy, 3);
        stopIntake_3.whenPressed(new SetMotorPower(Robot.intake, 0));
        openCloseClaw_4 = new JoystickButton(super.operatorJoy, 4);
        openCloseClaw_4.whenPressed(new IntakeSequenceCurrent());
        // intakePosition_4 = new JoystickButton(super.operatorJoy, 4);
        // intakePosition_4.whenPressed(new DefaultIntake());

        intakeRollers_9 = new JoystickButton(super.operatorJoy, 9);
        intakeRollers_9.whenPressed(new SetMotorPower(Robot.intake, .8333333333333333));
    //	intakeRollers_9.whenPressed(new StackCubes(0));
        stowToForwards_7 = new JoystickButton(super.operatorJoy, 7);
        
        stowToForwards_7.whenPressed(new StowToForwardsScale());
    //	stowToForwards_7.whenPressed(new StackCubes(-15));
        
        adjustMiddle_8 = new JoystickButton(super.operatorJoy, 8);
        adjustMiddle_8.whenPressed(new AdjustForwardsScale(SuperstructureConstants.kArmMiddleScalePosition));
        defaultStow_10 = new JoystickButton(super.operatorJoy, 10);
        defaultStow_10.whenPressed(new DefaultStow());
        switchPosition_11 = new JoystickButton(super.operatorJoy, 11);
        switchPosition_11.whenPressed(new SwitchScorePositionTeleop());
    //	switchPosition_11.whenPressed(new StackCubes(35));

        openClaw_6 = new JoystickButton(super.operatorJoy, 5);
        openClaw_6.whenPressed(new ClawOpen());
        closeClaw_5 = new JoystickButton(super.operatorJoy, 6);
        closeClaw_5.whenPressed(new ClawClose());
        // flipCube_12 = new JoystickButton(super.operatorJoy, 12);
        // flipCube_12.whenPressed(new FlipCube());
        backupStow_12 = new JoystickButton(super.operatorJoy, 12);
        backupStow_12.whenPressed(new DefaultStow());

    }

}
