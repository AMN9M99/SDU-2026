package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.Controllers.AprilTagWebcam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class AprilTagWebCamExample extends OpMode {

    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init() {
        aprilTagWebcam.init(hardwareMap, telemetry);
        telemetry.addLine("Initialization complete. Waiting for start...");
        telemetry.update();
    }

    @Override
    public void loop() {
        aprilTagWebcam.update();

        AprilTagDetection id21 = aprilTagWebcam.getTagBySpecificId(21);

        if (id21 != null) {
            aprilTagWebcam.displayDetectionTelemetry(id21);
        } else {
            telemetry.addLine("Tag with ID 21 not visible.");
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        aprilTagWebcam.stop();
    }
}
