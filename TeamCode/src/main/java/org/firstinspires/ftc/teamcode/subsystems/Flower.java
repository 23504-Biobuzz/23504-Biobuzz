package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import dev.easyfamily.easystack.control.wpiLibControllers.EasyElevatorFeedforward;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.hardware.Servo.EasyServoGroup;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Flower implements EasySubsystem {

    private EasyServo servo_left;
    private EasyServo servo_right;
    private Telemetry telemetry;

    private EasyServoGroup flower;

    public void upFlower(){
        flower.setPosition(0.875);
    }
    public void downFlower(){
        flower.setPosition(0);
    }

    public double currentPosition;

    public double getCurrentPosition(){
       return flower.getPosition();
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        servo_left = new EasyServo(hardwareMap, "servo_left");
        servo_right = new EasyServo(hardwareMap, "servo_right");

        flower = new EasyServoGroup(servo_left, servo_right);
        flower.reverse();
    }

    @Override
    public void loop() {
        currentPosition = getCurrentPosition();
        getTelemetry();
    }
    public void getTelemetry(){
        telemetry.addData("Posição atual", getCurrentPosition());
    }

}
