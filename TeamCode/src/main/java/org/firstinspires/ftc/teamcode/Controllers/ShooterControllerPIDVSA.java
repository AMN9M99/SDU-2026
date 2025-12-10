package org.firstinspires.ftc.teamcode.Controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ShooterControllerPIDVSA  {
    private DcMotorEx shooterMotorLeft;
    private DcMotorEx shooterMotorRight;
    private VoltageSensor voltageSensor;

    public static double kS = 0.59;
    public static double kV = 0.00455;
    public static double kA = 0.001;
    public static double kP = 0.035;

    private double targetVelocityRPM = 0;
    private double lastError = 0;
    private double lastVelocity = 0;
    private long lastTime = 0;
    private static final double NOMINAL_VOLTAGE = 12.5;

    public void initialize(HardwareMap hw, String shooterMotorLname, String shooterMotorRname) {
        shooterMotorLeft = hw.get(DcMotorEx.class, shooterMotorLname);
        shooterMotorRight = hw.get(DcMotorEx.class, shooterMotorRname);

        shooterMotorLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        shooterMotorRight.setDirection(DcMotorSimple.Direction.FORWARD);

        shooterMotorLeft.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotorRight.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);

        voltageSensor = hw.voltageSensor.iterator().next();
        lastTime = System.nanoTime();
    }

    public boolean checkVelocity(double targetVelocity,double offset){
        return Math.abs(shooterMotorLeft.getVelocity()) >= Math.abs(targetVelocity - offset);
    }

    public void setShooterVelocity(double targetRPM) {
        this.targetVelocityRPM = targetRPM;
    }

    public void update() {
        if (targetVelocityRPM == 0) {
            shooterMotorLeft.setPower(0);
            shooterMotorRight.setPower(0);
            lastError = 0;
            return;
        }

        long currentTime = System.nanoTime();
        double dt = (currentTime - lastTime) * 1e-9;
        lastTime = currentTime;

        double currentVelocity = (shooterMotorLeft.getVelocity() + shooterMotorRight.getVelocity()) / 2.0;
        double acceleration = (currentVelocity - lastVelocity) / dt;
        lastVelocity = currentVelocity;

        double currentVoltage = voltageSensor.getVoltage();
        double feedforwardVoltage = kS * Math.signum(targetVelocityRPM) +
                kV * targetVelocityRPM +
                kA * acceleration;

        double error = targetVelocityRPM - currentVelocity;
        double pidVoltage = kP * error;
        double totalVoltage = feedforwardVoltage + pidVoltage;
        double power = totalVoltage / currentVoltage;
        power = Math.max(-1.0, Math.min(1.0, power));

        shooterMotorLeft.setPower(power);
        shooterMotorRight.setPower(power);
    }

    public boolean isReadyToShoot(double tolerance) {
        if (targetVelocityRPM == 0) return false;
        double currentVelocity = (shooterMotorLeft.getVelocity() + shooterMotorRight.getVelocity()) / 2.0;
        return Math.abs(currentVelocity - targetVelocityRPM) < tolerance;
    }

    public void showTelemetry(Telemetry telemetry) {
        double currentVelocity = (shooterMotorLeft.getVelocity() + shooterMotorRight.getVelocity()) / 2.0;
        double currentVoltage = voltageSensor.getVoltage();
        double voltageCompensation = NOMINAL_VOLTAGE / currentVoltage;

        telemetry.addLine("=== WPILib Shooter Controller ===");
        telemetry.addData("Target RPM", "%.1f", targetVelocityRPM);
        telemetry.addData("Current RPM", "%.1f", currentVelocity);
        telemetry.addData("Error RPM", "%.1f", targetVelocityRPM - currentVelocity);
        telemetry.addData("Battery Voltage", "%.2fV", currentVoltage);
        telemetry.addData("Voltage Compensation", "%.3f", voltageCompensation);
        telemetry.addData("Ready to Shoot", isReadyToShoot(50) ? "YES" : "NO");
        telemetry.addData("Motor Power", "%.3f", shooterMotorLeft.getPower());
        telemetry.addData("Left Motor:", shooterMotorLeft.getVelocity());
        telemetry.addData("Right Motor:", shooterMotorRight.getVelocity());
    }
}
