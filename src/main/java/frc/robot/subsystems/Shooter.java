// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import frc.robot.constants.Hardware;
import frc.robot.constants.shooter.ShooterConstants;
import frc.robot.utils.ModifiedSignalLogger;
import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

public class Shooter extends SubsystemBase
{
  private final TalonFX m_ShooterMotor1 = new TalonFX(Hardware.SHOOTER_MOTOR_1_ID);
  private final TalonFX m_ShooterMotor2 = new TalonFX(Hardware.SHOOTER_MOTOR_2_ID);
  private final TalonFX m_WristMotor = new TalonFX(Hardware.WRIST_MOTOR_ID);
  private final TalonFX m_FeederMotor = new TalonFX(Hardware.FEEDER_MOTOR_ID);
  private final CANcoder m_WristCANCoder = new CANcoder(Hardware.SHOOTER_WRIST_CANCODER_ID);

  /**
   * @brief Creates a new Shooter subsystem.
  */
  public Shooter() 
  {
    // Motor configuration
    m_ShooterMotor1.getConfigurator().apply(new TalonFXConfiguration());
    m_ShooterMotor2.getConfigurator().apply(new TalonFXConfiguration());
    m_WristMotor.getConfigurator().apply(new TalonFXConfiguration());
    m_FeederMotor.getConfigurator().apply(new TalonFXConfiguration());

    m_WristMotor.getConfigurator().apply(ShooterConstants.WRIST_MOTOR_CONFIGURATION);
    
    // CANCoder configuration
    m_WristCANCoder.getConfigurator().apply(ShooterConstants.WRIST_CANCODER_CONFIGURATION);
  }

  // Shooter Motor 1
  private VoltageOut Shooter1VOut = new VoltageOut(0);

  private SysIdRoutine m_Shooter1Routine = new SysIdRoutine(
    new SysIdRoutine.Config(null, null, null, ModifiedSignalLogger.logState()),
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> {m_ShooterMotor1.setControl(Shooter1VOut.withOutput(volts.in(volts.unit())));}, null, this));

  // Shooter Motor 2
  private VoltageOut Shooter2VOut = new VoltageOut(0);

  private SysIdRoutine m_Shooter2Routine = new SysIdRoutine(
    new SysIdRoutine.Config(null, null, null, ModifiedSignalLogger.logState()),
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> {m_ShooterMotor2.setControl(Shooter2VOut.withOutput(volts.in(volts.unit())));}, null, this));

  // Feeder Motor
  private VoltageOut FeederVOut = new VoltageOut(0);

  private SysIdRoutine m_FeederRoutine = new SysIdRoutine(
    new SysIdRoutine.Config(null, null, null, ModifiedSignalLogger.logState()),
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> {m_FeederMotor.setControl(FeederVOut.withOutput(volts.in(volts.unit())));}, null, this));
    
  // Wrist Motor
  private VoltageOut WristVOut = new VoltageOut(0);

  private SysIdRoutine m_WristRoutine = new SysIdRoutine(
    new SysIdRoutine.Config(null, null, null, ModifiedSignalLogger.logState()),
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> {m_WristMotor.setControl(WristVOut.withOutput(volts.in(volts.unit())));}, null, this));

  // Shooter 1 Commands
  public Command runShooter1QuasiTest(Direction direction)
  {
    return m_Shooter1Routine.quasistatic(direction);
  }

  public Command runShooter1DynamTest(SysIdRoutine.Direction direction)
  {
    return m_Shooter1Routine.dynamic(direction);
  }

  // Shooter 2 Tests
  public Command runShooter2QuasiTest(Direction direction)
  {
    return m_Shooter2Routine.quasistatic(direction);
  }

  public Command runShooter2DynamTest(SysIdRoutine.Direction direction)
  {
    return m_Shooter2Routine.dynamic(direction);
  }

  // Wrist Tests
  public Command runShooterWristQuasiTest(SysIdRoutine.Direction direction)
  {
    return m_WristRoutine.quasistatic(direction);
  }

  public Command runShooterWristDynamTest(SysIdRoutine.Direction direction)
  {
    return m_WristRoutine.dynamic(direction);
  }

  // Feeder Tests
  public Command runFeederQuasiTest(SysIdRoutine.Direction direction)
  {
    return m_FeederRoutine.quasistatic(direction);
  }

  public Command runFeederDynamTest(SysIdRoutine.Direction direction)
  {
    return m_FeederRoutine.dynamic(direction);
  }
  
  @Override
  public void periodic()
  {
    // Intentionally Empty
  }
}
