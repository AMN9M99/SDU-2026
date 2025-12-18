package org.firstinspires.ftc.teamcode.Controllers;



import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierCurve;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.HeadingInterpolator;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.function.Supplier;


public class AutoParkingController {

    private Follower follower;
    private Telemetry telemetry;
    private IntakeController intake;
    private ModeController modeController;
    
    private final Pose AutoParkingPose = new Pose(10, 10, 0);

    private boolean isAutoDriving = false;
    private Supplier<PathChain> pathToParking;


    // Инициализация с Follower и Telemetry (базовая версия)
    public void init(Follower follower, Telemetry telemetry) {
        this.follower = follower;
        this.telemetry = telemetry;

        pathToParking = () -> this.follower.pathBuilder()
                .addPath(new Path(new BezierCurve(this.follower.getPose(), AutoParkingPose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();

        this.telemetry.addLine("AutoParkingController has been initialized.");
    }

    // Полная инициализация со всеми контроллерами из BuildOpMode
    public void init(Follower follower, Telemetry telemetry, IntakeController intake, ModeController modeController) {
        this.follower = follower;
        this.telemetry = telemetry;
        this.intake = intake;
        this.modeController = modeController;

        pathToParking = () -> this.follower.pathBuilder()
                .addPath(new Path(new BezierCurve(this.follower.getPose(), AutoParkingPose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();

        this.telemetry.addLine("AutoParkingController has been initialized with all controllers.");
    }

    public void mechanism(boolean dpadDown) {
        // Обновляем позицию робота
        follower.update();

        if (dpadDown && !isAutoDriving && !follower.isBusy()) {
            isAutoDriving = true;
            
            // Останавливаем все механизмы перед парковкой
            if (intake != null) {
                intake.setIntakePower(0);
                intake.setSecIntakeMotor(0);
            }
            
            // Запускаем движение к парковке
            follower.followPath(pathToParking.get());
            
            if (telemetry != null) {
                telemetry.addLine("AutoParking: Started parking sequence");
            }
        }

        if (isAutoDriving && !follower.isBusy()) {
            isAutoDriving = false;
            if (telemetry != null) {
                telemetry.addLine("AutoParking: Parking completed");
            }
        }
    }

    // Метод для установки целевой позиции парковки
    public void setParkingPose(Pose pose) {
        // Обновляем путь с новой позицией
        pathToParking = () -> this.follower.pathBuilder()
                .addPath(new Path(new BezierCurve(this.follower.getPose(), pose)))
                .setHeadingInterpolation(HeadingInterpolator.tangent)
                .build();
    }

    // Метод для получения текущей целевой позиции
    public Pose getParkingPose() {
        return AutoParkingPose;
    }

    public boolean isAutoParking() {
        return isAutoDriving;
    }
    
    // Метод для показа телеметрии
    public void showTelemetry(Telemetry telemetry) {
        telemetry.addLine("=== AutoParkingController ===");
        telemetry.addData("Is Auto Parking", isAutoDriving);
        telemetry.addData("Follower Busy", follower.isBusy());
        telemetry.addData("Target Position", String.format("(%.1f, %.1f, %.1f)", 
            AutoParkingPose.getX(), AutoParkingPose.getY(), AutoParkingPose.getHeading()));
        if (follower != null) {
            Pose currentPose = follower.getPose();
            telemetry.addData("Current Position", String.format("(%.1f, %.1f, %.1f)", 
                currentPose.getX(), currentPose.getY(), currentPose.getHeading()));
        }
    }
}
