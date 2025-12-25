package org.firstinspires.ftc.teamcode.Tests;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.function.Supplier;

@TeleOp
public class AutoHighPos extends OpMode {
    private Follower follower;
    private final Pose HighPose = new Pose(0,0,0);
    private boolean isAutoDriving = false;
    private Supplier<PathChain> pathToStateHigh;

    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();

        pathToStateHigh = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, HighPose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();

        telemetry.addLine("Initialization complete.");
        telemetry.addLine("Drive the robot around manually.");
        telemetry.addLine("Press 'A' to start automatic go to highPose.");
        telemetry.update();
    }
    public void start() {
        follower.startTeleOpDrive();
    }

    @Override
    public void loop() {
        follower.update();

        if (gamepad1.a && !isAutoDriving) {
            isAutoDriving = true;

            follower.followPath(pathToStateHigh.get());
        }

        if (isAutoDriving) {

            telemetry.addLine("!!! AUTO-HighPose ACTIVE !!!");
            telemetry.addData("Target Pose", HighPose.toString());

            if (!follower.isBusy()){
              isAutoDriving = false;
              follower.startTeleOpDrive();
            }

        }else {
            telemetry.addLine("--- Manual Drive Mode ---");
            telemetry.addLine("Press 'A' to goHighPose.");

            follower.setTeleOpDrive(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x
            );
        }

        telemetry.addData("isAutoDriving", isAutoDriving);
        telemetry.addData("isFollowerBusy", follower.isBusy());
        telemetry.addData("Current Pose", follower.getPose().toString());
        telemetry.update();

    }
}
