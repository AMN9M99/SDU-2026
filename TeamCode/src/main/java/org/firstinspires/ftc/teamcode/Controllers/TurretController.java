package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class TurretController {

    private DcMotorEx turretMotor;
    private double robotX = 0;
    private double robotY = 0;
    private double robotHeading = 0;

    private static final double TICKS_PER_MOTOR_REVOLUTION = 383.6;
    private static final double GEAR_RATIO = 3.125; //turret = 50, shesterenka = 16
    private static final double TICKS_PER_DEGREE = (TICKS_PER_MOTOR_REVOLUTION * GEAR_RATIO) / 360.0;

    private static final double MIN_TURRET_ANGLE = -180.0;
    private static final double MAX_TURRET_ANGLE = 180.0;
    private static final double ANGLE_TOLERANCE_DEGREES = 5.0;

    private long lastMoveTime = 0;
    private double lastPosition = 0;



    private static final double MIN_POWER = 0.1;
    private static final double KP = 0.05;

    public void init(HardwareMap hwMap) {
        turretMotor = hwMap.get(DcMotorEx.class,"turret");
        turretMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        turretMotor.setTargetPosition(0);
        turretMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turretMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lastMoveTime = System.currentTimeMillis();
        lastPosition = 0;
    }

    public void updateRobotPose(double x, double y, double heading) {
        this.robotX = x;
        this.robotY = y;
        this.robotHeading = heading;
    }

    public double calculateTargetAngle(double robotX, double robotY,
                                       double robotHeading,
                                       double targetX, double targetY) {
        double deltaX = targetX - robotX;
        double deltaY = targetY - robotY;

        double angleToTarget = Math.toDegrees(Math.atan2(deltaY, deltaX));

        double turretAngle = angleToTarget - robotHeading;

        while (turretAngle > 180) turretAngle -= 360;
        while (turretAngle <= -180) turretAngle += 360;

        return turretAngle;
    }

    public void aimAtTarget(double targetX, double targetY, double maxSpeed) {
        double targetAngle = calculateTargetAngle(robotX, robotY, robotHeading, targetX, targetY);
        update(targetAngle, maxSpeed);
    }

    public void update(double targetAngle, double maxSpeed) {
        double limitedTargetAngle = Math.max(MIN_TURRET_ANGLE, Math.min(targetAngle, MAX_TURRET_ANGLE));

        int targetPositionTicks = (int) (limitedTargetAngle * TICKS_PER_DEGREE);
        turretMotor.setTargetPosition(targetPositionTicks);

        double error = limitedTargetAngle - getCurrentAngle();

        if (isStuck()) {
            turretMotor.setPower(0);
            return;
        }

        if (Math.abs(error) < ANGLE_TOLERANCE_DEGREES) {
            turretMotor.setPower(0);
        } else {
            double power = calculatePower(error, maxSpeed);
            turretMotor.setPower(power);
        }

        updateStuckDetection();
    }

    private double calculatePower(double error, double maxSpeed) {
        double proportionalPower = Math.abs(error) * KP;
        double power = Math.min(proportionalPower, maxSpeed);
        return Math.max(power, MIN_POWER);
    }

    private boolean isStuck() {
        long currentTime = System.currentTimeMillis();
        double currentPosition = getCurrentAngle();



        return false;
    }

    private void updateStuckDetection() {
        double currentPosition = getCurrentAngle();


        lastPosition = currentPosition;
    }

    public double getCurrentAngle() {
        return turretMotor.getCurrentPosition() / TICKS_PER_DEGREE;
    }

    public boolean isAtTarget() {
        double error = (double)turretMotor.getTargetPosition() / TICKS_PER_DEGREE - getCurrentAngle();
        return Math.abs(error) < ANGLE_TOLERANCE_DEGREES;
    }

    public void stop() {
        turretMotor.setPower(0);
    }

    public void resetToZero(double speed) {
        update(0, speed);
    }

    public void setDirection(DcMotor.Direction direction) {
        turretMotor.setDirection(direction);
    }

    public void manualControl(double power) {
        turretMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        turretMotor.setPower(power);
    }

    public void returnToAutoMode() {
        turretMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public double getDistanceToTarget(double targetX, double targetY) {
        double deltaX = targetX - robotX;
        double deltaY = targetY - robotY;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public void showTelemetry(Telemetry telemetry) {
        telemetry.addLine("--- Turret Info ---");
        telemetry.addData("Target Angle", "%.2f°", (double)turretMotor.getTargetPosition() / TICKS_PER_DEGREE);
        telemetry.addData("Current Angle", "%.2f°", getCurrentAngle());
        telemetry.addData("Robot Heading", "%.2f°", robotHeading);
        telemetry.addData("At Target", isAtTarget() ? "YES" : "NO");
        telemetry.addData("Motor Power", "%.2f", turretMotor.getPower());
        telemetry.addData("Stuck", isStuck() ? "WARNING!" : "OK");
        telemetry.addData("Robot Position", "(%.1f, %.1f)", robotX, robotY);
    }
}