package org.firstinspires.ftc.teamcode.opModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@TeleOp(name = "IntakeTest")
public class teleOP extends OpMode {

    private Follower follower;
    Robot robot = new Robot();
   // Intake intake = new Intake();
   //  Shooter shooter = new Shooter();
    EasyGamepad driver;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        //shooter.init(hardwareMap);
        driver = new EasyGamepad(gamepad1);

    }

    @Override
    public void loop() {
        driver.update();
        follower.manual(
                driver.leftStickY(), driver.leftStickX(), driver.rightStickX()
        );
/*
        if (driver.bToggle()) {
            shooter.onShooter();
        } else {
            shooter.offShooter();
        }

        shooter.loop();



        telemetry.addData("Intake - currentTicks", intake.currentTicks);
        telemetry.addData("Target RPM", Shooter.target);
        telemetry.addData("Current RPM", shooter.currentRPM);
        telemetry.addData("Error RPM", shooter.errorRPM);

 */
        follower.update();
        telemetry.update();
    }
}
