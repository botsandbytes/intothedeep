package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.HangClaw;
import org.firstinspires.ftc.teamcode.robot.Intake;
import org.firstinspires.ftc.teamcode.robot.IntakeSlide;
import org.firstinspires.ftc.teamcode.robot.OuttakeClaw;
import org.firstinspires.ftc.teamcode.robot.OuttakeSlide;

@Autonomous(name="Drop", group = "Auto")
public final class dropSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final double pi = Math.PI;
        Pose2d beginPose = new Pose2d(-10, -62, Math.toRadians(-90));
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        OuttakeSlide outtakeSlide = new OuttakeSlide(hardwareMap);
        IntakeSlide inSlide = new IntakeSlide(hardwareMap);
        HangClaw hangClaw = new HangClaw(hardwareMap);
        OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
        Intake intake = new Intake(hardwareMap);

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        hangClaw.closeClaw(),
                        inSlide.closeSlide(),
                        // go to submersible
                        new ParallelAction(
                                drive.actionBuilder(beginPose)
                                        .lineToY(-31.5)
                                        .build(),
                                outtakeSlide.spinUp()
                        ),
                        //hang
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
                        drive.actionBuilder(new Pose2d(-10, -31.5, Math.toRadians(-90)))
                                .splineToLinearHeading(new Pose2d(-44, -40, Math.toRadians(90)), Math.toRadians(90))
//                                .waitSeconds(2)
////                                .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
////                                .waitSeconds(1)
////                                .splineToLinearHeading(new Pose2d(-58, -38, Math.toRadians(90)), Math.toRadians(90))
////                                .waitSeconds(2)
////                                .strafeTo(new Vector2d(-56,-38))
////                                .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
////                                .waitSeconds(1)
////                                .splineToLinearHeading(new Pose2d(-30, -12, Math.toRadians(0)), Math.toRadians(0))
                                .build(),
//                        new ParallelAction(
//                                inSlide.closeSlide(),
//                                intake.openClaw(),
//                                intake.lowerarm()
//                        ),
                        inSlide.readyToPickElement(),
                        new SleepAction(1.3),
                        intake.closeClaw(),
                        new SleepAction(.8),
                        new ParallelAction(
                                inSlide.transferElement(),
                                outtakeClaw.openClaw(),
                                outtakeClaw.armPick()
                        ),
                        new SleepAction(2),
                        outtakeClaw.closeClaw(),
                        new SleepAction(1),
                        intake.openClaw(),
                        new SleepAction(.2),
                        new ParallelAction(
                                outtakeClaw.armDrop(),
                                outtakeSlide.drop(),
                                drive.actionBuilder(new Pose2d(-44, -40, Math.toRadians(90)))
                                .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
                                        .build()
                        ),
//                        new SleepAction(2),
//                        outtakeClaw.closeClaw(),
//                        intake.openClaw(),
                        new SleepAction(2),
                        outtakeClaw.openClaw()
                )
        );
    }
}
