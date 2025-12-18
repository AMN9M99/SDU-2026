package org.firstinspires.ftc.teamcode.Controllers;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.Controllers.ModeController;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import java.util.ArrayList;

@Config
public class BuildOpMode {
    private IntakeController intake = new IntakeController();
    private ModeController modeController = new ModeController();
    private Follower follower;
    private AutoParkingController autoParkingController =  new AutoParkingController();

    public static double tolerance = 100;
    public static int shootTime = 150;
    public static int resetTime = 400;

    private enum IntakeState {
        COLLECTION, SHOOT, OUTTAKE, STOP, RESET, NULL
    }
    private IntakeState intakeState = IntakeState.NULL;

    private boolean isShooting = false;
    private boolean isAutoMode = false;
    private ElapsedTime timer = new ElapsedTime();
    private ElapsedTime kdButton = new ElapsedTime();
    private double robotY;


    public void initOpMode(HardwareMap hardwareMap,Telemetry telemetry) {
        intake.init(hardwareMap, "intake_1", "intake_2");
        modeController.init(hardwareMap, "shooter_l", "shooter_r", "l_angle", "r_angle");

        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(new Pose());
        follower.update();
        // Инициализация AutoParkingController со всеми контроллерами из BuildOpMode
        autoParkingController.init(follower, telemetry, intake, modeController);
    }

    public void AutoParkingMechanism(boolean dpadDown) {
        autoParkingController.mechanism(dpadDown);
    }

    public void intakeFinal(double rightTrigger, boolean rightBumper, double leftTrigger, boolean b) {
        if (rightBumper) {
            intake.setIntakePower(1);
            intake.setSecIntakeMotor(-1);
            intakeState = IntakeState.SHOOT;
            isShooting = true;
        } else if (rightTrigger > 0) {
            intake.setIntakePower(1);
            intake.setSecIntakeMotor(1);
            intakeState = IntakeState.COLLECTION;
        } else if (b) {
            if (modeController.shooterIsReady(tolerance)) {
                intake.setIntakePower(1);
                intake.setSecIntakeMotor(-1);
                intakeState = IntakeState.SHOOT;
                isShooting = true;
            } else {
                intake.setIntakePower(1);
                intake.setSecIntakeMotor(1);
                intakeState = IntakeState.COLLECTION;
            }
        } else if (leftTrigger > 0) {
            intake.setIntakePower(-1);
            intake.setSecIntakeMotor(1);
            intakeState = IntakeState.OUTTAKE;
        } else {
            intake.setIntakePower(0);
            intake.setSecIntakeMotor(0);
            intakeState = IntakeState.STOP;
        }
    }

    public void autoShooterMode(boolean a, boolean x, boolean y, boolean leftBumper, boolean dpadUp) {
        if (dpadUp) {
            if (kdButton.milliseconds() > 400) {
                isAutoMode = !isAutoMode;
                kdButton.reset();
            }
        } else {
            kdButton.reset();
        }

        if (isAutoMode) {
            follower.update();
            robotY = follower.getPose().getY();
            modeController.update(false, false, false, false, leftBumper, robotY);
        } else {
            modeController.update(y, x, a, leftBumper, false, 0);
        }
    }

    public void buildOpMode(boolean a, boolean x, boolean y, boolean b, double rightTrigger, boolean rightBumper, double leftTrigger, boolean leftBumper, boolean dpadUp,boolean dpadDown) {
        autoShooterMode(a, x, y, leftBumper, dpadUp);
        intakeFinal(rightTrigger, rightBumper, leftTrigger, b);
        AutoParkingMechanism(dpadDown);
    }

    public IntakeState getState() {
        return intakeState;
    }

    public void showTelemetry(Telemetry telemetry) {
        telemetry.addLine("=== BuildOpMode Telemetry ===");
        telemetry.addData("Intake State", getState());
        telemetry.addData("Shooter Ready", modeController.shooterIsReady(tolerance));
        modeController.showTelemetry(telemetry);
        autoParkingController.showTelemetry(telemetry);
        telemetry.addData("Сеня","гандон");
    }
}
