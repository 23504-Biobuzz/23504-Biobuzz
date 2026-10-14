package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.hiveVision.BallChaseFollower;
import org.firstinspires.ftc.teamcode.hiveVision.BallTracker;

import java.util.ArrayList;
import java.util.List;

import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class Limelight implements EasySubsystem {
    // Limelight
    public static Limelight3A limelight;
    public static LLResult result;
    public static String name = "limelight";
    public static int pipeline = 5;
    public static int pollRate = 50;

    // Hive Vision
    BallTracker tracker;
    BallChaseFollower chaser;

    @Override
    public void init(HardwareMap hardwareMap)
    {
        // limelight
        limelight = hardwareMap.get(Limelight3A.class, name);
        limelight.pipelineSwitch(pipeline);
        limelight.setPollRateHz(pollRate);
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
        chaser.setTimeBudgetSec(20);
    }

    public void setPipeline(int pipeline) { limelight.pipelineSwitch(pipeline); }

    public boolean seeingElement() { return result != null && result.isValid(); }
    public boolean seeingAprilTag() { return result != null && !result.getFiducialResults().isEmpty(); }

    public List<LLResultTypes.FiducialResult> aprilTags()
    {
        if (!seeingAprilTag()) return null;
        List<LLResultTypes.FiducialResult> aprilTags = new ArrayList<>();

        aprilTags.addAll(result.getFiducialResults());

        return aprilTags;
    }
}
