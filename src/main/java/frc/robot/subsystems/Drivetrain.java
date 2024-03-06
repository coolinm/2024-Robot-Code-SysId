// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix6.SignalLogger;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrain;
import com.ctre.phoenix6.mechanisms.swerve.SwerveDrivetrainConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveModuleConstants;
import com.ctre.phoenix6.mechanisms.swerve.SwerveRequest;

import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import frc.robot.utils.ModifiedSignalLogger;

public class Drivetrain extends SwerveDrivetrain implements Subsystem 
{
  private Timer m_timer;

  /** 
    @brief Creates a new Drivetrain.
    @param driveTrainConstants Drivetrain-wide constants for the swerve drive
    @param OdometryUpdateFrequency The frequency to run the odometry loop. If unspecified, this is 250 Hz on CAN FD, and 100 Hz on CAN 2.0
    @param modules Constants for each specific module 
  */
  public Drivetrain(SwerveDrivetrainConstants driveTrainConstants, double OdometryUpdateFrequency, SwerveModuleConstants... modules)
  {
    super(driveTrainConstants, OdometryUpdateFrequency, modules);
  }

  /** 
    @brief Creates a new Drivetrain without specifying the frequency to run the odometry loop.
    @param driveTrainConstants Drivetrain-wide constants for the swerve drive
    @param modules Constants for each specific module 
  */
  public Drivetrain(SwerveDrivetrainConstants driveTrainConstants, SwerveModuleConstants... modules)
  {
    super(driveTrainConstants, modules);
    // Create a photon camera and pose estimator object

    // Create a timer for less critical tasks such as Smartdashboard updates
    this.m_timer = new Timer();
    m_timer.start();
  }

  /**
   * @brief Applies a swerve request to the drivetrain
   * @param requestSupplier Supplier for the swerve request
   * @return Applies the given swerve request to the swerve modules
   */
  public Command applyRequest(Supplier<SwerveRequest> requestSupplier)
  {
    return run(() -> this.setControl(requestSupplier.get()));  
  }

  private final SwerveRequest.SysIdSwerveTranslation TranslationCharacterization = new SwerveRequest.SysIdSwerveTranslation();
    private final SwerveRequest.SysIdSwerveRotation RotationCharacterization = new SwerveRequest.SysIdSwerveRotation();
    private final SwerveRequest.SysIdSwerveSteerGains SteerCharacterization = new SwerveRequest.SysIdSwerveSteerGains();

  Measure<Voltage> stepVoltage = edu.wpi.first.units.Units.Volts.of(4);
  Measure<Voltage> stepVoltage2 = edu.wpi.first.units.Units.Volts.of(7);

    /* Use one of these sysidroutines for your particular test */
    private SysIdRoutine SysIdRoutineTranslation = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    stepVoltage,
                    null,
                    (state) -> SignalLogger.writeString("state", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> setControl(TranslationCharacterization.withVolts(volts)),
                    null,
                    this));

    private final SysIdRoutine SysIdRoutineRotation = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    stepVoltage,
                    null,
                    (state) -> SignalLogger.writeString("state", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> setControl(RotationCharacterization.withVolts(volts)),
                    null,
                    this));

    private final SysIdRoutine SysIdRoutineSteer = new SysIdRoutine(
            new SysIdRoutine.Config(
                    null,
                    stepVoltage2,
                    null,
                    (state) -> SignalLogger.writeString("state", state.toString())),
            new SysIdRoutine.Mechanism(
                    (volts) -> setControl(SteerCharacterization.withVolts(volts)),
                    null,
                    this));

  // CHANGE THIS TO CHANGE THE ROUTINE BEING USED
  private final SysIdRoutine RoutineToApply = SysIdRoutineTranslation;

  public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return RoutineToApply.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return RoutineToApply.dynamic(direction);
    }

  @Override
  public void periodic() 
  {
    // Intentionally Empty
  }
}
