package org.firstinspires.ftc.teamcode.subsystems;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDFCoefficients;
import dev.easyfamily.easystack.feedforward.FFController;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorType;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.InterpLUT;

@Config
public class Shooter implements EasySubsystem{

    //Instancias
    EasyMotor shooterMotorMaster;
    EasyServo hoodServo;
    EasyServo feeder;



    private InterpLUT shooterVelocity;
    private InterpLUT hoodPosition;



    // Fine Adjust in Teleop
    public double offset = 0;
    public double targetRpm = 0;


    //PID
    public static double KP = 0.001;
    public static double KV = 0.00019;
    public static double KS = 0.09;



    @Override
    public void init(HardwareMap hardwareMap) {
        lutShooter();
        shooterMotorMaster = new EasyMotor(hardwareMap, "shooterMaster", EasyMotorType.REV_HD_NAKED)
                .reversed()
                .floatMode();

        hoodServo = new EasyServo(hardwareMap, "hood");

        feeder = new EasyServo(hardwareMap, "feeder");

    }


    @Override
    public void loop() {
        configurePIDF();
    }





    /** HOOD *//// //////////////////////////////////////////////////////////

    /**

     ServoDoHood().setposition( servoPoseByInterlupt( robot.distanceToHive(x, y) ));

     */



    public EasyServo ServoDoHood(){
        return hoodServo;
    }


    public double servoPoseByInterlupt(double distance)
    {
        return hoodPosition.get(distance);
    }








    /** SHOOTER *//// /////////////////////////////////////////////////////////////

    /**

     shooter.setRpm( getTargetInterluptRpm(robot.distanceToCell(x, y)) ) ;
     shooter.shootIt();


     é só chamar isso e é gg

     */


    // RPM
    public void setRPMOffset(double rpmOffset)
    {
        offset = rpmOffset;
    }


    public void setRpm(double rpm)
    {
        targetRpm = rpm + offset;
    }

    public void shootIt()
    {
        shooterMotorMaster.setRPM(targetRpm);
    }

    public double getTargetInterluptRpm(double distance)
    {
        return shooterVelocity.get(distance);
    }




    //OFF
    public void offShooter()
    {
        shooterMotorMaster.setPower(0);
    }


    // TELEMETRY / DEBUG

    public double getActualRPMDebug()
    {
        return shooterMotorMaster.getRPM();
    }

    public double getTargetRpmDebug()
    {
        return targetRpm;
    }


    public double getShooterError()
    {
        return (targetRpm - shooterMotorMaster.getRPM());
    }

    public double getOffsetDebug()
    {
        return offset;
    }




    /** FEEDER *//// ///////////////////////////////////////////////////////


    public void unlockFeeder(){
        feeder.setPosition(0);
    }

    public void lockFeeder(){
        feeder.setPosition(1);
    }











    // SETTINGS
    private void configurePIDF()
    {
        shooterMotorMaster.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));
        shooterMotorMaster.setVelocityFF(new FFController(KS, KV));

    }


    /** LUTS *//////////////////////////////////////////////////////////

    public void lutShooter()
    {
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.createLUT();
    }


    public void lutHood()
    {
        hoodPosition.add(0, 0);
        hoodPosition.add(0, 0);
        hoodPosition.add(0, 0);
        hoodPosition.add(0, 0);
        hoodPosition.add(0, 0);
        hoodPosition.createLUT();
    }




}