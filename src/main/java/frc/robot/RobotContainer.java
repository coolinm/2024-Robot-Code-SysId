// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.Hardware;
import frc.robot.constants.drivetrain.TunerConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;


public class RobotContainer 
{
  // Subsystems
  private final Drivetrain m_Drivetrain = new Drivetrain(TunerConstants.DRIVETRAIN_CONSTANTS, TunerConstants.FRONT_LEFT,
                      TunerConstants.FRONT_RIGHT, TunerConstants.BACK_LEFT, TunerConstants.BACK_RIGHT);
  private final Elevator m_Elevator = new Elevator();
  private final Shooter m_Shooter = new Shooter();
  private final IntakeSubsystem m_Intake = new IntakeSubsystem();
  

  // Controllers
  private final CommandXboxController m_primaryController = new CommandXboxController(Hardware.PRIMARY_CONTROLLER_PORT);

  public RobotContainer() 
  {
    configureBindings();
  }
  
  private void configureBindings() 
  {
    m_primaryController.x().onTrue(m_Shooter.runShooter1QuasiTest(Direction.kForward));
    m_primaryController.y().onTrue(m_Shooter.runShooter1QuasiTest(Direction.kReverse));
    m_primaryController.a().onTrue(m_Shooter.runShooter1DynamTest(Direction.kForward));
    m_primaryController.b().onTrue(m_Shooter.runShooter1DynamTest(Direction.kReverse));
  }

  public Command getAutonomousCommand() 
  {
    return Commands.print("No autonomous command configured");
  }
}
