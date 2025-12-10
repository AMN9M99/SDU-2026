package org.firstinspires.ftc.teamcode.Controllers;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class GoToShoot {
    private Servo podacha1;
//    podacha2,podacha3;

    double posStart = 0.5;
    double posFinish = 1;

    boolean toFinish = true;

    public void init(HardwareMap hw){
        podacha1 = hw.get(Servo.class,"podacha_1");
//        podacha2 = hw.get(Servo.class,"podacha_2");
//        podacha3 = hw.get(Servo.class,"podacha_3");
    }

    public void work(){
        if (toFinish) {
            podacha1.setPosition(posFinish);
            toFinish = false;
        }else {
            podacha1.setPosition(posStart);
            toFinish = true;
        }
    }
}
