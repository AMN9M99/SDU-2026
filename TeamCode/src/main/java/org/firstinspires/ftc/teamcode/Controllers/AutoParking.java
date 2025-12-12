package org.firstinspires.ftc.teamcode.Controllers;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.util.ElapsedTime;

public class AutoParking {

    public static final double TeleOp_Duration = 120.0;
    public static final double AutoParking_TriggerTime =7.0;
    private boolean autoParkingStarted = false;
    private ElapsedTime matchTimer = new ElapsedTime();
    private final Pose AutoParking = new Pose(0,0,0);
}
