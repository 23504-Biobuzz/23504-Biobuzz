package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.subsytem.EasySubsystem;
import dev.easyfamily.easystack.util.Point;

public class Robot {

    public double distance;


    public enum Alliance {
        RED,
        BLUE

    }

    //Posição das celulas
    private static final Point RED_1 = new Point(48, 48);
    private static final Point RED_2 = new Point(48, 86);

    private static final Point BLUE_1 = new Point(74, 86);
    private static final Point BLUE_2 = new Point(74, 48);


    // Valores padrão

    private static Alliance alliance = Alliance.RED;

    private static Point targetCell = RED_1;


    // Metodo para setar a aliaça no inicio
    public static void setAlliance(Alliance newAlliance) {
        alliance = newAlliance;

        if (alliance == Alliance.RED) {
            targetCell = RED_1;
        } else {
            targetCell = BLUE_1;
        }
    }



    public static Point getClosestCell(double robotX, double robotY) {

        if (alliance == Alliance.RED) {

            double distanceToFirstCell = Math.hypot(robotX - RED_1.x(), robotY - RED_1.y());
            double distanceToSecondCell = Math.hypot(robotX - RED_2.x(), robotY - RED_2.y());

            //retorna a célula mais perto
            if (distanceToFirstCell <= distanceToSecondCell) {
                targetCell = RED_1;
            } else {
                targetCell = RED_2;
            }
        } else {

            double distanceToFirstCell = Math.hypot(robotX - BLUE_1.x(), robotY - BLUE_1.y());
            double distanceToSecondCell = Math.hypot(robotX - BLUE_2.x(), robotY - BLUE_2.y());

            // Retorna a célula mais perto

            if (distanceToFirstCell <= distanceToSecondCell) {
                targetCell = BLUE_1;
            } else {
                targetCell = BLUE_2;
            }
        }

        return targetCell;
    }


    public static double distanceToCell(double robotX, double robotY) {

        // Pega a célula que está mais perto
        Point cell = getClosestCell(robotX, robotY);

        // Pega a posição atual e a célula que ta mais perto e retorna a distância
        return Math.hypot(robotX - cell.x(), robotY - cell.y());

    }


    public static Alliance getTargetAlliance() {
        return alliance;
    }


}
