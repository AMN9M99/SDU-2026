package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Controllers.ColorSensor;

@TeleOp
public class ColorSensorTest extends OpMode {

    ColorSensor colorSensor  =new ColorSensor();

    @Override
    public void init() {
        colorSensor.init(hardwareMap);
    }

    @Override
    public void loop() {
        colorSensor.getDetectedColor(telemetry);
    }
}
