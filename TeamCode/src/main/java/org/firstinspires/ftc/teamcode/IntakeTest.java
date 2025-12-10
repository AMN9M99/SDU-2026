package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class IntakeTest extends OpMode {
    private DcMotor intake1;

    @Override
    public void init() {
        intake1 = hardwareMap.get(DcMotor.class,"intake_1");
    }

    @Override
    public void loop() {
        if (gamepad1.left_bumper) {
            intake1.setPower(1);
        } else if (gamepad1.right_bumper) {
            intake1.setPower(-1);
        }else {
            intake1.setPower(0);
        }
    }
}
