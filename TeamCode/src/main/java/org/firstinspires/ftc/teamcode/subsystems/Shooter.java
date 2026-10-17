package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDFCoefficients;
import dev.easyfamily.easystack.feedforward.FFCoefficients;
import dev.easyfamily.easystack.feedforward.FFController;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorType;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.InterpLUT;

public class Shooter implements EasySubsystem {

    private EasyMotor shooter_motor;
    private EasyServo block_servo;
    public double currentRPM = 0.0;
    public double currentTicks = 0.0;
    public double errorRPM = 0.0;
    public static double target = 3000;

    public static double KP = 0.001;
    public static double KV = 0.00019;
    public static double KS = 0.09;

    private InterpLUT shooterVelocity;

    public void onShooter() {
        shooter_motor.setPower(1);
    }

    public void offShooter() {
        shooter_motor.setPower(0);
    }

    private void configurePIDF() {
        shooter_motor.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));
        shooter_motor.setVelocityFF(new FFController(KS, KV));

    }

    public void lut() {
        shooterVelocity.add(20, 2000);
        shooterVelocity.add(40, 2200);
        shooterVelocity.add(60, 2400);
        shooterVelocity.add(80, 2600);
        shooterVelocity.add(100, 2800);
        shooterVelocity.add(120, 3000);
        shooterVelocity.add(140, 3200);
        shooterVelocity.createLUT();
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        shooter_motor = new EasyMotor(hardwareMap, "shooterMaster", EasyMotorType.REV_HD_NAKED)
                .reverse();
        //block_servo = new EasyServo(hardwareMap, "block_servo");
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