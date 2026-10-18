package org.firstinspires.ftc.teamcode.subsystems;

import static dev.easyfamily.easystack.util.MathFunctions.normalizeAngle;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDCoefficients;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.Point;

@Config
public class Turret implements EasySubsystem {


    public double flightTime = 0.30;

    // Instancias
    private EasyMotor turret_motor;


    // PID
    public static double KP = 0.0022;
    public static double KD = 0.00002;
    public static double KI = 0;


    // Calculo de Ticks

    public double currentTicks, targetTicks;

    public static double GEAR_RATIO = 70.0 / 20.0; // Dentes das engrenagens
    private static final double ENCODER_CPR = 8192;
    private static final double TICKS_PER_TURRET_REV = ENCODER_CPR * GEAR_RATIO; // Ticks Totais


    /** METODOS de Convercao */
    private int degreesToTicks(double degrees) {
        return (int) Math.round((degrees / 360.0) * TICKS_PER_TURRET_REV);
    }

    private double ticksToDegrees(int ticks) {
        return (ticks / TICKS_PER_TURRET_REV) * 360.0;
    }

    public double getCurrentDegrees() {
        return ticksToDegrees((int) turret_motor.getPosition());
    }

    public void moveToPosition(double position) {
        turret_motor.moveToPosition(position);
    }

    private double normalizeAngleDeg(double angle) {
        angle = angle % 360;
        if (angle > 180) angle -= 360;
        if (angle < -180) angle += 360;
        return angle;
    }

    // Alinhamento de odometria
    public void alignTurretWithOdometryMotion(Follower follower, Point Goal) {
        double headingRadianos = follower.pose().heading();

        double x = follower.pose().x();
        double y = follower.pose().y();

        double headingDeg = Math.toDegrees(headingRadianos);

        double goalX = Goal.x();
        double goalY = Goal.y();

        double movingX = x - follower.velocity().vx * flightTime;
        double movingY = y - follower.velocity().vy * flightTime;


        double angleToGoal = Math.toDegrees(Math.atan2(goalY - movingY, goalX - movingX));
        double turretAngle = normalizeAngleDeg(headingDeg - angleToGoal); // Invertido a ordem.

        // Move turret
        targetTicks = degreesToTicks(turretAngle);
        turret_motor.moveToPosition(targetTicks);

    }

    // Alinhamento de odometria
    public void alignTurretWithOdometry(Follower follower, Point Goal) {
        double headingRadianos = follower.pose().heading();

        double x = follower.pose().x();
        double y = follower.pose().y();

        double headingDeg = Math.toDegrees(headingRadianos);

        double goalX = Goal.x();
        double goalY = Goal.y();

        double angleToGoal = Math.toDegrees(Math.atan2(goalY - y, goalX - x));
        double turretAngle = normalizeAngleDeg(headingDeg - angleToGoal); // Invertido a ordem.

        // Move turret
        targetTicks = degreesToTicks(turretAngle);
        turret_motor.moveToPosition(targetTicks);

    }


    public void resetEncoder() {
        turret_motor.stopAndResetEncoder();
    }

    /** Metodos do easyStack */
    @Override
    public void init(HardwareMap hardwareMap) {
        turret_motor = new EasyMotor(hardwareMap, "turret_motor");
    }

    @Override
    public void loop()   {
        currentTicks = turret_motor.getPosition();
        turret_motor.setPositionPID(new PIDCoefficients(KP, KI, KD));
    }
}