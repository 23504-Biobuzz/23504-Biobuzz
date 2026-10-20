package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Limelight;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@TeleOp(name = "ManualTracking")
public class ManualTracking extends OpMode {

    // Instâncias
    private static Follower follower;
    private static final  Limelight limelight = new Limelight();
    private static EasyGamepad driver;

    // PID
    private static final double KP = 0.0025;
    private static final double DEADZONE = 0.5;
    private static final double MAX_TURN = 0.40;
    private static double turn = 0;
    private static boolean active = false;


    @Override
    public void init()
    {
        limelight.init(hardwareMap);
        limelight.setPipeline(5);
        driver = new EasyGamepad(gamepad1);
        follower = Constants.create(hardwareMap);
    }

    @Override
    public void loop()
    {
        limelight.loop();
        double tx = limelight.nearestTx();

        active = driver.aToggle();

        telemetry.addData("Alvo?", Double.isFinite(tx));
        telemetry.addData("TX", tx);
        telemetry.addData("TA", limelight.nearestTa());
        telemetry.addData("Rotação", turn);
        telemetry.update();

        if (Math.abs(tx) > DEADZONE && active)
        {
            turn = KP * tx;
            turn = Math.max(-MAX_TURN, Math.min(MAX_TURN, turn));
            follower.manual(0.4, 0, turn);
        }
        else { follower.manual(0, 0, 0); }

        follower.update();
    }
}
