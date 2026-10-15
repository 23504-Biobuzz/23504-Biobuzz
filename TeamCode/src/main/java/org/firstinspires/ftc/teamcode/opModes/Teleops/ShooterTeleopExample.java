package org.firstinspires.ftc.teamcode.opModes.Teleops;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.shootSystems.ShootMechanism;

import dev.easyfamily.easystack.gamepad.EasyGamepad;

@TeleOp
public class ShooterTeleopExample extends OpMode {


    double distance = 30;
    double offset = 10;


    ShootMechanism shooterGroup = new ShootMechanism();

    EasyGamepad pad = new EasyGamepad(gamepad1);

    @Override
    public void init() {
        shooterGroup.init(hardwareMap);


    }


    @Override
    public void loop() {

        // Atualiza a distancia e offset

        shooterGroup.loop(30, 10);


        if (pad.aToggle())
        {
            shooterGroup.feederUnlock();
        }
        else
        {
            shooterGroup.feederLock();
        }


    }
}