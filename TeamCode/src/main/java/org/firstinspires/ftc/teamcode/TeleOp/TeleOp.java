package org.firstinspires.ftc.teamcode.TeleOp;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.HangClaw;
import org.firstinspires.ftc.teamcode.robot.Intake;
import org.firstinspires.ftc.teamcode.robot.IntakeSlide;
import org.firstinspires.ftc.teamcode.robot.OuttakeClaw;
import org.firstinspires.ftc.teamcode.robot.OuttakeSlide;
import org.firstinspires.ftc.teamcode.robot.mecanum;
import org.firstinspires.ftc.teamcode.utils.ActionUpdater;

import java.util.ArrayList;
import java.util.List;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public class TeleOp extends OpMode {

    private FtcDashboard dash = FtcDashboard.getInstance();
    private List<Action> runningActions = new ArrayList<>();
    final double pi = Math.PI;
    Pose2d beginPose = new Pose2d(-10, -62, Math.toRadians(-90));
    PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
    OuttakeSlide outtakeSlide = new OuttakeSlide(hardwareMap);
    IntakeSlide inSlide = new IntakeSlide(hardwareMap);
    HangClaw hangClaw = new HangClaw(hardwareMap);
    OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
    Intake intake = new Intake(hardwareMap);
    mecanum drivetrain = new mecanum(hardwareMap, gamepad1);

    int outslide = 0;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }
    @Override
    public void loop() {
        drivetrain.drive();
        TelemetryPacket packet = new TelemetryPacket();
        runningActions = ActionUpdater.updateRunningActions(runningActions, packet);
        dash.sendTelemetryPacket(packet);
        if (gamepad1.dpad_up) {
            switch (outslide) {
                case 0:
                    outtakeSlide.spinUp();
                    outslide = 1;
                    break;
                case 1:
                    outtakeSlide.drop();
                    outslide = 2;
                    break;
                case 2:
                    break;
            }
        }

        if (gamepad1.dpad_down) {
            outslide = 0;
            outtakeSlide.spinDown();
        }
    }
}