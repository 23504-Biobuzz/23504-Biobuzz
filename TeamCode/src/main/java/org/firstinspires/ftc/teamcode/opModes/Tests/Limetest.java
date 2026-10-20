package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Limelight;

@TeleOp(name = "LimeTest")
public class Limetest extends OpMode {
    Limelight limelight = new Limelight();

    @Override
    public void init()
    {
        limelight.init(hardwareMap);
        limelight.setPipeline(0);
        limelight.setAlliance(Limelight.Alliance.RED, Limelight.Hive.FRONT);
    }

    @Override
    public void loop()
    {
        limelight.loop();
        telemetry.addData("Angulo", limelight.getHiveAngle());
        telemetry.addData("TagsSelecionadas", limelight.getSelectedTags());
    }
}
