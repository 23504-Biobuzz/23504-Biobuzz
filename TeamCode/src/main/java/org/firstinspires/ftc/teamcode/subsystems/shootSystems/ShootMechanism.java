package org.firstinspires.ftc.teamcode.subsystems.shootSystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import dev.easyfamily.easystack.subsytem.EasySubsystem;

public class ShootMechanism implements EasySubsystem{

    Feeder feeder = new Feeder();
    Shooter shooter = new Shooter();

    Hood hood = new Hood();



    public void init(HardwareMap hardwareMap) {
        feeder.init(hardwareMap);
        shooter.init(hardwareMap);
        hood.init(hardwareMap);

    }



    public void loop(double distance, double offset) {
        onShootAndHood(distance, offset);
    }


    public void onShootAndHood(double distance, double offset){
        shooterOn(distance, offset);
        angulateHood(distance);
    }



    public void shooterOn(double distance, double offset){
        shooter.setRpmByDistance(distance);
        shooter.setOffsetRpm(offset);
        shooter.shootIt();
    }

    public void angulateHood(double distance){
        hood.setPositionByDistance(distance);
        hood.loop();
    }



    public void feederUnlock()
    {
        feeder.unlock();
    }


    public void feederLock()
    {
        feeder.lock();
    }

}
