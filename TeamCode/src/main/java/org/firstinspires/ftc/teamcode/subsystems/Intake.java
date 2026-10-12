package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorGroup;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorType;
import dev.easyfamily.easystack.hardware.Sensors.EasyRevColorSensorV3;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Intake implements EasySubsystem {

    public double currentTicks;

    public EasyMotor intake_motor_front;
    public EasyMotor intake_motor_back;
    public EasyMotorGroup intake_motors;

    public EasyRevColorSensorV3 color_sensor_left;
    public EasyRevColorSensorV3 color_sensor_right;

    public EasyServo led_back;

    public double distance_left;
    public double distance_right;

    public boolean full = false;

    private long detectedSince = -1;
    private final long confirm = 300;

    private static final double DETECTION_DISTANCE_CM = 2.75;

    public void onIntake() {
        intake_motors.setPower(1);
    }

    public void offIntake() {
        intake_motors.setPower(0);
    }

    public double getVelocity() {
        return intake_motors.getVelocity();
    }

    public double getDistanceLeft() {
        return color_sensor_left.getDistance(DistanceUnit.CM);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        intake_motor_front = new EasyMotor(hardwareMap, "intake_motor_front").reversed();
        intake_motor_back = new EasyMotor(hardwareMap, "intake_motor_back");
        intake_motors = new EasyMotorGroup(intake_motor_front, intake_motor_back)
                .setEncoderStrategy(EasyMotorGroup.EncoderStrategy.LEADER_ONLY)
                .brakeMode()
                .setLeaderMotor(0);
/*
        color_sensor_left = new EasyRevColorSensorV3(hardwareMap, "color_sensor_left", DistanceUnit.CM);

        led_back = new EasyServo(hardwareMap, "led_back")
                .addState("RED", 0.279)
                .addState("GREEN", 0.500);

        led_back.setState("RED");

 */
    }

    @Override
    public void loop() {
        currentTicks = getVelocity();
        //updateDetection();
    }

    private void updateDetection() {
        distance_left = getDistanceLeft();

        boolean detectedLeft =
                Double.isFinite(distance_left)
                        && distance_left < DETECTION_DISTANCE_CM;

        boolean detected = detectedLeft;
        long now = System.currentTimeMillis();

        if (detected) {
            if (detectedSince < 0) {
                detectedSince = now;
            }

            full = now - detectedSince >= confirm;
        } else {
            detectedSince = -1;
            full = false;
        }

        if (full) {
            led_back.setState("GREEN");
        } else {
            led_back.setState("RED");
        }
    }
}
