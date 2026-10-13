package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.wpiLibControllers.EasyElevatorFeedforward;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.hardware.Servo.EasyServoGroup;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Flower implements EasySubsystem {

    private EasyServo servo_left;
    private EasyServo servo_right;

    private EasyServoGroup flower;

    @Override
    public void init(HardwareMap hardwareMap) {
        servo_left = new EasyServo(hardwareMap, "servo_left");
        servo_right = new EasyServo(hardwareMap, "servo_right");
        flower = new EasyServoGroup(servo_left, servo_right);
    }

    @Override
    public void loop() {

    }
}
