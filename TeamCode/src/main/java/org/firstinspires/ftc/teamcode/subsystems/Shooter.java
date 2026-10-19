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
import dev.easyfamily.easystack.util.InterpLUT;

@Config
public class Shooter implements EasySubsystem {

    private EasyMotor shooter_motor;

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
    public static double KP = 0.01;
    public static double KV = 0.000162;
    public static double KS = 0.12;


    // Lupt de rpm
    private InterpLUT shooterVelocity;

    private InterpLUT hoodAngleLut;


    // Ftc DashBoard Tunning Methods
    public void onShooter() {
        shooter_motor.setRPM(target + RPMoffset);
    }

    public void setServoPosition(double servoPositionChoosed){
        hoodServo.setPosition(servoPositionChoosed);
    }

    public void offShooter() {
        shooter_motor.setPower(0);
    }






    // Metodo pra usar a interpolacao cubica  de hermite com tangentes limitadas no RPM
    public void setRpmByDistance(double distance){
        shooter_motor.setRPM(shooterVelocity.get(distance) + RPMoffset);
    }


    public void setHoodByDistance(double distance){
        hoodServo.setPosition(hoodAngleLut.get(distance));
    }








    /** Metodos do Easy */
    @Override
    public void init(HardwareMap hardwareMap) {

        shooter_motor = new EasyMotor(hardwareMap, "shooterMaster", EasyMotorType.REV_HD_NAKED)
                .reverse();

         LedShooterGuloso = new EasyServo(hardwareMap, "ledShooter"); // **************************************

       // hoodServo = new EasyServo(hardwareMap, "hood");

    }


    @Override
    public void loop() {
        currentRPM = shooter_motor.getRPM();
        currentTicks = shooter_motor.getVelocity();
        errorRPM = shooter_motor.getRPMError();

        loopShooterLEd(); // **************************************************

        configurePIDF();
    }




    /** Metodos de configuracao */
    private void configurePIDF() {
        // Mover pra o init quando terminar de calibrar
        shooter_motor.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));
        shooter_motor.setVelocityFF(new FFController(KS, KV));

        // TIRAR isso aqui quando terminar a calibracao
       // setServoPosition(servoPosition);

    }


    public void loopShooterLEd() // ***********************************************************
    {

        if (Math.abs( target ) <= rpmTolerence) {

            LedShooterGuloso.setPosition(1);
        } else {
            LedShooterGuloso.setPosition(0);
        }


    }



    public void lutShooter() {
        shooterVelocity.add(20, 0);
        shooterVelocity.add(40, 0);
        shooterVelocity.add(60, 0);
        shooterVelocity.add(80, 0);
        shooterVelocity.add(100, 0);
        shooterVelocity.add(120, 0);
        shooterVelocity.add(140, 0);
        shooterVelocity.createLUT();
    }


    public void lutHood() {
        hoodAngleLut.add(20, 0);
        hoodAngleLut.add(40, 0);
        hoodAngleLut.add(60, 0);
        hoodAngleLut.add(80, 0);
        hoodAngleLut.add(100, 0);
        hoodAngleLut.add(120, 0);
        hoodAngleLut.add(140, 0);
        hoodAngleLut.createLUT();
    }
}