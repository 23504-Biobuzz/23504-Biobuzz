package org.firstinspires.ftc.teamcode.opModes.Testes;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Flower;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

import dev.easyfamily.easystack.gamepad.EasyGamepad;
import dev.easyfamily.easystack.util.Point;

@TeleOp(name = "teleOP")
public class teleOP extends OpMode {

    private Follower follower;
    EasyGamepad driver;

    Intake intake = new Intake();
    Shooter shooter = new Shooter();
    Turret turret = new Turret();
    Flower flower = new Flower();

    PoseFactory poseFactory = PoseFactory.degrees();

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(poseFactory.of(6.5, 7.5, 0));
        follower.update();

        driver = new EasyGamepad(gamepad1);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        flower.init(hardwareMap);
        turret.init(hardwareMap);

        Robot.setAlliance(Robot.Alliance.RED);
        telemetry.addData("Alliance", Robot.getTargetAlliance());

        turret.resetEncoder();

    }

    @Override
    public void loop() {
        follower.update();
        driver.update();

        double x = follower.pose().x();
        double y = follower.pose().y();
        double heading = follower.pose().heading();
        double distance = Robot.distanceToCell(x,y);


        double forward = -driver.leftStickY();

        double strafe = -driver.leftStickX();
        double turn = -driver.rightStickX();

        follower.manual(
                -driver.leftStickY(), -driver.leftStickX(), -driver.rightStickX()
        );

        if (driver.b()) {
            intake.eject();
        } else if (driver.leftTrigger() > 0.1 || driver.rightTrigger() > 0.1) {
            if (driver.rightTrigger() > 0.1) {
                intake.openFeeder();
            } else {
                intake.lockFeeder();
            }

            intake.onIntake();
        } else {
            intake.offIntake();
            intake.lockFeeder();
        }

        if (driver.leftBumperToggle()) {
            flower.collectFlower();
        } else {
            flower.retractFlower();
        }

        Point celulaAtual = Robot.getClosestCell(x, y);
        shooter.setShooter(distance);

        turret.alignTurretWithOdometry(follower, celulaAtual);

        telemetry.addData("Target RPM", Shooter.target);
        telemetry.addData("Current RPM", shooter.currentRPM);
        telemetry.addData("--------------", "---------------");
        telemetry.addData("Distancia até a Hive ",  distance); // Ver distancia até a hive
        telemetry.addData("X", x);
        telemetry.addData("Y", y);
        telemetry.addData("Heading", heading);
        telemetry.addData("velocity LUT", shooter.shooterVelocity.get(distance));

        turret.loop();
        shooter.loop();
        intake.loop();

        intake.getTelemetry(telemetry);
        telemetry.update();
    }
}
