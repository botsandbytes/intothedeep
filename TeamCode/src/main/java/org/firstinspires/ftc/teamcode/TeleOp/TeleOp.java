package org.firstinspires.ftc.teamcode.TeleOp;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.HangClaw;
import org.firstinspires.ftc.teamcode.robot.IntakeSlide;
import org.firstinspires.ftc.teamcode.robot.OuttakeClaw;
import org.firstinspires.ftc.teamcode.robot.OuttakeSlide;
import org.firstinspires.ftc.teamcode.robot.mecanumTeleop;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp
public final class TeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(0, 0, Math.toRadians(0));
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        mecanumTeleop mecanum = new mecanumTeleop(hardwareMap, gamepad1);
        waitForStart();

        Actions.runBlocking(
                new ParallelAction(
                        mecanum.drivetrain(),
                        teleop()
                )
        );
    }

    public class Teleop implements Action {
OuttakeSlide outtakeSlide = new OuttakeSlide(hardwareMap);
    OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
    HangClaw hangClaw = new HangClaw(hardwareMap);
    IntakeSlide inSlide = new IntakeSlide(hardwareMap);

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (gamepad1.a) {
                outtakeClaw.closeClaw().run(packet);
            } else if (gamepad1.b) {
                outtakeClaw.openClaw().run(packet);
            }
            return true;
        }
    }

    public Action teleop() {
        return new Teleop();
    }
}
