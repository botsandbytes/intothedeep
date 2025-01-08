package org.firstinspires.ftc.teamcode.tests;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp
public class servoTest extends LinearOpMode {
    public static double position = 0;
    public static String servoName = "intClaw";

    // .58 open for hangClaw
    // .75 close for hangClaw

    @Override

    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            Servo Servo = hardwareMap.get( Servo.class, servoName);
            Servo.setPosition(position);
    }}}