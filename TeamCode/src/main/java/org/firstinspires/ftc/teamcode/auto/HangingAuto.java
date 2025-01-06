package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.Slide;

public final class HangingAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final double pi = Math.PI;
        Pose2d beginPose = new Pose2d(9, -62, Math.toRadians(-90));
            PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
            waitForStart();
                Slide slide = new Slide(hardwareMap);
                Actions.runBlocking(
                        drive.actionBuilder(beginPose)
                                // Drive to Submarine
                                .lineToY(-34)
                                // Drive to First Block
                                .strafeTo(new Vector2d(34, -34))
                                .strafeTo(new Vector2d(34,-8.75))
                                .strafeTo(new Vector2d(45, -8.75))
//                .splineToConstantHeading(new Vector2d(45, -8.75), pi/2)
                                // Push Block 1
                                .strafeTo(new Vector2d(45, -54.5))
                                // Go back, Spline Turn, and go to block 2
                                .strafeTo(new Vector2d(45, -48))
                                .splineToLinearHeading(new Pose2d(45, -8.75, pi/2), pi/2)
                                .strafeTo(new Vector2d(54, -8.75))
                                // Push Block 2
                                .strafeTo(new Vector2d(54, -54.5))
                                // Go back, and go pick up Block 1
                                .strafeTo(new Vector2d(34, -40))
                                .strafeTo(new Vector2d(34, -61.5), new TranslationalVelConstraint(50))

                                .lineToY(-55)
                                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
                                .waitSeconds(0)
                                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
                                .lineToY(-61.5, new TranslationalVelConstraint(30))
                                .lineToY(-55)
                                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
                                .waitSeconds(0)
                                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
                                .lineToY(-61.5, new TranslationalVelConstraint(30))
                                .build());
        }
}
