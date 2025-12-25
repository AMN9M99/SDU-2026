package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Controllers.TurretController;

@TeleOp(name = "Turret World Lock Test", group = "Tests")
public class TurretTsetends OpMode {

    // 1. Создаем экземпляры контроллеров
    private TurretController turretController = new TurretController();
    private GoBildaPinpointDriver pinpoint;

    // 2. Переменная для хранения "заблокированного" мирового угла
    private double lockedWorldAngle = 0.0;

    /**
     * Выполняется один раз при нажатии INIT
     */
    @Override
    public void init() {
        // Инициализация турели и Pinpoint
        turretController.init(hardwareMap);
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.initialize();
        pinpoint.resetDeviceConfigurationForOpMode();
        pinpoint.recalibrateIMU();

        telemetry.addLine("Ready! Place the robot and aim the turret.");
        telemetry.addLine("Наведите турель и нажмите Start, чтобы заблокировать направление.");
        telemetry.update();
    }

    /**
     * Выполняется ОДИН РАЗ сразу после нажатия START
     */
    @Override
    public void start() {
        // --- 3. "ЗАПОМИНАЕМ" НАПРАВЛЕНИЕ ---
        // Считываем текущий курс робота и текущий угол турели в момент старта
        pinpoint.update(GoBildaPinpointDriver.ReadData.ONLY_UPDATE_HEADING);
        double initialRobotHeading = pinpoint.getHeading(AngleUnit.DEGREES);
        double initialTurretAngle = turretController.getCurrentAngle();

        // Мировой угол = курс робота + угол турели
        // Это направление в пространстве, которое мы будем удерживать
        lockedWorldAngle = initialRobotHeading + initialTurretAngle;

        telemetry.addData("Direction Locked! World Angle", "%.2f°", lockedWorldAngle);
        telemetry.update();
    }

    /**
     * Выполняется в цикле после нажатия START
     */
    @Override
    public void loop() {
        // --- 4. ПОЛУЧАЕМ СВЕЖИЕ ДАННЫЕ О КУРСЕ ---
        pinpoint.update(GoBildaPinpointDriver.ReadData.ONLY_UPDATE_HEADING);
        double currentRobotHeading = pinpoint.getHeading(AngleUnit.DEGREES);

        // --- 5. ВЫЧИСЛЯЕМ НОВЫЙ ЦЕЛЕВОЙ УГОЛ ДЛЯ ТУРЕЛИ ---
        // Цель турели = (Мировой угол, который мы держим) - (Текущий курс робота)
        double targetTurretAngle = lockedWorldAngle - currentRobotHeading;

        // --- 6. ОТДАЕМ КОМАНДУ КОНТРОЛЛЕРУ ---
        // Говорим турели двигаться к этому новому целевому углу
        turretController.update(targetTurretAngle, 0.8); // Скорость 80%

        // --- 7. ВЫВОДИМ ТЕЛЕМЕТРИЮ ---
        telemetry.addLine(">>> TURRET WORLD LOCK TEST <<<");
        telemetry.addData("Locked World Angle", "%.2f°", lockedWorldAngle);
        telemetry.addLine();

        // Вызываем телеметрию из нашего контроллера, чтобы видеть его состояние
        turretController.showTelemetry(telemetry);

        telemetry.update();
    }
}
