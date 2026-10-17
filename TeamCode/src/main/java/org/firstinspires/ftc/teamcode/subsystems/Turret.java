package org.firstinspires.ftc.teamcode.subsystems;

import static dev.easyfamily.easystack.util.MathFunctions.normalizeAngle;

import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.control.PIDCoefficients;
import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.Point;

public class Turret implements EasySubsystem {

    public static double KP = 0;
    public static double KD = 0;
    public static double KI = 0;

    private EasyMotor turret_motor;

    public double currentTicks, targetTicks;

    public static double GEAR_RATIO = 106.0 / 37.0;
    private static final double ENCODER_CPR = 8192;
    private static final double TICKS_PER_TURRET_REV = ENCODER_CPR * GEAR_RATIO;

    private int degreesToTicks(double degrees) {
        return (int) Math.round((degrees / 360.0) * TICKS_PER_TURRET_REV);
    }

    private double ticksToDegrees(int ticks) {
        return (ticks / TICKS_PER_TURRET_REV) * 360.0;
    }


    public void alignTurretWithOdometry(double x, double y, double heading, boolean blue) {
        double headingDeg = Math.toDegrees(heading);

        Point celulaAtual = Robot.autoAim(x, y);
        double goalX = celulaAtual.x();
        double goalY = celulaAtual.y();
        double angleToGoal = Math.toDegrees(Math.atan2(goalY - y, goalX - x));
        double turretAngle = normalizeAngle(headingDeg - angleToGoal);

        targetTicks = degreesToTicks(turretAngle);
        turret_motor.moveToPosition(targetTicks);

    }

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