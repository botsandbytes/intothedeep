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
        Pose2d beginPose = new Pose2d(-54, 0, Math.toRadians(-180));
            PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
            waitForStart();
                Slide slide = new Slide(hardwareMap);
                Actions.runBlocking(
                        new ParallelAction(
//                                slide.spinUp(),
                        drive.actionBuilder(beginPose)
//                                .lineToY(10)
//                                .splineToConstantHeading(new Vector2d(-70, 0), 0)
                                .lineToX(-24)
                                .waitSeconds(1)
                                .lineToX(-27)
                                .turn(-Math.PI/2)
                                .lineToY(-27)
                                .turn(-Math.PI/2)
                                .lineToX(-5)
                                .strafeTo(new Vector2d(-5,-36))
                                .setReversed(true)
                                .lineToX(-45)
                                .lineToX(-5)
                                .strafeTo(new Vector2d(-5,-45))
                                .setReversed(true)
                                .lineToX(-45)
                                .lineToX(-5)
                                .strafeTo(new Vector2d(-5,-54))
                                .setReversed(true)
                                .lineToX(-45)
                                .lineToX(-5)
//                                .setReversed(true)
//                                .lineToY(-24)

//                                .lineToY(-30)

//                                .splineToLinearHeading(new Pose2d(-45, 45, 0), 0)
//                                .turn(Math.PI)
                                .build()
                        )
                );
        }
}
