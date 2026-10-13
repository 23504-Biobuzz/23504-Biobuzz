package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.hardware.Servo.EasyServoGroup;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Flower implements EasySubsystem {

    private EasyServo servo_left;
    private EasyServo servo_right;
    private EasyServoGroup flower;
    public double currentPosition;
    public Telemetry telemetry;


    @Override
    public void init(HardwareMap hardwareMap) {
        servo_left = new EasyServo(hardwareMap, "servo_left");
        servo_right = new EasyServo(hardwareMap, "servo_right");
        flower = new EasyServoGroup(servo_left, servo_right);
        currentPosition = 0;
    }

    public double getCurrentPosition(){
        return flower.getPosition();
    }
    public void upFlower(){
        flower.setPosition(0.870);
    }
    public void downFlower(){
        flower.setPosition(0);
    }
    public void setPositionFlower(double position){
        flower.setPosition(position);
    }

    @Override
    public void loop() {
        currentPosition = getCurrentPosition();
        getTelemetry();
    }

    public void getTelemetry(){
        telemetry.addData("Current Servos Position", currentPosition);
        telemetry.addData("Servo Left Position", servo_left.getPosition());
        telemetry.addData("Servo Right Position", servo_right.getPosition());
    }
}
