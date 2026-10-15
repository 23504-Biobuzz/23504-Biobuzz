package org.firstinspires.ftc.teamcode.subsystems.shootSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.InterpLUT;

public class Hood implements EasySubsystem {

    EasyServo hood;

    InterpLUT hoodPosition;

    // Valor do servo
    private double servoPosition;


    @Override
    public void init(HardwareMap hardwareMap)
    {
        lutHood();
        hood = new EasyServo(hardwareMap, "hood");

    }


    @Override
    public void loop()
    {
        hood.setPosition(servoPosition);
    }



    public void setPositionByDistance(double distance)
    {
        servoPosition = hoodPosition.get(distance);
    }

     public void setPositionManual(double position)
    {
        servoPosition = position;
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
