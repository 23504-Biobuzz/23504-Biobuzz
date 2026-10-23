package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDFCoefficients;
import dev.easyfamily.easystack.feedforward.FFCoefficients;
import dev.easyfamily.easystack.feedforward.FFController;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorGroup;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorType;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.InterpLUT;

@Config
public class Shooter implements EasySubsystem {

    private EasyMotor shooter_left;
    private EasyMotor shooter_right;
    private EasyMotorGroup shooter_motors;
    private EasyServo hoodServo;

    private EasyServo LedShooterGuloso; // **********************************



    // Variavel
    public double currentRPM = 0.0;
    public double currentTicks = 0.0;
    public double errorRPM = 0.0;
    public static double target = 0;

    public static double RPMoffset = 0;

    public static double rpmTolerence = 70; // PRA ACENDER LED


    // PID
    public static double KP = 0.001; //0.001
    public static double KV = 0.000215; //0.00019
    public static double KS = 0.1;

    public static double position = 0.55;


    // Lupt de rpm
    public InterpLUT shooterVelocity = new InterpLUT();

    public InterpLUT hoodAngleLut = new InterpLUT();


    // Ftc DashBoard Tunning Methods
    public void onShooter() {
        shooter_motors.setRPM(target + RPMoffset);
    }

    public void offShooter() {
        shooter_motors.setPower(0);
    }


    // Metodo pra usar a interpolacao cubica  de hermite com tangentes limitadas no RPM
    public void setShooter(double distance) {
        shooter_motors.setRPM(shooterVelocity.get(distance) + RPMoffset);
        hoodServo.setPosition(hoodAngleLut.get(distance));
    }


    /** Metodos do Easy */
    @Override
    public void init(HardwareMap hardwareMap) {

        shooter_left = new EasyMotor(hardwareMap, "shooter_left", EasyMotorType.REV_HD_NAKED);
        shooter_right = new EasyMotor(hardwareMap, "shooter_right", EasyMotorType.REV_HD_NAKED);
        shooter_motors = new EasyMotorGroup(shooter_left, shooter_right).setLeaderMotor(0);

       //  LedShooterGuloso = new EasyServo(hardwareMap, "ledShooter"); // **************************************

        hoodServo = new EasyServo(hardwareMap, "hood");
        lut();

    }


    @Override
    public void loop() {
        currentRPM = shooter_motors.getRPM();
        currentTicks = shooter_motors.getVelocity();

       // loopShooterLEd(); // **************************************************

        configurePIDF();


    }




    /** Metodos de configuracao */
    private void configurePIDF() {
        shooter_motors.setVelocityFF(new FFController(KS, KV));
        shooter_motors.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));

    }


    public void loopShooterLEd() // ***********************************************************
    {

        if (Math.abs( target ) <= rpmTolerence) {

            LedShooterGuloso.setPosition(1);
        } else {
            LedShooterGuloso.setPosition(0);
        }


    }



    public void lut() {
        shooterVelocity.add(70.0, 3500.0);
        shooterVelocity.add(60.0, 3300.0);
        shooterVelocity.add(50.0, 3000.0);
        shooterVelocity.add(40.0, 2900.0);
        shooterVelocity.add(30.0, 2900.0);
        shooterVelocity.add(20.0, 2900.0);
        shooterVelocity.createLUT();

        hoodAngleLut.add(70.0, 0.0);
        hoodAngleLut.add(60.0, 0.0);
        hoodAngleLut.add(50.0, 0.0);
        hoodAngleLut.add(40.0, 0.0);
        hoodAngleLut.add(30.0, 0.2);
        hoodAngleLut.add(20.0, 0.5);
        hoodAngleLut.createLUT();
    }
}