package org.firstinspires.ftc.teamcode.utils;

import com.pedropathing.math.Pose;

import org.firstinspires.ftc.teamcode.subsystems.Robot;

public class PoseSaver {

    // Valores
    private static double x = 0;
    private static double y = 0;
    private static double heading = 0;

    public static boolean hasPose = false;

    public static boolean hasAlliance = false;


    private static Robot.Alliance alliance = Robot.Alliance.RED; // RED PADRAO


    // SAVERS
    public static void savePose(Pose pose)
    {
        x = pose.x();
        y = pose.y();
        heading = pose.heading();

        hasPose = true;
    }

    public static void savePose(Pose pose, Robot.Alliance choosedAlliance)
    {
        x = pose.x();
        y = pose.y();
        heading = pose.heading();
        alliance = choosedAlliance;

        hasPose = true;
        hasAlliance = true;
    }






    // LOADERS

    public static Pose loadPose(){
        return new Pose(x, y, heading);
    }


    public static Robot.Alliance loadAlliance() {
        return alliance;
    }


    // RESET
    public static void reset(){
        x = 0;
        y = 0;
        heading = 0;
        alliance = Robot.Alliance.RED;
    }




    // Poses Validators
    public static boolean hasAlliance(){
        return hasAlliance;
    }

    public static boolean hasPose(){
        return hasPose;
    }




}
