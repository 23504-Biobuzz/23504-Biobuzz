package org.firstinspires.ftc.teamcode.opModes.Tests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Limelight;

@Config
@TeleOp(name = "LimeTest")
public class Limetest extends OpMode {
    Limelight limelight = new Limelight();

    private ElapsedTime timer = new ElapsedTime();
    private double nowAngle = 0;
    private double lastAngle = 0;
    private boolean fall = true;
    private boolean fallTeste = false;
    private double treshold = 0;

    @Override
    public void init()
    {
        limelight.init(hardwareMap);
        limelight.setPipeline(0);
        limelight.setAlliance(Limelight.Alliance.RED, Limelight.Hive.FRONT);
        timer.reset();
    }

    @Override
    public void loop()
    {
        limelight.loop();

        if (timer.milliseconds() >= 1000)
        {
            nowAngle = limelight.getHiveAngle();
            if (nowAngle - lastAngle >= 5) fall = true;
            else { fall = false; }
            lastAngle = nowAngle;
            timer.reset();
        }

        if (limelight.getHiveAngle() <= treshold) fallTeste = true;
        else { fallTeste = false; }

        telemetry.addData("FallTeste", fallTeste);
        telemetry.addData("Fall", fall);
        telemetry.addData("Angulo", limelight.getHiveAngle());
        telemetry.addData("TagsSelecionadas", limelight.getSelectedTags());
    }
}
