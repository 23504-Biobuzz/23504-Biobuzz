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

import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "GoodSide-" +
        "", group = "JUSTPATH")
public class GoodSideJustPath extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(28.6, 6.2, 180);
    private final Pose collectGardenStart = poseFactory.of(28.6, 6.2, 180);
    private final Pose collectGarden = poseFactory.of(10, 6.2, 180);
    private final Pose shootPosition = poseFactory.of(47, 22.5, 80);
    private final Pose goToFlowerStart = poseFactory.of(47, 22.5, 122);
    private final Pose goToFlower = poseFactory.of(23, 46.5, 180);
    private final Pose collectFlower = poseFactory.of(16, 46.5, 180);
    private final Pose shootAgain = poseFactory.of(30, 22.5, 0);
    private final Pose shootWhileCollecting = poseFactory.of(67, 22.5, 0);
    private final Pose goToPark = poseFactory.of(10, 94, 270);
    private final Pose goToParkControl1 = poseFactory.of(10, 23, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
            follow(follower, collectGarden()),
            follow(follower, shootPosition()),
            follow(follower, goToFlower()),
            follow(follower, collectFlower()),
            follow(follower, shootAgain()),
            follow(follower, shootWhileCollecting()),
            follow(follower, goToPark())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
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

    public Path collectGarden() {
        return line(collectGardenStart, collectGarden).linear(collectGardenStart, collectGarden);
    }

    public Path shootPosition() {
        return line(collectGarden, shootPosition).linear(collectGarden, shootPosition);
    }

    public Path goToFlower() {
        return line(goToFlowerStart, goToFlower).linear(goToFlowerStart, goToFlower);
    }

    public Path collectFlower() {
        return line(goToFlower, collectFlower).linear(goToFlower, collectFlower);
    }

    public Path shootAgain() {
        return line(collectFlower, shootAgain).linear(collectFlower, shootAgain);
    }

    public Path shootWhileCollecting() {
        return line(shootAgain, shootWhileCollecting).linear(shootAgain, shootWhileCollecting);
    }

    public Path goToPark() {
        return curve(shootWhileCollecting, goToParkControl1, goToPark).linear(shootWhileCollecting, goToPark);
    }
}
