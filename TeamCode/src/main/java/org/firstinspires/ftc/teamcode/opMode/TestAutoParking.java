package org.firstinspires.ftc.teamcode.opMode;

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

@TeleOp(name = "Go To AutoParking Example", group = "PedroPathing")
public class TestAutoParking extends OpMode {

    private Follower follower;
    private final Pose AutoParkingPose = new Pose(10, 10, 0);

    private boolean isAutoDriving = false;
    private Supplier<PathChain> pathToParking;

    @Override
    public void init() {

        follower = Constants.createFollower(hardwareMap);

        follower.setStartingPose(new Pose());
        follower.update();

        pathToParking = () -> follower.pathBuilder()
                .addPath(new Path(new BezierLine(follower::getPose, AutoParkingPose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();

        telemetry.addLine("Initialization complete.");
        telemetry.addLine("Drive the robot around manually.");
        telemetry.addLine("Press 'A' to start automatic parking.");
        telemetry.update();
    }

    @Override
    public void start() {

        follower.startTeleopDrive();
    }

    @Override
    public void loop() {
        follower.update();

        if (gamepad1.a && !isAutoDriving) {
            isAutoDriving = true;

            follower.followPath(pathToParking.get());
        }

        if (isAutoDriving) {
            telemetry.addLine("!!! AUTO-PARKING ACTIVE !!!");
            telemetry.addData("Target Pose", AutoParkingPose.toString());

            if (!follower.isBusy()) {
                isAutoDriving = false;
                follower.startTeleopDrive();
            }

        } else {
            telemetry.addLine("--- Manual Drive Mode ---");
            telemetry.addLine("Press 'A' to park.");

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
