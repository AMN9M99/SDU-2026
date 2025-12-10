package org.firstinspires.ftc.teamcode.Controllers;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Config
public class ModeController {
    private ShooterControllerPIDVSA powerController = new ShooterControllerPIDVSA();
    private ServoPos servoPos = new ServoPos();

    public static double highRPM = 1513;
    public static double mediumRPM = 1233;
    public static double lowRPM = 1000;

    public static double positionHigh = 0.6;
    public static double positionMedium = 0.56;
    public static double positionLow = 0.5;

    private double autoRPM = 0;
    private double autoPos = 0;
    private boolean isAutoMode = false;
    private boolean leftBumperWasPressed = false;

    public boolean shooterIsReady(double offset) {
        return powerController.isReadyToShoot(offset);
    }

    public void showTelemetry(Telemetry telemetry) {
        powerController.update();
        powerController.showTelemetry(telemetry);
        telemetry.addData("isAutoMode", isAutoMode);
    }

    public void init(HardwareMap hw, String shooterMotorLeftName, String shooterMotorRightName, String servoAngleLeftName, String servoAngleRightName) {
        servoPos.initServo(hw, servoAngleLeftName, servoAngleRightName);
        powerController.initialize(hw, shooterMotorLeftName, shooterMotorRightName);
    }

    public void modeShoot(boolean y, boolean x, boolean a, boolean stopButton) {
        powerController.update();

        if (y) {
            servoPos.setPosServo(positionHigh);
            powerController.setShooterVelocity(highRPM);
        } else if (x) {
            servoPos.setPosServo(positionMedium);
            powerController.setShooterVelocity(mediumRPM);
        } else if (a) {
            servoPos.setPosServo(positionLow);
            powerController.setShooterVelocity(lowRPM);
        } else if (stopButton) {
            powerController.setShooterVelocity(0);
        }
    }

    public void update(boolean y, boolean x, boolean a, boolean stopButton, boolean autoToggleButton, double robotY) {
        if (autoToggleButton && !leftBumperWasPressed) {
            isAutoMode = !isAutoMode;
        }
        leftBumperWasPressed = autoToggleButton;

        if (isAutoMode) {
            if (robotY > 99) {
                autoPos = positionLow;
                autoRPM = lowRPM;
            } else if (robotY >= 45) {
                autoPos = positionMedium;
                autoRPM = mediumRPM;
            } else {
                autoPos = positionHigh;
                autoRPM = highRPM;
            }
            powerController.setShooterVelocity(autoRPM);
            servoPos.setPosServo(autoPos);
        } else {
            modeShoot(y, x, a, stopButton);
        }
        powerController.update();
    }

    public double getHighRPM() { return highRPM; }
    public double getMediumRPM() { return mediumRPM; }
    public double getLowRPM() { return lowRPM; }
    public double getServoHigh() { return positionHigh; }
    public double getServoMedium() { return positionMedium; }
    public double getServoLow() { return positionLow; }
}
