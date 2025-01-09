package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.HangClaw;
import org.firstinspires.ftc.teamcode.robot.IntakeSlide;
import org.firstinspires.ftc.teamcode.robot.OuttakeSlide;

public final class HangingAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final double pi = Math.PI;
        Pose2d beginPose = new Pose2d(10, -62, Math.toRadians(-90));
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        OuttakeSlide outtakeSlide = new OuttakeSlide(hardwareMap);
        HangClaw hangClaw = new HangClaw(hardwareMap);
        IntakeSlide inSlide = new IntakeSlide(hardwareMap);

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        hangClaw.closeClaw(),
                        inSlide.closeSlide(),
//                        hangClaw.closeClaw(),
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
                        // go back a little bit
                        drive.actionBuilder(new Pose2d(10, -31.5, Math.toRadians(-90)))
                                .strafeTo(new Vector2d(10,-33))
                                .build(),
                        new ParallelAction(
                                // Close the slide
                                new SequentialAction(
                                        // close the slide
                                        outtakeSlide.spinDown(),
                                        outtakeSlide.powerDown()),
                                drive.actionBuilder(new Pose2d(10, -33, Math.toRadians(-90)))
                                 //Drive to First Block
                                        .splineToLinearHeading(new Pose2d(31, -17, pi/2), pi/2)
                                .strafeTo(new Vector2d(42, -12))
//                                // Push Block 1
                                .   strafeTo(new Vector2d(45, -51)) // 54.5
//                                // Go back, Spline Turn, and go to block 2
                                    .strafeTo(new Vector2d(45, -12))
                                    .strafeTo(new Vector2d(55, -12))
//                                // Push Block 2
                                    .strafeTo(new Vector2d(45, -51)) // 54.5
//                                // Go back, and go pick up Block 1
                                    .strafeTo(new Vector2d(34, -47))
                                    .strafeTo(new Vector2d(33.5, -63), new TranslationalVelConstraint(50))
                                .build()
                        ),
                        hangClaw.closeClaw(),
//                        new SleepAction(1),
                        // go to submersible to hang block 1
                        outtakeSlide.spinUp(),
                                drive.actionBuilder(new Pose2d(33.5,-63,Math.toRadians(-90)))
                                        .strafeTo(new Vector2d(33.5,-60))
                                        .splineToLinearHeading(new Pose2d(6, -29.5, -pi/2), pi/2)
                                        .build(),
                        new SleepAction(0.1),
                        // hang block 1
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
                        // go back to pick up block 2
                        new ParallelAction(
                                outtakeSlide.spinDown(),
                                drive.actionBuilder(new Pose2d(7,-29.5,Math.toRadians(-90)))
                                        .splineToLinearHeading(new Pose2d(30, -57, pi/2), 180)
                                        .strafeTo(new Vector2d(32,-63), new TranslationalVelConstraint(50))
                                        .build()
                        ),
                        //pick up block 2
                        hangClaw.closeClaw(),
                        outtakeSlide.spinUp(),
                        // go to submersible to hang block 2
                        drive.actionBuilder(new Pose2d(32,-63,Math.toRadians(-90)))
                                .strafeTo(new Vector2d(33.5,-60))
                                .splineToLinearHeading(new Pose2d(3, -29.5, -pi/2), pi/2)
                                .build(),
                        new SleepAction(0.1),
                        // hang block 2
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
//                        new SleepAction(0.5),
                        // go for parking
                        new ParallelAction(
                                outtakeSlide.spinDown(),
                                drive.actionBuilder(new Pose2d(3,-29.5,Math.toRadians(-90)))
                                        .splineToLinearHeading(new Pose2d(33, -59, pi/2), 180)
                                        .build()
                        ),
                        outtakeSlide.powerDown()
                )
        );
    }
}
