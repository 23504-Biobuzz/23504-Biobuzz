package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.hiveVision.BallHunt;
import org.firstinspires.ftc.teamcode.pedro.Constants;

@Autonomous(name = "Ball Hunt Test")
public class HuntAutoTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        Follower follower;
        Limelight3A limelight;
        BallHunt hunt;

        follower = Constants.create(hardwareMap);
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(5);
        limelight.start();

        telemetry.addLine("Pronto");
        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        int picked = new BallHunt(follower, limelight, null).yellows().collect(4).within(20).go(this);

        telemetry.addData("Bolas coletadas", picked);
        telemetry.update();
    }
}