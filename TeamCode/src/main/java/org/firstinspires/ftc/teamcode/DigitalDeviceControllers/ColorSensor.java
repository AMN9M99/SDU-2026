package org.firstinspires.ftc.teamcode.DigitalDeviceControllers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ColorSensor {
    NormalizedColorSensor colorSensor;

    public enum DetectedColor {
        Green,
        Purple,
        Unknown
    }

    public void init(HardwareMap hardwareMap) {
        colorSensor = hardwareMap.get(NormalizedColorSensor.class,"colorSensor");
    }

    public DetectedColor getDetectedColor(Telemetry telemetry) {
        NormalizedRGBA colors = colorSensor.getNormalizedColors(); //return 4 values

        float normGreen, normRed, normBlue;
        normRed = colors.red / colors.alpha;
        normBlue = colors.blue / colors.alpha;
        normGreen = colors.green / colors.alpha;

        telemetry.addData("red",normRed);
        telemetry.addData("blue",normBlue);
        telemetry.addData("green",normGreen);

        // TODO ADD if statementes for specific colors added

        return DetectedColor .Unknown;


    }
}
