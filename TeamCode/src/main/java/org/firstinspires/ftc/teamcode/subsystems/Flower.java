package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.wpiLibControllers.EasyElevatorFeedforward;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.hardware.Servo.EasyServoGroup;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

@Config
public class Flower implements EasySubsystem {

    // Instancias
    private EasyServo servo_left;
    private EasyServo servo_right;
    public EasyServoGroup flower;

    // position
    public static double servoFLowerValue = 0;


    @Override
    public void init(HardwareMap hardwareMap) {
        servo_left = new EasyServo(hardwareMap, "servo_left");
        servo_right = new EasyServo(hardwareMap, "servo_right");
        flower = new EasyServoGroup(servo_left, servo_right);

        flower.addState("collect", 0.1);
        flower.addState("retracted", 0.5);
    }


    @Override
    public void loop() {
        setServoPosition();
    }



    public void setServoPosition() {
        flower.setPosition(servoFLowerValue);
    }

    public void retractFlower(){
        flower.setState("retracted");
    }

    public void collectFlower(){
        flower.setState("collect");
    }


}
