//package org.firstinspires.ftc.teamcode.Tests;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.teamcode.Controllers.TurretController;
//
//@TeleOp
//public class TurretTest extends OpMode {
//
//    private TurretController turretController = new TurretController();
//
//    private double robotX =0;
//    private double robotY =0;
//    private double targetX =10;
//    private double targetY =10;
//
//    @Override
//    public void init(HardwareMap hardwareMap) {
//        turretController.init(hardwareMap,"");
//    }
//
//    public void turret() {
//        double targetAngle = turretController.calculateTargetAngle(robotX,robotY,targetX,targetY);
//
//        turretController.update(targetAngle,0.8);
//    }
//
//    public void Telemetry() {
//        telemetry.addLine("--- Управление турелью (OpMode) ---");
//        telemetry.addData("Координаты робота (X, Y)", "%.1f, %.1f", robotX, robotY);
//        turretController.showTelemetry(telemetry); // Контроллер сам выведет свою информацию
//        telemetry.update();
//    }
//}
