package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDFCoefficients;
import dev.easyfamily.easystack.feedforward.FFCoefficients;
import dev.easyfamily.easystack.feedforward.FFController;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorType;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

@Config
public class Shooter implements EasySubsystem {

    private EasyMotor shooter_motor;
    private EasyServo block_servo;
    public double currentRPM = 0.0;
    public double currentTicks = 0.0;
    public double errorRPM = 0.0;
    public static double target = 0;

    public static double KP = 0.001;
    public static double KV = 0.00019;
    public static double KS = 0.09;

    public void onShooter() {
        shooter_motor.setRPM(target);
    }

    public void offShooter() {
        shooter_motor.setPower(0);
    }

    private void configurePIDF() {
        shooter_motor.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));
        shooter_motor.setVelocityFF(new FFController(KS, KV));

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        shooter_motor = new EasyMotor(hardwareMap, "shooter_motor", EasyMotorType.REV_HD_NAKED);
       // block_servo = new EasyServo(hardwareMap, "block_servo");
        //configurePIDF();

    }

    @Override
    public void loop() {
        currentRPM = shooter_motor.getRPM();
        currentTicks = shooter_motor.getVelocity();
        errorRPM = shooter_motor.getRPMError();

        configurePIDF();
    }
}