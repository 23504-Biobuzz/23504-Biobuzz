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



    public EasyServo ServoDoHood()
    {
        return hoodServo;
    }


    public double servoPoseByInterlupt(double distance)
    {
        return hoodPosition.get(distance);
    }




    /** SHOOTER *//// /////////////////////////////////////////////////////////////
    /**

     shooter.shootIt( getTargetInterluptRpm(robot.distanceToCell(x, y)) );


     é só chamar isso e é gg

     */




    public void shootIt(double rpm, double offset)
    {
        shooterMotorMaster.setRPM(rpm + offset);
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



    /** FEEDER *//// ///////////////////////////////////////////////////////


    public void unlockFeeder(){
        feeder.setPosition(0);
    }

    public void lockFeeder(){
        feeder.setPosition(1);
    }


    public boolean islocked()
    {
       return feeder.getPosition() > 0.6;
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