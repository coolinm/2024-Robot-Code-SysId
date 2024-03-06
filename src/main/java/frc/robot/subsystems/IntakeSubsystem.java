// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.units.Measure;
import edu.wpi.first.units.Time;
import edu.wpi.first.units.Units;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.constants.Hardware;
import frc.robot.constants.intake.IntakeConstants;
import frc.robot.utils.ModifiedSignalLogger;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;


public class IntakeSubsystem extends SubsystemBase 
{
  /** Creates a new IntakeSubsystem. */

  private final TalonFX m_RollerMotor = new TalonFX(Hardware.INTAKE_ROLLER_MOTOR_ID);
  private final TalonFX m_WristMotor = new TalonFX(Hardware.INTAKE_WRIST_MOTOR_ID);


  public IntakeSubsystem()
  {
    this.m_RollerMotor.getConfigurator().apply(new TalonFXConfiguration());
    this.m_WristMotor.getConfigurator().apply(new TalonFXConfiguration());

    this.m_WristMotor.getConfigurator().apply(IntakeConstants.INTAKE_WRIST_SOFT_LIMITS);
  }

  // Roller Motor
  private VoltageOut RollerVOut = new VoltageOut(0);

  private SysIdRoutine m_RollerRoutine = new SysIdRoutine(
    new SysIdRoutine.Config(null, null, null, ModifiedSignalLogger.logState()),
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> {m_RollerMotor.setControl(RollerVOut.withOutput(volts.in(volts.unit())));}, null, this));

  // Wrist Motor
  private VoltageOut WristVOut = new VoltageOut(0);

  private Measure<Velocity<Voltage>> rampVoltage = Units.Volts.of(0.3).per(Units.Seconds.of(1));
  private Measure<Voltage> stepVoltage = Units.Volts.of(2);
  private Measure<Time> timeout = Units.Seconds.of(60);

  private SysIdRoutine m_WristRoutine = new SysIdRoutine(
    new SysIdRoutine.Config(rampVoltage, stepVoltage, timeout, ModifiedSignalLogger.logState()),
    new SysIdRoutine.Mechanism(
      (Measure<Voltage> volts) -> {m_WristMotor.setControl(WristVOut.withOutput(volts.in(volts.unit())));}, null, this));

  // Roller Commands
  public Command runRollerQuasiTest(Direction direction)
  {
    return m_RollerRoutine.quasistatic(direction);
  }

  public Command runRollerDynamTest(SysIdRoutine.Direction direction)
  {
    return m_RollerRoutine.dynamic(direction);
  }

  // Wrist Commands
  public Command runWristQuasiTest(Direction direction)
  {
    return m_WristRoutine.quasistatic(direction);
  }

  public Command runWristDynamTest(SysIdRoutine.Direction direction)
  {
    return m_WristRoutine.dynamic(direction);
  }
   @Override
  public void periodic()
  {
    // Intentionally Empty
  }
}
