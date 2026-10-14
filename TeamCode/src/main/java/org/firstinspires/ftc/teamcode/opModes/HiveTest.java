package org.firstinspires.ftc.teamcode.opModes;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hiveVision.BallChaseFollower;
import org.firstinspires.ftc.teamcode.hiveVision.BallTracker;
import org.firstinspires.ftc.teamcode.pedro.Constants;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@TeleOp(group = "Testes", name = "HiveTest")
public class HiveTest extends OpMode {

    // Definindo
    Limelight3A limelight;
    BallTracker tracker;
    BallChaseFollower chaser;
    Follower follower;
    EasyGamepad driver;

    private final PoseFactory p = PoseFactory.degrees();
    private final Pose start = p.of(0, 0, 0);
    boolean chasing = false;

    @Override
    public void init()
    {
        // Pedro
        follower = Constants.create (hardwareMap);
        // Limelight
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(5);
        limelight.setPollRateHz(50);
        limelight.start();
        // Tracker
        tracker = new BallTracker(limelight);
        tracker.setAllowedClasses(BallTracker.CLASSES_ALL);
        // Chaser
        chaser = new BallChaseFollower(follower, tracker, null);
        chaser.setAllowedClasses(BallTracker.CLASSES_ALL);
        chaser.setMaxPickups(3);
        chaser.setTimeBudgetSec(20);
        // Gamepad
        driver = new EasyGamepad(gamepad1);
    }

    @Override
    public void loop()
    {
        follower.update();
        //tracker.update();

        if (driver.aPressed())
        {
            chasing = true;
            chaser.start();
        }

        if (chasing)
        {
            chaser.update();
            chaser.addTelemetry(telemetry);
            if (chaser.isDone())
            {
                chasing = false;
                telemetry.addLine("Acabou");
            }
        }
        else
        {
            double forward = -gamepad1.left_stick_y;
            double strafe = -gamepad1.left_stick_x;
            double turn = -gamepad1.right_stick_x;
            follower.manual(forward, strafe, turn);
        }
    }
}
