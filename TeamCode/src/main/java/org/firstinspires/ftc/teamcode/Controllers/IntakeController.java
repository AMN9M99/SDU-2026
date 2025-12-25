package org.firstinspires.ftc.teamcode.Controllers;


import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeController {
    private double OPENBOSS = 0.35;
    private double CLOSEBOSS = 0.365;
    private  double OPENMINI = 0.4;
    private double CLOSEMINI = 0.23;
    public static IntakeState IntakeState;
    private DcMotor intakeOne;
    private Servo intakeTwoBoss, intakeTwoMini;

    public void initMotor(HardwareMap hwmap) {
        intakeOne = hwmap.get(DcMotor.class, "intake_1");

        intakeOne.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void initServo(HardwareMap hwmap){
        intakeTwoBoss = hwmap.get(Servo.class,"intakeTwo");
        intakeTwoMini = hwmap.get(Servo.class,"stoper");
    }

    public void setPoseOpenBoss(){
        intakeTwoBoss.setPosition(OPENBOSS);
    }
    public void setPoseOpenMini(){
        intakeTwoMini.setPosition(OPENMINI);
    }
    public void setCloseBoss(){
        intakeTwoBoss.setPosition(CLOSEBOSS);
    }
    public void setCloseMini(){
        intakeTwoMini.setPosition(CLOSEMINI);
    }

    public enum IntakeState {
        NULL,
        SHOOT,
        COLLECTION,
        OUTTAKE,
        RESET,
        STOP;

        @Override
        public String toString() {
            switch (this) {
                case STOP:
                    return "🛑 Stopped";
                case COLLECTION:
                    return "📥 Collecting";
                case SHOOT:
                    return "🎯 Shooting";
                case RESET:
                    return "🔄 Resetting";
                case OUTTAKE:
                    return "⏪ Outtake";
                default:
                    return "Null";

            }


        }
    }

    public void setIntakeOne(double power) {
        intakeOne.setPower(power);
    }

}
//getter



