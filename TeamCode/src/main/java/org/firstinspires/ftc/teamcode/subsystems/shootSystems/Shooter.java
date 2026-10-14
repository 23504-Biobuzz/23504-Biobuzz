package org.firstinspires.ftc.teamcode.subsystems.shootSystems;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDFCoefficients;
import dev.easyfamily.easystack.feedforward.FFController;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorType;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.InterpLUT;

@Config
public class Shooter implements EasySubsystem{

    //Instancias
    EasyMotor shooterMotorMaster;

    private InterpLUT shooterVelocity;

    // Fine Adjust in Teleop
    public double offset = 0;

    public double targetRpm = 0;


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


    public void setRpm(double rpm)
    {
        targetRpm = rpm + offset;
    }

    public double getTargetInterluptRpm(double distance)
    {
        return shooterVelocity.get(distance);
    }



    public void shootIt()
    {
       shooterMotorMaster.setRPM(targetRpm);
    }


    // shooter.setRpm( getTargetInterluptRpm(robot.distanceToCell(x, y)) ) ;
    // shooter.shootIt();

    // é só chamar isso e é gg



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
        return targetRpm;
    }


    public double getShooterError()
    {
        return (targetRpm - shooterMotorMaster.getRPM());
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