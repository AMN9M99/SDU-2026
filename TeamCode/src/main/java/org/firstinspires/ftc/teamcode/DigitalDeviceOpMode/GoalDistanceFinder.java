package org.firstinspires.ftc.teamcode.DigitalDeviceOpMode;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.DigitalDeviceControllers.AprilTagController;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import java.util.List;

@Config
@Autonomous(name = "Goal Distance Finder", group = "AprilTag")
public class GoalDistanceFinder extends LinearOpMode {

    public static int GOAL_TAG_ID = 20;

    private AprilTagController aprilTagController = new AprilTagController();

    @Override
    public void runOpMode() throws InterruptedException {

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        aprilTagController.init(hardwareMap);

        telemetry.addLine("Ready! Point camera at the goal.");
        telemetry.addLine("Check FTC Dashboard for live camera stream.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {

            List<AprilTagDetection> allDetections = aprilTagController.getDetections();
            AprilTagDetection goalTag = null;

            if (allDetections != null) {
                for (AprilTagDetection detection : allDetections) {
                    if (detection.id == GOAL_TAG_ID) {
                        goalTag = detection;
                        break;
                    }
                }
            }

            if (goalTag != null) {
                telemetry.addLine(String.format("--- TARGET (ID %d) FOUND! ---", goalTag.id));
                double distanceToGoal = goalTag.ftcPose.y;
                telemetry.addData("Distance to Goal", "%.2f cm", distanceToGoal);
                double lateralOffset = goalTag.ftcPose.x;
                telemetry.addData("Lateral Offset", "%.2f cm", lateralOffset);
            } else {
                telemetry.addLine("--- Goal tag not visible ---");
            }

            telemetry.update();
            sleep(50);
        }

        aprilTagController.close();
    }
}

