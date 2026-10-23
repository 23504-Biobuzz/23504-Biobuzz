package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import dev.easyfamily.easystack.control.wpiLibControllers.EasyElevatorFeedforward;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.hardware.Servo.EasyServoGroup;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

@Config
public class Flower implements EasySubsystem {

    // Instancias
    private EasyServo servo_right;


    // position
    public static double servoFLowerValue;


    @Override
    public void init(HardwareMap hardwareMap) {
        servo_right = new EasyServo(hardwareMap, "right_servo_flower");

        servo_right.addState("collect", 0.55);
        servo_right.addState("retracted", 1);

    }


    @Override
    public void loop() {

    }



    public void setServoPosition() {
        servo_right.setPosition(servoFLowerValue);
    }

    public void retractFlower(){
        servo_right.setState("retracted");
    }

    public void collectFlower(){
        servo_right.setState("collect");
    }


}
