package org.firstinspires.ftc.teamcode.tests;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
public class axontest extends LinearOpMode {
    public static double position = 0;
    public static String servoName = "extL";
    public static String servoName2 = "extR";

    // .58 open for hangClaw
    // .75 close for hangClaw

    @Override

    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();
        Servo Servo = hardwareMap.get( Servo.class, servoName);
        Servo Servo2 = hardwareMap.get( Servo.class, servoName2);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            Servo.setPosition(position);
            Servo2.setPosition(1-position);
        }}}