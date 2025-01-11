package org.firstinspires.ftc.teamcode.TeleOp;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
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
import org.firstinspires.ftc.teamcode.robot.Intake;
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
        IntakeSlide inSlide = new IntakeSlide(hardwareMap);
        waitForStart();
        inSlide.closeSlide();
        Actions.runBlocking(
                new ParallelAction(
                        mecanum.drivetrain(),
                        teleopControls()
                )
        );
    }

    public class TeleopControl implements Action {
        final double pi = Math.PI;
        OuttakeSlide outSlide = new OuttakeSlide(hardwareMap);
        OuttakeClaw outtake = new OuttakeClaw(hardwareMap);
        HangClaw hangClaw = new HangClaw(hardwareMap);
        IntakeSlide inSlide = new IntakeSlide(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Pose2d beginPose = new Pose2d(33, -59, pi/2);
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);


        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            // GAMEPAD 1 Controls

            // Intake Claw Controls
            if (gamepad1.a) {
                intake.lowerarm().run(packet);
            }
            if (gamepad1.x) {
                intake.openClaw().run(packet);

            }
            if (gamepad1.b) {
                intake.closeClaw().run(packet);
            }
            if (gamepad1.y) {
                intake.armMid().run(packet);
            }
//
//            if (gamepad1.right_bumper) {
//                intake.raisearm().run(packet);
//            }

            //pick up the element and drive and hang element
            if (gamepad1.right_bumper) {
                Actions.runBlocking(
                        new SequentialAction(
                                drive.actionBuilder(new Pose2d(33, -59, Math.toRadians(90)))
                                    .strafeTo(new Vector2d(32,-62.5), new TranslationalVelConstraint(50))
                                            .build(),

                            //pick up block
                            hangClaw.closeClaw(),
                            outSlide.spinUp(),
                            // go to submersible to hang block
                            drive.actionBuilder(new Pose2d(32, -62.5, Math.toRadians(-90)))
                                .strafeTo(new Vector2d(34, -60))
                                .splineToLinearHeading(new Pose2d(4, -27, -pi / 2), pi / 2)
                                .build(),
                            new SleepAction(0.1),
                            // hang block 2
                            outSlide.hang()
//                            hangClaw.openClaw()
                        )
                );
            }

            if (gamepad1.left_bumper) {
                Actions.runBlocking(
                        new SequentialAction(
                                hangClaw.openClaw(),
                                // go back to pick up block 2
                                new ParallelAction(
                                        outSlide.spinDown(),
                                        drive.actionBuilder(new Pose2d(4,-27,Math.toRadians(-90)))
                                                .splineToLinearHeading(new Pose2d(30, -57, pi/2), 180)
                                                .strafeTo(new Vector2d(33,-62.5), new TranslationalVelConstraint(50))
                                                .build()
                                )
                        )
                );
            }

            // GAMEPAD 2 Controls


            // Hang Claw Controls

            if (gamepad2.a) {
                hangClaw.openClaw().run(packet);
            }
            if (gamepad2.x) {

            }

            //Hang speciment and spin down
            if (gamepad2.right_bumper) {
                Actions.runBlocking(
                        new SequentialAction(
                            outSlide.hang(),
                            hangClaw.openClaw(),
                            outSlide.spinDown(),
                            outSlide.powerDown()
                        )
                );
            }

            // grab the specimen and slide up
            if (gamepad2.left_bumper) {
                Actions.runBlocking(
                        new SequentialAction(
                            hangClaw.closeClaw(),
                            outSlide.spinUp()
                        )
                );
            }

            //Drop controls

            //transfer the specimen
            if (gamepad2.left_trigger > 0) {
                Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                outtake.armPick(),
                                outtake.openClaw(),
                                intake.raisearm(),
                                inSlide.closeSlide()
                            ),
                        outtake.closeClaw(),
                        intake.openClaw(),
                        outtake.armDrop(),
                        outSlide.drop()
                    )
                );
            }

            // drop speciment and close slide
            if (gamepad2.right_trigger > 0) {
                Actions.runBlocking(
                    new SequentialAction(
                        outtake.openClaw(),
                        outtake.armPick(),
                        outSlide.spinDown(),
                        outSlide.powerDown()
                    )
                );
            }

            // INTAKE SLIDE Controls
            if (gamepad2.dpad_up) {
                inSlide.expandSlow().run(packet);
            }
            if (gamepad1.dpad_down) {
                inSlide.closeSlow().run(packet);
            }
            if (gamepad2.dpad_right) {
                inSlide.ExpandSlide().run(packet);
            }
            if (gamepad2.dpad_left) {
                inSlide.closeSlide().run(packet);
            }

            return true;
        }
    }

    public Action teleopControls() {
        return new TeleopControl();
    }
}
