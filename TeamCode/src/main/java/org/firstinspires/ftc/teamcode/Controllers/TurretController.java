package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretController {

    private DcMotorEx turretMotor;

    private static final double TICKS_PER_REVOLUTION = 537.7;
    private static final double GEAR_RATIO = 2.0;
    private static final double TICKS_PER_DEGREE = (TICKS_PER_REVOLUTION * GEAR_RATIO) / 360.0;
    private int zeroPositionOffset = 0;


    public void init(HardwareMap hwMap, String motorName) {
        turretMotor = hwMap.get(DcMotorEx.class, motorName);

        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        turretMotor.setTargetPosition(0);

        turretMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

    }


    public double calculateTargetAngle(double robotX, double robotY, double targetX, double targetY) {
        double deltaX = targetX - robotX;
        double deltaY = targetY - robotY;
        double angleInRadians = Math.atan2(deltaY, deltaX);
        double angleInDegrees = Math.toDegrees(angleInRadians);
        double turretAngle = angleInDegrees - 90.0;
        while (turretAngle > 180) turretAngle -= 360;
        while (turretAngle <= -180) turretAngle += 360;
        return turretAngle;
    }

    public void update(double targetAngle, double speed) {
        int targetPositionTicks = (int) (targetAngle * TICKS_PER_DEGREE);

        turretMotor.setTargetPosition(targetPositionTicks);

        turretMotor.setPower(Math.abs(speed));
    }


    public boolean isBusy() {
        return turretMotor.isBusy();
    }

    public double getCurrentAngle() {
        return (turretMotor.getCurrentPosition() - zeroPositionOffset) / TICKS_PER_DEGREE;
    }

    public void showTelemetry(Telemetry telemetry) {
        telemetry.addData("Turret Target Angle", "%.2f", (double)turretMotor.getTargetPosition() / TICKS_PER_DEGREE);
        telemetry.addData("Turret Current Angle", "%.2f", getCurrentAngle());
        telemetry.addData("Turret Is Busy", isBusy());
    }
}
