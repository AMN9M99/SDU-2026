package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeController {
    private DcMotor intakeMotor,secIntakeMotor;

    public void init(HardwareMap hw,String intakeMotorName,String secIntakeMotorName){
        intakeMotor = hw.get(DcMotor.class, intakeMotorName);
        secIntakeMotor = hw.get(DcMotor.class,secIntakeMotorName);

        intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        secIntakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        intakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        secIntakeMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setIntakePower(double intakePower){
        intakeMotor.setPower(intakePower);
    }
    public void setSecIntakeMotor(double power){
        secIntakeMotor.setPower(power);
    }
}
