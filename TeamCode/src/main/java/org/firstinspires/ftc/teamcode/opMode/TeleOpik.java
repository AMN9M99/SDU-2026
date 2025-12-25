package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Controllers.BaseController;
import org.firstinspires.ftc.teamcode.Controllers.IntakeController;
import org.firstinspires.ftc.teamcode.Controllers.ShooterControllerPIDVSA;
import org.firstinspires.ftc.teamcode.Controllers.TurretController;

@TeleOp
public class TeleOpik extends OpMode {
    private IntakeController intake = new IntakeController();
    private ShooterControllerPIDVSA shooter = new ShooterControllerPIDVSA();
    private BaseController base = new BaseController();
    private TurretController turret = new TurretController();

    @Override
    public void init() {
        intake.initMotor(hardwareMap);
        intake.initServo(hardwareMap);
        shooter.initialize(hardwareMap);
        turret.init(hardwareMap);
        base.init(hardwareMap);
    }

    @Override
    public void loop() {

        if (gamepad1.a){
            shooter.setShooterVelocity(shooter.HIGH_VELOCITY);
            intake.setCloseBoss();
            intake.setPoseOpenMini();
        } else if (gamepad1.b) {
            shooter.setShooterVelocity(shooter.MID_VELOCITY);
            intake.setCloseBoss();
            intake.setPoseOpenMini();
        } else if (gamepad1.x) {
            shooter.setShooterVelocity(shooter.LOW_VELOCITY);
            intake.setCloseBoss();
            intake.setPoseOpenMini();
        }else if (gamepad1.y){
            shooter.setShooterVelocity(0);
        }

        if (gamepad1.left_trigger > 0){
            intake.setIntakeOne(1);
            intake.setPoseOpenBoss();
            intake.setCloseMini();
        } else if (gamepad1.right_trigger > 0) {
            intake.setIntakeOne(1);
        }else {
            intake.setIntakeOne(0);
        }

        base.update(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x,gamepad1.back, telemetry);


    }
}
