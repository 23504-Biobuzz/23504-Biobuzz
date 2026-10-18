package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.lazy;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;

@Autonomous(name = "HiveAutoTest", group = "Tests")
public class HiveAutoTest extends LinearOpMode {

    private Follower follower;
    private Limelight limelight = new Limelight();

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(8.642, 9.7221, 0);
    private final Pose path1 = poseFactory.of(117.9959, 32.5558, 180);
    private final Pose point2 = poseFactory.of(10.3915, 95.7039, 0);


    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                limelight.chaserCommand(),
                lazy(() -> {
                    Pose ballPoint = follower.pose();
                    Path ballPath = Paths.line(ballPoint, point2).linear(ballPoint, point2);
                    return follow(follower, ballPath);
                })
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();
        limelight.init(hardwareMap);
        limelight.setChaser(follower);

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            limelight.loop();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }
}
