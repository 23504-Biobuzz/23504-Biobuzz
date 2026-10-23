package org.firstinspires.ftc.teamcode.opModes.DaniloJustPath;

import static com.pedropathing.api.Paths.*;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.CommandBuilder;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;

import static com.pedropathing.ivy.commands.Commands.instant;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.parallel;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.paths.interpolator.Interpolator;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Flower;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Robot;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;
import org.firstinspires.ftc.teamcode.subsystems.Turret;

import dev.easyfamily.easystack.Commands.ParallelCommandGroup;
import dev.easyfamily.easystack.util.Point;

@Autonomous(name = "SoloAutoPathJUSTPATH", group = "JUSTPATH")
public class SoloAutoPathJUSTPATH extends LinearOpMode {

    private Follower follower;

    private Flower flower = new Flower();

    Shooter shooter = new Shooter();

    Turret turret = new Turret();

    private Intake intake = new Intake();

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(52, 10, 90);
    private final Pose shootInitialBalls = poseFactory.of(52, 26, 90);



    private final Pose positionToCollectFlower = poseFactory.of(25, 50, 180);
    private final Pose collectFlower = poseFactory.of(12, 49.5, 180);
    private final Pose backStepFirstFlower = poseFactory.of(25, 49.5, 180);
    private final Pose shootInOtherSide = poseFactory.of(40, 118, 90);
    private final Pose shootInOtherSideControl1 = poseFactory.of(40, 115, 90);
    private final Pose shootInOtherSideSegment2Start = poseFactory.of(40, 118, 83.8129);
    private final Pose shootInOtherSideSegment2End = poseFactory.of(40, 118, 90);
    private final Pose collectOtherSideFlowerStart = poseFactory.of(40, 118, 90);
    private final Pose collectOtherSideFlower = poseFactory.of(40, 125, 90);
    private final Pose backStepSecondFlower = poseFactory.of(40, 118, 90);
    private final Pose collectGarden = poseFactory.of(7.5, 10, -90);
    private final Pose collectGardenControl1 = poseFactory.of(20, 114, 0);
    private final Pose collectGardenControl2 = poseFactory.of(34, 24, 0);
    private final Pose collectGardenSegment2Start = poseFactory.of(7.5, 10, -103.7828);
    private final Pose collectGardenSegment2End = poseFactory.of(7.5, 10, 270);
    private final Pose shootGardenBallsStart = poseFactory.of(7.5, 10, 270);
    private final Pose shootGardenBalls = poseFactory.of(25.2, 22.5, 0);
    private final Pose elementTracking = poseFactory.of(60.5, 23, 52.7891);
    private final Pose elementTrackingControl1 = poseFactory.of(46.2489, 4, 0);
    private final Pose goToOtherSideAndCollectBallsTrackingStart = poseFactory.of(60.5, 23, 90);
    private final Pose goToOtherSideAndCollectBallsTracking = poseFactory.of(61, 120, 90);
    private final Pose parkingZone = poseFactory.of(17, 88, -90);
    private final Pose parkingZoneSegment2Start = poseFactory.of(17, 88, -143.9726);
    private final Pose parkingZoneSegment2End = poseFactory.of(12, 88, 270);


    CommandBuilder shootBalls(){
        return parallel(
                instant(() -> {intake.onIntake();}),
                instant(() -> {intake.openFeeder();})
        );
    }

    CommandBuilder stopShootingBalls(){
        return parallel(
                instant(() -> {intake.offIntake();}),
                instant(() -> {intake.lockFeeder();})
        );
    }





        CommandBuilder activeFlower(){
        return parallel(
                instant(() -> {flower.collectFlower();}),
                instant(() -> {intake.onIntake();})
        );
    }


        CommandBuilder offFlower(){
        return parallel(
                instant(() -> {flower.retractFlower();}),
                instant(() -> {intake.  offIntake();})
        );
    }

    CommandBuilder intakeCollect(){
        return parallel(
                instant(() -> {intake.onIntake();})
        );
    }

    CommandBuilder intakeStop(){
        return parallel(
                instant(() -> {intake.offIntake();})
        );
    }





    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                instant(() -> flower.retractFlower()),
            stopShootingBalls(),
            parallel(
                    follow(follower, shootMoreClose()),
                    waitMs(1750)
            ),


            shootBalls(),
            waitMs(500),
            stopShootingBalls(),



            follow(follower, positionToCollectFlower()),

            activeFlower(),
                waitMs(500),
            

            follow(follower, collectFlower()),
            waitMs(2000),
            follow(follower, backStepFirstFlower()),

                offFlower(),
waitMs(500),


            follow(follower, shootInOtherSide()),
            shootBalls(),
            waitMs(500),
            parallel(
                    stopShootingBalls(),
                    activeFlower()
            ),

            follow(follower, collectOtherSideFlower()),
            waitMs(500),
            follow(follower, backStepSecondFlower()),
            parallel(
                    offFlower(),
                    shootBalls(),
                    waitMs(2000)
            ),
            stopShootingBalls(),

            parallel(
                    follow(follower, collectGarden()),
                    intakeCollect()
            ),
            intakeStop(),

            follow(follower, shootGardenBalls()),

            shootBalls(),
            waitMs(500),
            stopShootingBalls(),

                intakeCollect(),
            follow(follower, elementTracking()),
            intakeStop(),

            follow(follower, goToOtherSideAndCollectBallsTracking())
        );

    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
        follower.update();

        flower.init(hardwareMap);
        intake.init(hardwareMap);
        turret.init(hardwareMap);
        turret.resetEncoder();



        shooter.init(hardwareMap);





        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            intake.loop();
            turret.loop();
            shooter.loop();


            Point goal = Robot.getClosestCell(follower.pose().x(), follower.pose().y());
            double distance = Robot.distanceToCell(follower.pose().x(), follower.pose().y());

            turret.alignTurretWithOdometry(follower, goal);

            shooter.setShooter(distance);


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


    public Path positionToCollectFlower() {
        return line(start, positionToCollectFlower).linear(start, positionToCollectFlower);
    }

    public Path shootMoreClose(){
        return line(start, shootInitialBalls).linear(start, shootInitialBalls);
    }

    public Path collectFlower() {
        return line(positionToCollectFlower, collectFlower)
                .linear(positionToCollectFlower, collectFlower)
                .with(Constants.foresightConfig.maxPathSpeed.at(0.7));
    }

    public Path backStepFirstFlower() {
        return line(collectFlower, backStepFirstFlower).linear(collectFlower, backStepFirstFlower);
    }

    public Path shootInOtherSide() {
        return curve(backStepFirstFlower, shootInOtherSideControl1, shootInOtherSide).heading(Interpolator.piecewise().until(0.4, Interpolator.tangent).until(1, Interpolator.linear(shootInOtherSideSegment2Start, shootInOtherSideSegment2End)));
    }

    public Path collectOtherSideFlower() {
        return line(collectOtherSideFlowerStart, collectOtherSideFlower).linear(collectOtherSideFlowerStart, collectOtherSideFlower);
    }

    public Path backStepSecondFlower() {
        return line(collectOtherSideFlower, backStepSecondFlower).linear(collectOtherSideFlower, backStepSecondFlower);
    }

    public Path collectGarden() {
        return curve(backStepSecondFlower, collectGardenControl1, collectGardenControl2, collectGarden).heading(Interpolator.piecewise().until(0.75, Interpolator.tangent).until(1, Interpolator.linear(collectGardenSegment2Start, collectGardenSegment2End)));
    }

    public Path shootGardenBalls() {
        return line(shootGardenBallsStart, shootGardenBalls).linear(shootGardenBallsStart, shootGardenBalls);
    }

    public Path elementTracking() {
        return curve(shootGardenBalls, elementTrackingControl1, elementTracking).tangent();
    }

    public Path goToOtherSideAndCollectBallsTracking() {
        return line(goToOtherSideAndCollectBallsTrackingStart, goToOtherSideAndCollectBallsTracking).linear(goToOtherSideAndCollectBallsTrackingStart, goToOtherSideAndCollectBallsTracking);
    }

    public Path parkingZone() {
        return line(goToOtherSideAndCollectBallsTracking, parkingZone).heading(Interpolator.piecewise().until(0.9, Interpolator.tangent).until(1, Interpolator.linear(parkingZoneSegment2Start, parkingZoneSegment2End)));
    }
}
