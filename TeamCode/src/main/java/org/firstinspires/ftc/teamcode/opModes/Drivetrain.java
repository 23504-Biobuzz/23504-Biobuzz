package org.firstinspires.ftc.teamcode.opModes;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import dev.easyfamily.easystack.hardware.Motors.EasyMotor;
import dev.easyfamily.easystack.hardware.Motors.EasyMotorGroup;

public class Drivetrain extends OpMode {
    Follower follower;
    EasyMotor intake1;
    EasyMotor intake2;
    EasyMotorGroup intake;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        intake1 = new EasyMotor(hardwareMap, "intake1");
        intake2 = new EasyMotor(hardwareMap, "intake2");
        intake = new EasyMotorGroup(intake1, intake2).setLeaderMotor(0);
    }

    @Override
    public void loop() {
        double forward;
        double strafe;
        double rotate;
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        follower.manual(forward, strafe, rotate);
        follower.update();

        if (gamepad1.a){
            intake.setPower(1);
        }else if (gamepad1.b){
            intake.setPower(0);
        }
    }
}
