package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.shootSystems.Shooter;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@TeleOp(name = "ShooterTuner", group = "Tuner")
public class ShooterTuner extends OpMode {


    Shooter shooter = new Shooter();

    EasyGamepad gamepad = new EasyGamepad(gamepad1);

    public static boolean ActiveShooter = false;
    public static boolean ActiveIntake = false;


    @Override
    public void init() {
        shooter.init(hardwareMap);

    }


    @Override
    public void loop() {
        shooter.loop(); // atualizar o PID


        if (gamepad.dpadRight()){
            ActiveIntake = true;
        }
        if (gamepad.dpadLeft()){
            ActiveIntake = false;
        }


        if (gamepad.dpadUp()){
            ActiveShooter = true;
        }
        if(gamepad.dpadDown()){
            ActiveShooter = false;
        }


        if (ActiveShooter) {
            shooter.shootIt();
        } else {
            shooter.offShooter();
        }

        if (ActiveIntake){
            //Liga Intake
        } else {
            //Desliga Intake
        }




        telemetry.addData("Target Rpm", shooter.getTargetRpmDebug());
        telemetry.addData("Actual Rpm", shooter.getActualRPMDebug());
        telemetry.addData("Error", (shooter.getTargetRpmDebug() - shooter.getActualRPMDebug() ));
        telemetry.update();
    }
}
