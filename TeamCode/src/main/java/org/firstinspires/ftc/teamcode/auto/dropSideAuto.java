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
                                        .lineToY(-30.5)
                                        .build(),
                                outtakeSlide.spinUp()
                        ),
                        //hang
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
                        // drive to first pixel while closing the slide
                        new ParallelAction(
                            drive.actionBuilder(new Pose2d(-10, -30.5, Math.toRadians(-90)))
                                .splineToLinearHeading(new Pose2d(-48, -48, Math.toRadians(90)), Math.toRadians(90))
                                .build(),
                                outtakeSlide.spinDown()
                        ),
                        //pick up the element
                        inSlide.readyToPickElement(),
                        new SleepAction(1.3),
                        intake.closeClaw(),
                        new SleepAction(.8),
                        // trasnfer the element to outtake
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
                        // drive to drop pole while extending slide and getting arm ready
                        new ParallelAction(
                                outtakeClaw.armDrop(),
                                outtakeSlide.drop(),
                                drive.actionBuilder(new Pose2d(-48, -48, Math.toRadians(90)))
                                .splineToLinearHeading(new Pose2d(-53, -51, Math.toRadians(47)), Math.toRadians(47))
                                        .build()
                        ),
                        new SleepAction(1),
                        // drop the block
                        drive.actionBuilder(new Pose2d(-53, -51,Math.toRadians(47)))
                                .strafeTo(new Vector2d(-57,-55), new TranslationalVelConstraint(50))
                                .build(),
                        outtakeClaw.openClaw(),
                        new SleepAction(.3),
                        // drive to block 2
                        new ParallelAction(
                                outtakeClaw.armPick(),
                                outtakeSlide.spinDown(),
                                drive.actionBuilder(new Pose2d(-57, -55, Math.toRadians(47)))
                                        .splineToLinearHeading(new Pose2d(-58, -47, Math.toRadians(90)), Math.toRadians(90))
                                        .build(),
                                inSlide.readyToPickElement()
                        ),
                        //pick up the block 2
//                        new SleepAction(1.3),
                        intake.closeClaw(),
                        new SleepAction(.8),
                        // trasnfer the block 2 to outtake
                        new ParallelAction(
                                inSlide.transferElement(),
                                outtakeClaw.openClaw(),
                                outtakeClaw.armPick()
                        ),
                        inSlide.closeSlide(),
                        new SleepAction(2),
                        outtakeClaw.closeClaw(),
                        new SleepAction(1),
                        intake.openClaw(),
                        new SleepAction(.2),
                        // drive to drop pole while extending slide and getting arm ready for block 2
                        new ParallelAction(
                                outtakeClaw.armDrop(),
                                outtakeSlide.drop(),
                                drive.actionBuilder(new Pose2d(-58, -47, Math.toRadians(90)))
                                        .splineToLinearHeading(new Pose2d(-53, -51, Math.toRadians(47)), Math.toRadians(47))
                                        .build()
                        ),
                        new SleepAction(1),
                        // drop the block
                        drive.actionBuilder(new Pose2d(-53, -51,Math.toRadians(47)))
                                .strafeTo(new Vector2d(-57,-55), new TranslationalVelConstraint(50))
                                .build(),
                        outtakeClaw.openClaw(),
                        new SleepAction(.8),
                        drive.actionBuilder(new Pose2d(-57, -55,Math.toRadians(47)))
                        .splineToLinearHeading(new Pose2d(-24, -12, Math.toRadians(-180)), Math.toRadians(0))
                                .build(),
                        outtakeClaw.armPick(),
                        outtakeSlide.spinDown(),
                        new SleepAction(1)
                )
        );
    }
}
