package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class AllControllers {
    public ServoPos pos = new ServoPos();
    public BaseController drive = new BaseController();
    public ShooterControllerPIDVSA shoot = new ShooterControllerPIDVSA();
    public IntakeController suck = new IntakeController();
    public ModeController mode = new ModeController();

public AllControllers(HardwareMap hardwareMap){
suck.init(hardwareMap,"intake_1","intake_2");
shoot.initialize(hardwareMap,"shooter_l","shooter_r");
pos.initServo(hardwareMap,"l_angle","r_angle");
mode.init(hardwareMap,"shooter_l","shooter_r","l_angle","r_angle");
}
}
