package org.firstinspires.ftc.teamcode.auto;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Lift;
import org.firstinspires.ftc.teamcode.robot.Slide;
import org.firstinspires.ftc.teamcode.robot.inExL;

@Config
@Autonomous(name = "dropSideAuto", group = "Autonomous")
public class dropSideAuto extends LinearOpMode {
    @Override
    public void runOpMode() {
        Pose2d beginPose = new Pose2d(-9, -62, Math.toRadians(-90));
            MecanumDrive drive = new MecanumDrive(hardwareMap, beginPose);
            waitForStart();
                Slide slide = new Slide(hardwareMap);
                inExL inExL = new inExL(hardwareMap);
                Actions.runBlocking(
                        new ParallelAction(
                          slide.spinUp(),
                          inExL.extendOut(),
                            drive.actionBuilder(beginPose)
                                    .lineToY(-34)
                                    .waitSeconds(1)
//                        .strafeTo(new Vector2d(-48,-40))
//                        .turn(Math.toRadians(180))
                                    .splineToLinearHeading(new Pose2d(-48, -40, Math.toRadians(90)), Math.toRadians(90))
                                    .waitSeconds(2)
//                        .strafeTo(new Vector2d(-56,-54))
//                        .turn(Math.toRadians(-47))
                                    .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
                                    .waitSeconds(1)
//                        .turn(Math.toRadians(47))
//                        .strafeTo(new Vector2d(-58,-38))
                                    .splineToLinearHeading(new Pose2d(-58, -38, Math.toRadians(90)), Math.toRadians(90))
                                    .waitSeconds(2)
                                    .strafeTo(new Vector2d(-56,-38))
//                        .turn(Math.toRadians(-47))
                                    .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
                                    .waitSeconds(1)
//                .turn(Math.toRadians(47))
//                .strafeTo(new Vector2d(-52,-25))
//                .turn(Math.toRadians(90))
//                                    .splineToLinearHeading(new Pose2d(-53, -25, Math.toRadians(180)), Math.toRadians(180))
//                                    .waitSeconds(3)
////                .turn(Math.toRadians(-90))
////                .strafeTo(new Vector2d(-56,-54))
////                .turn(Math.toRadians(-47))
//                                    .strafeTo(new Vector2d(-49,-25))
//                                    .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
//                                    .waitSeconds(2)
                                    .splineToLinearHeading(new Pose2d(-30, -12, Math.toRadians(0)), Math.toRadians(0))
//                .strafeTo(new Vector2d(-30,-16))
                                    .build()
                )
        );
    }
}
