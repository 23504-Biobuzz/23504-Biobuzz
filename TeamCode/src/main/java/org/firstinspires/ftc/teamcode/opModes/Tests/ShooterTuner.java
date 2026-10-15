package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.shootSystems.Feeder;
import org.firstinspires.ftc.teamcode.subsystems.shootSystems.Hood;
import org.firstinspires.ftc.teamcode.subsystems.shootSystems.Shooter;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@Config
@TeleOp(name = "ShooterTuner", group = "Tuner")
public class ShooterTuner extends OpMode {

    Shooter shooter = new Shooter();

    Hood hood = new Hood();

    Feeder feeder = new Feeder();

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



        if (gamepad.aToggle()){
            feeder.unlock();
        } else {
            feeder.lock();
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
            shooter.shootIt();
        } else {
            shooter.offShooter();
        }


        if (ActiveIntake){
            //Liga Intake
        } else {
            //Desliga Intake
        }




        telemetry.addData("Target Rpm: ", shooter.getTargetedRpm());
        telemetry.addData("Actual Rpm: ", shooter.getActualRPM());
        telemetry.addData("Error: ", shooter.getRpmError() );
        telemetry.addData("is Locked: ", feeder.isLocked());
        telemetry.update();
    }
}
