package org.firstinspires.ftc.teamcode.Controllers;


import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;


public class BaseController {



    private double lastHeading, currentHeading, setPos;
    private double angleTarget;

    private GoBildaPinpointDriver pinpoint = null;

    private Follower follower;


    public void init(HardwareMap hardwareMap) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
        pinpoint.recalibrateIMU();
        pinpoint.resetPosAndIMU();
        pinpoint.resetPosAndIMU();
        pinpoint.setHeading(0, AngleUnit.RADIANS);


        follower = Constants.createFollower(hardwareMap);
        follower.update();


        follower.startTeleopDrive(true);
        follower.setStartingPose(new Pose(0, 0, 0));

    }




    public void update(double left_stick_y, double left_stick_x, double right_stick_x,boolean back, Telemetry telemetry) {
        if (back){
            pinpoint.setHeading(0,AngleUnit.RADIANS);
//            pinpoint.setPosX(follower.getPose().getX() ,AngleUnit.RADIANS);
            pinpoint.recalibrateIMU();

            Pose bPose = new Pose(follower.getPose().getX(),follower.getPose().getY(),0);
            follower.setPose(bPose);
        }

        double forward = -left_stick_y;
        double strafe = -left_stick_x;
        double rotate = -right_stick_x;

        if (forward <= 0.05 && forward >= -0.05) {
            forward = 0;
        }
        if (strafe <= 0.05 && strafe >= -0.05) {
            strafe = 0;
        }
        if (rotate <= 0.05 && rotate >= -0.05) {
            rotate = 0;
        }

        follower.update();
        follower.setTeleOpDrive(
                forward,
                strafe,
                rotate * 0.7,
                false // Robot Centric
        );
        telemetry.addData("robot X: ", follower.getPose().getX());
        telemetry.addData("robot Y: ", follower.getPose().getY());
        telemetry.addData("robot Y: ", follower.getPose().getY());
    }
    public double getX(){
        return follower.getPose().getX();
    }
    public double getY(){
        return follower.getPose().getY();
    }
    public double getAngle(){return follower.getHeading();}




        }





//    public Follower getFollower() {
//        return follower;
//    }
//
//    public double getX() {
//        return follower.getPose().getX();
//    }
//
//    public double getY() {
//        return follower.getPose().getY();
//    }
//    public double HeadingRobot(){
//        return follower.getHeading();
//    }
//    public void followUpdate(){
//        follower.update();
//    }
//
//    public double turretController( double robotX, double robotY, double targetX, double targetY) {
////     fieldDriveController.followUpdate();
//
//        angleTarget = utilAngle.calculateAbsoluteAngleDifference(robotX, robotY, targetX, targetY);
//        lastHeading = currentHeading;
//        double heading = Math.toDegrees(follower.getHeading());
//        currentHeading = heading;
//        setPos = currentHeading;
//        if (setPos > 180) {
//            setPos += -360;
//        }
//        if (setPos < -180) {
//            setPos += 360;
//        }
//
//
//        setPos = setPos * utilEncoder.getDivisionPrice();
//        if (setPos > 350) {
//            setPos += (-700);
//        }
//        if (setPos < -350) {
//            setPos += 700;
//        }
//
////        pidfTurret.updatePIDTurret(setPos);
//
//        return setPos;
//
//    }
////    public void showTelemetry(Telemetry telemetry){
////     follower.update();
////        double fds = turretController (getX(), getY(), 0,0);
////        double angleTarget21 = utilAngle.calculateAbsoluteAngleDifference(getX(), getY(), 0,0);
////        double dsaf = Math.toDegrees(follower.getHeading());
////        telemetry.addData("TurretController",fds );
////        telemetry.addData("Heading", dsaf);
////        telemetry.addData("last" , lastHeading);
////        telemetry.addData("TurretController",currentHeading );
//
//
//
//
//
//    }
//    public void TurretTeleOp(boolean dpadRight, boolean dpadLeft){
//        if (dpadRight){
//            servoPos.setTurret(1);
//        }else {servoPos.setTurret(0);}
//    if (dpadLeft){
//
//        servoPos.setTurret(-1);
//    }else {servoPos.setTurret(0);}
//    }
//}



