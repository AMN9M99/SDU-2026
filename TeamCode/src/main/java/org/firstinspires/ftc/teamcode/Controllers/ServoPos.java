package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


public class ServoPos {
    private Servo servoL;
    private Servo servoR;
    private CRServo turret;
    private AnalogInput encoder = null;

    private double servoposition = 0;


    public void initServo(HardwareMap hw,String servoAngleLeftName, String servoAngleRightName){


        servoL = hw.get(Servo.class, servoAngleLeftName);
        servoR = hw.get(Servo.class, servoAngleRightName);

        servoL.setDirection(Servo.Direction.REVERSE);
    }

//    public void update(){
//        servoposition = encoder.getVoltage() / encoder.getMaxVoltage() * 360;
//    }
//    public void setTurret(double power){
//            turret.setPower(power);
//    }
//    public double getEncoderTurret(){
//        return servoposition;
//    }

    //setter
    public void setPosServo(double position){
        servoL.setPosition(position);
        servoR.setPosition(position);
    }
    //getter
    public double getServoPos(){
        double position = (servoL.getPosition() + servoR.getPosition()) / 2;
        return position;
    }
    public void telemetryServo(Telemetry telemetry){
        telemetry.addData("Position telemetry - ", getServoPos());
    }
}
