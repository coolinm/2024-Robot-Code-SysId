package frc.robot.constants.shooter;

import com.ctre.phoenix6.configs.CANcoderConfiguration;
import com.ctre.phoenix6.configs.FeedbackConfigs;
import com.ctre.phoenix6.configs.MagnetSensorConfigs;
import com.ctre.phoenix6.configs.SoftwareLimitSwitchConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;

import com.ctre.phoenix6.signals.SensorDirectionValue;

import frc.robot.constants.Hardware;

public class ShooterConstants
{
    public static final double SHOOTER_WRIST_MAX_FORWARD_ROTATION = .2;
    public static final double SHOOTER_WRIST_MAX_REVERSE_ROTATION = 0;

    public static final SoftwareLimitSwitchConfigs WRIST_SOFT_LIMIT_CONFIGS = new SoftwareLimitSwitchConfigs()
        .withForwardSoftLimitEnable(true)
        .withForwardSoftLimitThreshold(SHOOTER_WRIST_MAX_FORWARD_ROTATION)
        .withReverseSoftLimitEnable(true)
        .withReverseSoftLimitThreshold(SHOOTER_WRIST_MAX_REVERSE_ROTATION);

    public static final FeedbackConfigs WRIST_FEEDBACK_CONFIGS = new FeedbackConfigs()
        .withFeedbackRemoteSensorID(Hardware.SHOOTER_WRIST_CANCODER_ID)
        .withFeedbackSensorSource(FeedbackSensorSourceValue.RemoteCANcoder)
        .withRotorToSensorRatio(1)
        .withSensorToMechanismRatio(1);
    
    // Wrist CANCoder
    public static final MagnetSensorConfigs WRIST_CANCODER_MAGNET_CONFIGS = new MagnetSensorConfigs()
        .withSensorDirection(SensorDirectionValue.Clockwise_Positive)
        .withMagnetOffset(0.269);

    public static final TalonFXConfiguration WRIST_MOTOR_CONFIGURATION = new TalonFXConfiguration()
        .withFeedback(WRIST_FEEDBACK_CONFIGS)
        .withSoftwareLimitSwitch(WRIST_SOFT_LIMIT_CONFIGS);

    public static final CANcoderConfiguration WRIST_CANCODER_CONFIGURATION = new CANcoderConfiguration()
        .withMagnetSensor(WRIST_CANCODER_MAGNET_CONFIGS);

    // Motor Enum
    public enum MOTOR
    {
        MOTOR_1,
        MOTOR_2,
        WRIST_MOTOR,
        FEEDER_MOTOR
    }
}
