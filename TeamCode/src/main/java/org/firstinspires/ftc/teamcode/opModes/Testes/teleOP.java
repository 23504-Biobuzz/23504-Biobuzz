package org.firstinspires.ftc.teamcode.opModes.Testes;

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

    Intake intake = new Intake();
    Shooter shooter = new Shooter();

    EasyGamepad driver;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        driver = new EasyGamepad(gamepad1);
        intake.init(hardwareMap);
        shooter.init(hardwareMap);

    }

    @Override
    public void loop() {
        driver.update();

        follower.manual(
                -driver.leftStickY(), -driver.leftStickX(), -driver.rightStickX()
        );
/*
        if (driver.leftTrigger() > 0.1 || driver.rightTrigger() > 0.1) {
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

 */

        if (driver.leftTrigger() > 0.1) {
            intake.onIntake();
        } else {
            intake.offIntake();
        }

        if (driver.bToggle()) {
            shooter.onShooter();
        } else {
            shooter.offShooter();
        }

        telemetry.addData("Target RPM", Shooter.target);
        telemetry.addData("Current RPM", shooter.currentRPM);
        telemetry.addData("Error RPM", shooter.errorRPM);

        shooter.loop();
        intake.loop();

        intake.getTelemetry(telemetry);
        follower.update();
        telemetry.update();
    }
}
