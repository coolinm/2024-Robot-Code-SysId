// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.constants.intake;

import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;

/** Add your docs here. */
public class IntakeConstants
{
    public static final SoftwareLimitSwitchConfigs INTAKE_WRIST_SOFT_LIMITS = new SoftwareLimitSwitchConfigs()
        //.withForwardSoftLimitEnable(true)
        //.withReverseSoftLimitEnable(true)
        .withForwardSoftLimitThreshold(0)
        .withReverseSoftLimitThreshold(0);
}

   
