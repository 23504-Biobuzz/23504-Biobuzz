package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {
    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("fl");
        c.frontRightName.set("fr");
        c.backLeftName.set("rl");
        c.backRightName.set("rr");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });


    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(6.0);
        c.yPodOffset.set(-4.0);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.CM);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.30358441466998165);
                Controller secondaryTranslationalForward = Controller.proportional(0.1121663292955988);
                Controller primaryTranslationalLateral = Controller.proportional(0.4132018541746231);
                Controller secondaryTranslationalLateral = Controller.proportional(0.15266704416063548);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.012074913132273036));
                c.brake.set(Controller.proportionalFeedforward(0.010263676162432081));

                c.headingFeedback.set(Controller.proportional(4.370238823492224));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04220645351423337, 0.007569397484533416));

                c.linearBrakeCoefficients.set(Matrix.diag(0.10227804763444862, 0.04608156502929025));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0012039552851094686, 0.0024119851504775204));

                c.maxAchievableForwardVelocity.set(83.61854190613539);
                c.maxAchievableStrafeVelocity.set(69.22638656131404);
                c.naturalForwardDeceleration.set(61.14958879982429);
                c.naturalStrafeDeceleration.set(84.3513638992874);
            }
    );


}
