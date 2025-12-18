package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class BaseIntake extends OpMode {
    private DcMotor lf,rf,lb,rb;
    private DcMotor intake_1;

    @Override
    public void init() {
        lf = hardwareMap.get(DcMotor.class,"lfd");
        rf = hardwareMap.get(DcMotor.class,"rfd");
        lb = hardwareMap.get(DcMotor.class,"lbd");
        rb = hardwareMap.get(DcMotor.class,"rbd");
        intake_1 = hardwareMap.get(DcMotor.class,"intake_1");

        lf.setDirection(DcMotorSimple.Direction.REVERSE);
        lb.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double forward  = -gamepad1.left_stick_y;
        double strafe = gamepad1.left_stick_x;
        double ratate = gamepad1.right_stick_x;

        double lfd = forward + strafe + ratate;
        double rfd = forward - strafe - ratate;
        double lbd = forward - strafe + ratate;
        double rbd = forward + strafe - ratate;

        lf.setPower(lfd);
        rf.setPower(rfd);
        lb.setPower(lbd);
        rb.setPower(rbd);

        if (gamepad1.right_trigger > 0.1) {
            intake_1.setPower(1);
        } else if (gamepad1.left_trigger >0.1) {
            intake_1.setPower(-1);
        }else {
            intake_1.setPower(0);
        }
    }
}
