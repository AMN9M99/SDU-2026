package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.Controllers.HuskyLensAiCameraController;

@Autonomous
public class HuskyLensExample extends OpMode {
    HuskyLensAiCameraController huskyLensController = new HuskyLensAiCameraController();

    @Override
    public void init() {
        huskyLensController.init(hardwareMap, "huskylens");
        telemetry.addLine("HuskyLens AI Camera Initialized.");
        telemetry.addLine("Mode: TAG_RECOGNITION");
        telemetry.update();
    }

    @Override
    public void loop() {
        huskyLensController.update();
        huskyLensController.displayTelemetry(telemetry);
        telemetry.update();
    }
}



