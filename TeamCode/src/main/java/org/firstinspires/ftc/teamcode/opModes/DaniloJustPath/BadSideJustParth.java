package org.firstinspires.ftc.teamcode.opModes.DaniloJustPath;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.paths.interpolator.Interpolator;

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "BadSide", group = "JUSTPATH")
public class BadSideJustParth extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose waitAprilTagAndShoot = poseFactory.of(64.5, 132.4, 90);
    private final Pose afterWaitToShootGoToFlower = poseFactory.of(47, 118, 90);
    private final Pose afterWaitToShootGoToFlowerControl1 = poseFactory.of(70, 100, 0);
    private final Pose afterWaitToShootGoToFlowerControl2 = poseFactory.of(48.3511, 100, 0);
    private final Pose afterWaitToShootGoToFlowerSegment1Start = poseFactory.of(47, 118, 270);
    private final Pose afterWaitToShootGoToFlowerSegment1End = poseFactory.of(47, 118, 90);
    private final Pose afterWaitToShootGoToFlowerSegment2Start = poseFactory.of(47, 118, 90);
    private final Pose afterWaitToShootGoToFlowerSegment2End = poseFactory.of(47, 118, 90);
    private final Pose collectFlower = poseFactory.of(47, 125, 90);
    private final Pose backStep = poseFactory.of(47, 117.8, 90);
    private final Pose po = poseFactory.of(17.2892, 119.0482, -2.4057);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
            follow(follower, afterWaitToShootGoToFlower()),
            follow(follower, collectFlower()),
            follow(follower, backStep()),
            follow(follower, po())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(waitAprilTagAndShoot);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
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

    public Path afterWaitToShootGoToFlower() {
        return curve(waitAprilTagAndShoot, afterWaitToShootGoToFlowerControl1, afterWaitToShootGoToFlowerControl2, afterWaitToShootGoToFlower).heading(Interpolator.piecewise().until(0.4, Interpolator.linear(afterWaitToShootGoToFlowerSegment1Start, afterWaitToShootGoToFlowerSegment1End)).until(1, Interpolator.linear(afterWaitToShootGoToFlowerSegment2Start, afterWaitToShootGoToFlowerSegment2End)));
    }

    public Path collectFlower() {
        return line(afterWaitToShootGoToFlower, collectFlower).linear(afterWaitToShootGoToFlower, collectFlower);
    }

    public Path backStep() {
        return line(collectFlower, backStep).linear(collectFlower, backStep);
    }

    public Path po() {
        return line(backStep, po).reverseTangent();
    }
}
