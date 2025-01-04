package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.Slide;
import org.firstinspires.ftc.teamcode.tuning.TuningOpModes;

public final class Test extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d beginPose = new Pose2d(-54, 54.75, Math.toRadians(-90));
            PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
            waitForStart();
                Slide slide = new Slide(hardwareMap);
                Actions.runBlocking(
                        new ParallelAction(
                                slide.spinUp(),
                        drive.actionBuilder(beginPose)
                                .splineToConstantHeading(new Vector2d(0, 27), -Math.PI / 2)
                                .lineToY(40)
                                .splineToLinearHeading(new Pose2d(-54, 54.75, Math.PI/2), Math.PI/2)
                                .turn(Math.PI)
                                .build()
                        )
                );
        }
}
