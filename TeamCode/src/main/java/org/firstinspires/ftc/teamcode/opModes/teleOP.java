package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@TeleOp(name = "IntakeTest")
public class teleOP extends OpMode {

    Robot robot = new Robot();
    Intake intake = new Intake();
    Shooter shooter = new Shooter();

    double target = 0;
    EasyGamepad driver;

    @Override
    public void init() {
        intake.init(hardwareMap);
        shooter.init(hardwareMap);
        driver = new EasyGamepad(gamepad1);
    }

    @Override
    public void loop() {
        intake.loop();
        shooter.loop();
        driver.update();

        if (driver.aToggle()) {
            intake.onIntake();
            telemetry.addData("Intake", "Ligado");
        } else {
            intake.offIntake();
            telemetry.addData("Intake", "Desligado");
        }

        if (driver.bToggle()) {
            shooter.onShooter();
        } else {
            shooter.offShooter();
        }

        if (driver.y()) {target += 5; shooter.setTarget(target);}
        if (driver.x()) {target -= 5; shooter.setTarget(target);}

        shooter.loop();

        telemetry.addData("Intake - currentTicks", intake.currentTicks);
        telemetry.addData("Target RPM", Shooter.target);
        telemetry.addData("Current RPM", shooter.currentRPM);
        telemetry.addData("Error RPM", shooter.errorRPM);

        telemetry.update();
    }
}
