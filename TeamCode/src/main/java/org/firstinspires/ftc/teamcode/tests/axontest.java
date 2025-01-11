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
    public static String leftServoName = "extL";
    public static String rightServoName = "extR";

    // .58 open for hangClaw
    // .75 close for hangClaw

    @Override

    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();
        Servo leftServo = hardwareMap.get( Servo.class, leftServoName);
        Servo rightServo = hardwareMap.get( Servo.class, rightServoName);

        rightServo.setDirection(Servo.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            leftServo.setPosition(position);
            rightServo.setPosition(position);
        }}}