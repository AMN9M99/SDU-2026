package org.firstinspires.ftc.teamcode.DigitalDeviceControllers;

import android.util.Size;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import java.util.List;

public class AprilTagController {

    private AprilTagProcessor aprilTagProcessor;
    private VisionPortal visionPortal;

    public void init(HardwareMap hwMap) {
        aprilTagProcessor = new AprilTagProcessor.Builder()
                .setOutputUnits(DistanceUnit.CM, AngleUnit.DEGREES)
                .build();

        visionPortal = new VisionPortal.Builder()
                .setCamera(hwMap.get(WebcamName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(aprilTagProcessor)
                .enableLiveView(true)
                .build();
    }

    public List<AprilTagDetection> getDetections() {
        if (visionPortal == null) {
            return null;
        }
        return aprilTagProcessor.getDetections();
    }

    public void close() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }
}

