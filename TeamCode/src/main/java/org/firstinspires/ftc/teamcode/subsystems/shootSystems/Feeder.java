package org.firstinspires.ftc.teamcode.subsystems.shootSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Feeder implements EasySubsystem {

    EasyServo servo;

    // Posições
    private double UNLOCK_POSE = 0.0;
    private double LOCK_POSE = 1.0;



    @Override
    public void init(HardwareMap hardwareMap)
    {
        servo = new EasyServo(hardwareMap, "feeder");
    }


    // Commands
    public void unlock()
    {
        servo.setPosition(0);
    }

    public void lock()
    {
        servo.setPosition(1);
    }


    // Debug
    public boolean isLocked()
    {
        return servo.getPosition() > 0.1;
    }




}
