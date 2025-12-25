package org.firstinspires.ftc.teamcode.Controllers;



import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;


public class AutoParkingController {

    private Follower follower;
    private Telemetry telemetry;
    private IntakeController intake;

    
    private final Pose AutoParkingPose = new Pose(10, 10, 0);

    private boolean isAutoDriving = false;
    private Supplier<PathChain> pathToParking;


    public void init(Follower follower, Telemetry telemetry) {
        this.follower = follower;
        this.telemetry = telemetry;

        pathToParking = () -> this.follower.pathBuilder()
                .addPath(new Path(new BezierCurve(this.follower.getPose(), AutoParkingPose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();

    }

    public void init(Follower follower, Telemetry telemetry, IntakeController intake) {
        this.follower = follower;
        this.telemetry = telemetry;
        this.intake = intake;

        pathToParking = () -> this.follower.pathBuilder()
                .addPath(new Path(new BezierCurve(this.follower.getPose(), AutoParkingPose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();

    }

    public void mechanism(boolean dpadDown) {
        follower.update();

        if (dpadDown && !isAutoDriving && !follower.isBusy()) {
            isAutoDriving = true;
            

            
            follower.followPath(pathToParking.get());
            
            if (telemetry != null) {
                telemetry.addLine("AutoParking: Started parking sequence");
            }
        }

        if (isAutoDriving && !follower.isBusy()) {
            isAutoDriving = false;
            if (telemetry != null) {
                telemetry.addLine("AutoParking: Parking completed");
            }
        }
    }

    public void setParkingPose(Pose pose) {
        pathToParking = () -> this.follower.pathBuilder()
                .addPath(new Path(new BezierLine(this.follower.getPose(), pose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();
    }

    public Pose getParkingPose() {
        return AutoParkingPose;
    }

    public boolean isAutoParking() {
        return isAutoDriving;
    }
    
    public void showTelemetry(Telemetry telemetry) {
        telemetry.addData("Is Auto Parking", isAutoDriving);
        telemetry.addData("Follower Busy", follower.isBusy());
        telemetry.addData("Target Position", String.format("(%.1f, %.1f, %.1f)", 
            AutoParkingPose.getX(), AutoParkingPose.getY(), AutoParkingPose.getHeading()));
        if (follower != null) {
            Pose currentPose = follower.getPose();
            telemetry.addData("Current Position", String.format("(%.1f, %.1f, %.1f)", 
                currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
        }
    }
}
