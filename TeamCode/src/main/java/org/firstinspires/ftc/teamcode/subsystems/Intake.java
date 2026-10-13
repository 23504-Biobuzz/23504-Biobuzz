package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorGroup;
import dev.easyfamily.easystack.hardware.Sensors.EasyRevColorSensorV3;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Intake implements EasySubsystem {

    public Telemetry telemetry;
    public double currentTicks;
    public EasyMotor intake_motor_left;
    public EasyMotor intake_motor_right;
    public EasyMotorGroup intake_motors;
    public EasyServo servo_flower;
    public Led led;
    public EasyRevColorSensorV3 color_sensor_left;
    public EasyRevColorSensorV3 color_sensor_right;
    public double distance_left;
    public double distance_right;
    public boolean full = false;
    public boolean detected = false;
    private long detectedSince = -1;
    private final long confirm = 300;
    private static final double DETECTION_DISTANCE_CM = 2.75;

    public void onIntake() {
        intake_motors.setPower(1);
    }

    public void offIntake() {
        intake_motors.setPower(0);
    }

    public void upServo(){
        servo_flower.setPosition(0.850);
    }

    public void downServo(){
        servo_flower.setPosition(0);
    }

    public double getVelocity() {
        return intake_motors.getVelocity();
    }

    public double getDistanceLeft() {
        return color_sensor_left.getDistance(DistanceUnit.CM);
    }

    public double getDistanceRight() {
        return color_sensor_right.getDistance(DistanceUnit.CM);
    }

    @Override
    public void init(HardwareMap hardwareMap) {
        intake_motor_left = new EasyMotor(hardwareMap, "intake_motor_front").reversed();
        intake_motor_right = new EasyMotor(hardwareMap, "intake_motor_back");
        intake_motors = new EasyMotorGroup(intake_motor_left, intake_motor_right)
                .setEncoderStrategy(EasyMotorGroup.EncoderStrategy.LEADER_ONLY)
                .brakeMode()
                .setLeaderMotor(0);
        servo_flower = new EasyServo(hardwareMap, "intake_servo").reverse();

        color_sensor_left = new EasyRevColorSensorV3(hardwareMap, "color_sensor_left", DistanceUnit.CM);
        color_sensor_right = new EasyRevColorSensorV3(hardwareMap, "color_sensor_right", DistanceUnit.CM);

        led = new Led();
    }

    @Override
    public void loop() {
        currentTicks = getVelocity();
        updateDetection();

        // para testar, depois retirar.
        getTelemetry();
    }

    public void getTelemetry(){
        telemetry.addData("Velocity", currentTicks);
        telemetry.addData("Servo Flower", servo_flower.getPosition());
        telemetry.addData("Detected?", detected);
        telemetry.addData("Sensor Left Distance", color_sensor_left.getDistance(DistanceUnit.CM));
        telemetry.addData("Sensor Right Distance", color_sensor_right.getDistance(DistanceUnit.CM));
    }

    /**
     * Metodo responsável por detectar se os sensores estão detectando elemento.
     * Se sim, acende o LED verde, senão, acende o LED Vermelho
     */
    private void updateDetection() {
        distance_left = getDistanceLeft();
        distance_right = getDistanceRight();

        detected = distance_left < DETECTION_DISTANCE_CM || distance_right < DETECTION_DISTANCE_CM;
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
            led.green.execute();
        } else {
            led.red.execute();
        }
    }
}