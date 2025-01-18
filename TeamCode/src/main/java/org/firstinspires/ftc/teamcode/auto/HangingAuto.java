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
import org.firstinspires.ftc.teamcode.robot.IntakeSlide;
import org.firstinspires.ftc.teamcode.robot.OuttakeSlide;

@Autonomous(name="Hang", group = "Auto")
public final class HangingAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final double pi = Math.PI;
        Pose2d beginPose = new Pose2d(11, -62, Math.toRadians(-90));
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
        OuttakeSlide outtakeSlide = new OuttakeSlide(hardwareMap);
        HangClaw hangClaw = new HangClaw(hardwareMap);
        IntakeSlide inSlide = new IntakeSlide(hardwareMap);

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        hangClaw.closeClaw(),
                        inSlide.closeSlide(),
                        // go to submersible
                        new ParallelAction(
                                outtakeSlide.spinUp(),
                                drive.actionBuilder(beginPose)
                                        .lineToY(-32.5)
                                        .build()

                        ),
                        //hang the first specimen
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
                        // go back a little bit
                        drive.actionBuilder(new Pose2d(11, -32.5, Math.toRadians(-90)))
                                .strafeTo(new Vector2d(11,-33))
                                .build(),
                        new ParallelAction(
                                // Close the slide
                                new SequentialAction(
                                        // close the slide
                                        outtakeSlide.spinDown(),
                                        outtakeSlide.powerDown()),
                                drive.actionBuilder(new Pose2d(11, -33, Math.toRadians(-90)))
                                 //Drive to First Block
                                        .splineToLinearHeading(new Pose2d(32, -15, pi/2), pi/2)
                                .strafeTo(new Vector2d(42, -12))
//                                // Push Block 1
                                .   strafeTo(new Vector2d(45, -50)) // 54.5
//                                // Go back, Spline Turn, and go to block 2
//                                        .lineToY(-12)
                                    .strafeTo(new Vector2d(40, -12))
                                    .strafeTo(new Vector2d(52, -12))
//                                // Push Block 2
                                    .strafeTo(new Vector2d(50, -50)) // 54.5
//                                // Go back, and go pick up Block 1
                                    .strafeTo(new Vector2d(35, -52))
                                    .strafeTo(new Vector2d(35, -60), new TranslationalVelConstraint(30))
                                .build()
                        ),
                        hangClaw.closeClaw(),
//                        new SleepAction(0.2),
//                        new SleepAction(1),
                        // go to submersible to hang specimen 2
                        new SleepAction(0.2),
                        new ParallelAction(
                            outtakeSlide.spinUp(),
                                drive.actionBuilder(new Pose2d(35,-60,Math.toRadians(-90)))
                                        .strafeTo(new Vector2d(35,-53))
                                        .splineToLinearHeading(new Pose2d(9, -32.5, -pi/2), pi/2)
//                                        .strafeTo(new Vector2d(6, -29), new TranslationalVelConstraint(40))
                                        .build()
                        ),
//                        new SleepAction(0.3),
                        // hang speciment 2
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
                        // go back to pick up specimen 3
                        new ParallelAction(
                                outtakeSlide.spinDown(),
                                drive.actionBuilder(new Pose2d(9,-32.5,Math.toRadians(-90)))
                                        .splineToLinearHeading(new Pose2d(32, -55, pi/2), 180)
                                        .strafeTo(new Vector2d(32,-60), new TranslationalVelConstraint(30))
                                        .build()
                        ),
//                        new SleepAction(0.5),
                        //pick up specimen 3
                        hangClaw.closeClaw(),
                        new SleepAction(0.2),
                        new ParallelAction(
                            outtakeSlide.spinUp(),
                            // go to submersible to hang specimen 3
                            drive.actionBuilder(new Pose2d(32,-60,Math.toRadians(-90)))
                                .strafeTo(new Vector2d(32,-53))
                                .splineToLinearHeading(new Pose2d(8, -32.5, -pi/2), pi/2)
                                .build()
                        ),
//                        new SleepAction(0.3),
                        // hang specimen 3
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
//                        new SleepAction(0.5),
                        // go for speciment 4
                        new ParallelAction(
                                outtakeSlide.spinDown(),
                                drive.actionBuilder(new Pose2d(8,-32.5,Math.toRadians(-90)))
                                        .splineToLinearHeading(new Pose2d(32, -55, pi/2), 180)
                                        .strafeTo(new Vector2d(32,-60), new TranslationalVelConstraint(30))
                                        .build()
                        ),
                        hangClaw.closeClaw(),
                        new SleepAction(0.2),
                        new ParallelAction(
                                outtakeSlide.spinUp(),
                                // go to submersible to hang specimen 4
                                drive.actionBuilder(new Pose2d(32,-60,Math.toRadians(-90)))
                                        .strafeTo(new Vector2d(32,-53))
                                        .splineToLinearHeading(new Pose2d(6, -32.5, -pi/2), pi/2)
                                        .build()
                        ),
                        // hang specimen 4
                        outtakeSlide.hang(),
                        hangClaw.openClaw(),
                        // go for parking
                        new ParallelAction(
                                outtakeSlide.spinDown(),
                                drive.actionBuilder(new Pose2d(6,-32.5,Math.toRadians(-90)))
//                                        .splineToLinearHeading(new Pose2d(32, -15, pi/2), pi/2)
                                        .splineToLinearHeading(new Pose2d(32, -57, pi/2), 180)
//                                        .strafeTo(new Vector2d(32,-60), new TranslationalVelConstraint(30))
                                        .build()
                        )
                )
        );
    }
}
