package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.hiveVision.BallChaseFollower;
import org.firstinspires.ftc.teamcode.hiveVision.BallTracker;
import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.List;

import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Limelight implements EasySubsystem {
    // Limelight
    private static Limelight3A limelight;
    private static LLResult result;
    private static final String NAME = "limelight";
    private static final int PIPELINE = 5;
    private static final int POLL_RATE = 50;

    // Alliances
    public enum Alliance { RED, BLUE };
    public enum Hive { FRONT, BACK };
    private static Alliance alliance = Alliance.RED;
    private static Hive hive = Hive.FRONT;
    private static final int[] RED_1 = new int[] { 30, 31, 32, 33 };
    private static final int[] RED_2 = new int[] { 34, 35, 36, 37 };
    private static final int[] BLUE_1 = new int[] { 38, 39, 40, 41 };
    private static final int[] BLUE_2 = new int[] { 42, 43, 44, 45 };

    // Hive Vision
    BallTracker tracker;
    BallChaseFollower chaser;

    @Override
    public void init(HardwareMap hardwareMap)
    {
        // limelight
        limelight = hardwareMap.get(Limelight3A.class, NAME);
        limelight.pipelineSwitch(PIPELINE);
        limelight.setPollRateHz(POLL_RATE);
        limelight.start();

        // tracker
        tracker = new BallTracker(limelight);
        tracker.setAllowedClasses(BallTracker.CLASSES_ALL);
    }

    public void setChaser(Follower follower)
    {
        chaser = new BallChaseFollower(follower, tracker, null);
        chaser.setAllowedClasses(BallTracker.CLASSES_ALL);
        chaser.setMaxPickups(4);
        chaser.setTimeBudgetSec(5);
    }

    public void setAlliance(Alliance side, Hive tip)
    {
        alliance = side;
        hive = tip;
    }

    public void chasing() { chaser.update(); }
    public void chaserStart() { chaser.start(); }
    public boolean chaserIsDone() { return chaser.isDone(); }

    public void setPipeline(int pipeline) { limelight.pipelineSwitch(pipeline); }
    public boolean seeingElement() { return result != null && result.isValid(); }
    public boolean seeingAprilTag() { return result != null && !result.getFiducialResults().isEmpty(); }

    public List<LLResultTypes.FiducialResult> getSelectedTags()
    {
       if (!seeingAprilTag()) return null;

       List<LLResultTypes.FiducialResult> selected = new ArrayList<>();
       int[] ids;

       if (alliance == Alliance.RED)
       {
           ids = hive == Hive.FRONT ? RED_1 : RED_2;
       }
       else
       {
           ids = hive == Hive.FRONT ? BLUE_1 : BLUE_2;
       }

       for (LLResultTypes.FiducialResult tag : result.getFiducialResults())
       {
           for (int id : ids)
           {
               if (tag.getFiducialId() == id)
               {
                   selected.add(tag);
                   break;
               }
           }
       }

       return selected;
    }

    public double getHiveAngle()
    {
        List<LLResultTypes.FiducialResult> tags = getSelectedTags();
        if (tags.isEmpty()) return Double.NaN;

        double sinSum = 0;
        double cosSum = 0;

        for (LLResultTypes.FiducialResult tag : tags)
        {
            if (tag.getTargetPoseCameraSpace() == null) continue;

            double angle = tag.getTargetPoseCameraSpace().getOrientation().getPitch(AngleUnit.RADIANS);
            sinSum += Math.sin(angle);
            cosSum += Math.cos(angle);
        }

        return  Math.toDegrees(Math.atan2(sinSum, cosSum));
    }

    @Override
    public void loop()
    {
        result = limelight.getLatestResult();
    }

    // Comandos
    public Command chaserCommand()
    {
        return Command.build()
                .setStart(this::chaserStart)
                .setExecute(this::chasing)
                .setDone(this::chaserIsDone);
    }
}
