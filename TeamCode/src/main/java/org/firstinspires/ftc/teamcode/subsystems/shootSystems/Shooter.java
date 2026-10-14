package org.firstinspires.ftc.teamcode.subsystems.shootSystems;


import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDFCoefficients;
import dev.easyfamily.easystack.feedforward.FFController;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorType;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.InterpLUT;


public class Shooter implements EasySubsystem{

    //Instancias
    EasyMotor shooterMotorMaster;

    private InterpLUT shooterVelocity;

    // Fine Adjust in Teleop
    public double offset = 0;


    //PID
    public static double KP = 0.001;
    public static double KV = 0.00019;
    public static double KS = 0.09;



    @Override
    public void init(HardwareMap hardwareMap) {
        shooterMotorMaster = new EasyMotor(hardwareMap, "shooterMaster", EasyMotorType.REV_HD_NAKED)
                .reversed()
                .floatMode();
    }


    @Override
    public void loop() {
        configurePIDF();
    }



    /** SETTERS */
    public void setRPMOffset(double rpmOffset)
    {
        offset = rpmOffset;
    }


    public void setManualRpm(double targetRpm)
    {
        shooterMotorMaster.setRPM(targetRpm + offset);
    }


    public void shootIt(double distance)
    {
        setManualRpm(shooterVelocity.get(distance));
    }


    //GETTERS

    public double getOffsetDebug()
    {
        return offset;
    }


    public double getActualRPMDebug()
    {
        return shooterMotorMaster.getRPM();
    }

    public double getTargetRpmDebug()
    {
        return shooterMotorMaster.getTargetRPM();
    }



    public double getTargetInterluptRpmDebug(double distance)
    {
       return shooterVelocity.get(distance);
    }



    //OFF
    public void offShooter()
    {
        shooterMotorMaster.setPower(0);
    }
















    // SETTINGS
    private void configurePIDF()
    {
        shooterMotorMaster.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));
        shooterMotorMaster.setVelocityFF(new FFController(KS, KV));

    }



    public void lut()
    {
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.createLUT();
    }

}