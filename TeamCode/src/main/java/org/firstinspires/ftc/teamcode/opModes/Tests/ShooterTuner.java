package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Shooter;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@Config
@TeleOp(name = "ShooterTuner", group = "Tuner")
public class ShooterTuner extends OpMode {


    // Variaveis
    public static double offset = 0;
    public static double targetRpm = 0;

    public static double hoodPose = 0;




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
        shooter.ServoDoHood().setPosition(hoodPose);


        if (gamepad.aToggle()){
            shooter.unlockFeeder();
        } else {
            shooter.lockFeeder();
        }





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
            shooter.shootIt(targetRpm, offset);
        } else {
            shooter.offShooter();
        }

        if (ActiveIntake){
            //Liga Intake
        } else {
            //Desliga Intake
        }




        telemetry.addData("Target Rpm: ", targetRpm);
        telemetry.addData("Actual Rpm: ", shooter.getActualRPMDebug());
        telemetry.addData("Error: ", targetRpm - shooter.getActualRPMDebug() );
        telemetry.addData("is Locked: ", shooter.islocked());
        telemetry.update();
    }
}
