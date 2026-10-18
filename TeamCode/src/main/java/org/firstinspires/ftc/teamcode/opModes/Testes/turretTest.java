package org.firstinspires.ftc.teamcode.opModes.Testes;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

import dev.easyfamily.easystack.gamepad.EasyGamepad;
import dev.easyfamily.easystack.util.Point;

@TeleOp(name = "turretTest")
@Config
public class turretTest extends OpMode {

    Follower follower;
    Turret turret = new Turret();
    public static double position = 0;
    EasyGamepad driver;

    private final PoseFactory poseFactory = PoseFactory.degrees();
    private final Pose waitAprilTagAndShoot = poseFactory.of(0, 0, 0);


    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(waitAprilTagAndShoot);
        follower.update();

        driver = new EasyGamepad(gamepad1);
        turret.init(hardwareMap);
        turret.resetEncoder();
        Robot.setAlliance(Robot.Alliance.RED);
        telemetry.addData("Alliance", Robot.getTargetAlliance());
    }

    @Override
    public void loop() {
        telemetry.update();
        follower.update();
        turret.loop();
        driver.update();

        double x = follower.pose().x();
        double y = follower.pose().y();
        double heading = follower.pose().heading();

        double headingGraus = Math.toDegrees(follower.pose().heading());

        follower.manual(
                -driver.leftStickY(), -driver.leftStickX(), -driver.rightStickX()
        );

        Point celulaAtual = Robot.getClosestCell(x, y);

        turret.alignTurretWithOdometry(follower, celulaAtual);

        telemetry.addData("Current (deg)", turret.getCurrentDegrees());
        telemetry.addData("Current (ticks)", turret.currentTicks);
        telemetry.addData("Target Ticks", turret.targetTicks);

        telemetry.addData("X", x);
        telemetry.addData("Y", y);
        telemetry.addData("heading", heading);

        telemetry.addData("Target Cellx ", celulaAtual.x());
        telemetry.addData("Target Celly", celulaAtual.y());

    }
}
