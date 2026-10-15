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
public class Shooter implements EasySubsystem {
    private EasyMotor shooterMotorMaster;

    private InterpLUT shooterVelocity;



    //PID
    public static double KP = 0.000;
    public static double KV = 0.000;
    public static double KS = 0.000;

    public static double rpmAlvo = 0;

    public static double offset = 0;



    @Override
    public void init(HardwareMap hardwareMap) {
        lutShooter();
        shooterMotorMaster = new EasyMotor(hardwareMap, "shooterMaster", EasyMotorType.REV_HD_NAKED)
                .reversed()
                .floatMode();
    }


    @Override
    public void loop() {
        configurePIDF();
    }



    // Setters de velocidade
    public void setTargetRpm(double rpm){
        rpmAlvo = rpm;
    }

    public void setRpmByDistance(double distance)
    {
        rpmAlvo = shooterVelocity.get(distance);
    }




    public void setOffsetRpm(double choosedOffset){
        offset = choosedOffset;
    }




    // Principais

    public void shootIt()
    {
        shooterMotorMaster.setRPM(rpmAlvo + offset);
    }




    //OFF
    public void offShooter()
    {
        shooterMotorMaster.setPower(0);
    }



    // TELEMETRY / DEBUG

    public double getActualRPM()
    {
        return shooterMotorMaster.getRPM();
    }


    public double getTargetedRpm(){
        return rpmAlvo;
    }

    public double getRpmError(){
        return getTargetedRpm() - getActualRPM();
    }






    // Configuração de PID shooter
    private void configurePIDF()
    {
        shooterMotorMaster.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));
        shooterMotorMaster.setVelocityFF(new FFController(KS, KV));

    }


    // Shooter Interlupt
    public void lutShooter()
    {
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.add(0, 0);
        shooterVelocity.createLUT();
    }
}
