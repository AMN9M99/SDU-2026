//package org.firstinspires.ftc.teamcode.Tests;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.teamcode.Controllers.TurretController;
//
//@TeleOp
//public class TurretTest extends OpMode {
//    private TurretController turret = new TurretController();
//
//    private double robotX = 0;
//    private double robotY = 0;
//    private final double targetX = 0;
//    private final double targetY = 0;
//
//    @Override
//    public void init() {
//        turret.init(hardwareMap,"turret");
//    }
//
//    @Override
//    public void loop() {
//        double targetAngle = turret.calculateTargetAngle(robotX,robotY,targetX,targetY);
//        turret.update(targetAngle,0.8);
//        turret.getCurrentAngle();
//        turret.isAtTarget();
//        turret.showTelemetry(telemetry);
//    }
//}
