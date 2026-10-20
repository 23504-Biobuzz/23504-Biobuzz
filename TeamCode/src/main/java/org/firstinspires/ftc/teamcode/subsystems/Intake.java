package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import dev.easyfamily.easystack.control.PIDFCoefficients;
import dev.easyfamily.easystack.feedforward.FFController;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorGroup;
import dev.easyfamily.easystack.hardware.Sensors.EasyRevColorSensorV3;
import dev.easyfamily.easystack.hardware.Servo.EasyServo;
import dev.easyfamily.easystack.subsytem.EasySubsystem;

@Config
public class Intake implements EasySubsystem {

    public Telemetry telemetry;
    public double currentTicks;

    public double mediaDistancia;
    public EasyMotor intake_motor_front;
    public EasyMotor intake_motor_side;
    public EasyMotorGroup intake_motors;
    Led led = new Led();

    public EasyServo trava;

    public EasyRevColorSensorV3 color_sensor_left;
    public EasyRevColorSensorV3 color_sensor_right;
    public double distance_left;
    public double distance_right;
    public boolean full = false;
    public boolean detected = false;
    private long detectedSince = -1;
    private final long confirm = 175;
    private static final double DETECTION_DISTANCE_CM = 2.67;

    // PID
    public static double KP = 0.01;
    public static double KV = 11;
    public static double KS = 0.11;

    public static double target = 3000;

    public static double position = 0;



    public void onIntake() {
        intake_motors.setRPM(target);
    }

    public void offIntake() {
        intake_motors.setPower(0);
    }

    public void lockFeeder() {
        trava.setState("Closed");
    }

    public void openFeeder() {
        trava.setState("Open");
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

    private void configurePIDF() {
        // Mover pra o init quando terminar de calibrar
        intake_motors.setVelocityPIDF(new PIDFCoefficients(KP, 0, 0, 0));
        intake_motors.setVelocityFF(new FFController(KS, KV));

    }

    @Override
    public void init(HardwareMap hardwareMap) {
        intake_motor_front = new EasyMotor(hardwareMap, "intake_motor_front");
        intake_motor_side = new EasyMotor(hardwareMap, "intake_motor_side").reversed();
        intake_motors = new EasyMotorGroup(intake_motor_front, intake_motor_side)
                .setEncoderStrategy(EasyMotorGroup.EncoderStrategy.LEADER_ONLY)
                .brakeMode()
                .setLeaderMotor(0);

        color_sensor_left = new EasyRevColorSensorV3(hardwareMap, "color_sensor_left", DistanceUnit.CM);
        color_sensor_right = new EasyRevColorSensorV3(hardwareMap, "color_sensor_right", DistanceUnit.CM);

        trava = new EasyServo(hardwareMap, "feeder");

        led.init(hardwareMap);
    }

    @Override
    public void loop() {
        currentTicks = getVelocity();
        trava.setPosition(position);
        configurePIDF();
        updateDetection();
    }

    public void getTelemetry(Telemetry telemetry){
        telemetry.addData("Velocity", currentTicks);
        telemetry.addData("Detected?", detected);
        telemetry.addData("Média", mediaDistancia);
    }



    /**
     * Metodo responsável por detectar se os sensores estão detectando elemento.
     * Se sim, acende o LED verde, senão, acende o LED Vermelho
     */
    private void updateDetection() {
        distance_left = getDistanceLeft();
        distance_right = getDistanceRight();

        mediaDistancia = (getDistanceLeft() + getDistanceRight()) / 2;

        detected = mediaDistancia < DETECTION_DISTANCE_CM;

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
            led.setGreen();
        } else {
            led.setRed();
        }


    }
}