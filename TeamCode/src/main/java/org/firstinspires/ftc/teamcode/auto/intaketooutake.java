package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.OuttakeSlide;
import org.firstinspires.ftc.teamcode.robot.Intake;
import org.firstinspires.ftc.teamcode.robot.IntakeSlide;
import org.firstinspires.ftc.teamcode.robot.Outake;

@TeleOp
public final class intaketooutake extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final double pi = Math.PI;
        Pose2d beginPose = new Pose2d(0, 0, 0);
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        waitForStart();
        OuttakeSlide outtakeSlide = new OuttakeSlide(hardwareMap);
//        HangClaw hangClaw = new HangClaw(hardwareMap);
        IntakeSlide intakeslide = new IntakeSlide(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Outake outake = new Outake(hardwareMap);
        Actions.runBlocking(
                new SequentialAction(
                        intakeslide.ExpandSlide(),
                        intake.lowerarm(),
                        intake.openClaw(),
                        new SleepAction(3),
                        intake.closeClaw(),
                        intake.raisearm(),
                        intakeslide.closeSlide(),
                        // TODO: align to MeepMeep field
                        drive.actionBuilder(beginPose).strafeTo(new Vector2d(-18, 0)).strafeToLinearHeading(new Vector2d(-34.3, -45.8), Math.toRadians(47)).build(),
                        outake.armlower(),
                        outake.openClaw(),
                        outake.closeClaw(),
                        outtakeSlide.drop(),
                        outake.armlower(),
                        outake.openClaw()
                )
        );
    }
}
