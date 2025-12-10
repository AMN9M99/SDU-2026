package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.Controllers.BaseController;
import org.firstinspires.ftc.teamcode.Controllers.BuildOpMode;
import org.firstinspires.ftc.teamcode.Controllers.BuildOpMode;

@TeleOp
public class Main extends OpMode {

    private BuildOpMode buildOpMode = new BuildOpMode();
    private BaseController drive = new BaseController();

    @Override
    public void init() {
        buildOpMode.initOpMode(hardwareMap);
        drive.initialize(hardwareMap, true);
    }

    @Override
    public void loop() {
        drive.update(
                -gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                gamepad1.right_stick_x,
                1.0,
                false,
                false
        );

        buildOpMode.buildOpMode(
                gamepad1.a,
                gamepad1.x,
                gamepad1.y,
                gamepad1.b,
                gamepad1.right_trigger,
                gamepad1.right_bumper,
                gamepad1.left_trigger,
                gamepad1.left_bumper,
                gamepad1.dpad_up
        );

        buildOpMode.showTelemetry(telemetry);
        drive.viewTelemetry(telemetry);
        telemetry.update();
    }
}
