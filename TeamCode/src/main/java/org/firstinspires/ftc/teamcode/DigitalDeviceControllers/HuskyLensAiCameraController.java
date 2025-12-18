package org.firstinspires.ftc.teamcode.DigitalDeviceControllers;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class HuskyLensAiCameraController {

    //tag id 4 = 22 id purple|green|purple
    //tag id 1 = 23 id purple|purple|green
    //tag id 3 = 21 id green|purple|purple

    private Integer savedTagID = null;
    private HuskyLens huskyLens;
    private HuskyLens.Block[] blocks = new HuskyLens.Block[0];

    public void init(HardwareMap hwMap, String cameraName) {
        huskyLens = hwMap.get(HuskyLens.class, cameraName);

        if (!huskyLens.knock()) {
        }

        huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
    }

    public void update() {
        blocks = huskyLens.blocks();

        if (savedTagID == null && blocks.length > 0) {
            savedTagID = blocks[0].id;
        }
    }

    public Integer getSavedTagID() {
        return savedTagID;
    }

    public int getDetectedAprilTagId() {
        if (blocks.length > 0) {
            return blocks[0].id;
        }
        return 0;
    }

    public void displayTelemetry(Telemetry telemetry) {
        telemetry.addData("Tags Detected", blocks.length);

        for (int i = 0; i < blocks.length; i++) {
            HuskyLens.Block block = blocks[i];
            telemetry.addLine(
                    String.format("Tag[%d]: ID=%d, X=%d, Y=%d",
                            i, block.id, block.x, block.y)
            );
        }

        telemetry.addData("Saved Tag ID", savedTagID);
    }
}
