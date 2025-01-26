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

        int hangCount = 0;
        final double pi = Math.PI;
        OuttakeSlide outSlide = new OuttakeSlide(hardwareMap);
        OuttakeClaw outtake = new OuttakeClaw(hardwareMap);
        HangClaw hangClaw = new HangClaw(hardwareMap);
        IntakeSlide inSlide = new IntakeSlide(hardwareMap);
        Intake intake = new Intake(hardwareMap);
        Pose2d beginPose = new Pose2d(6, -33, -pi/2);
        PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);

        Pose2d specimentPickUpPose = new Pose2d(33, -55, pi/2);

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            // GAMEPAD 1 Controls
            drive.updatePoseEstimate();
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




            //pick up the element and drive and hang element
            if (gamepad1.right_bumper) {
                hangCount++;
//                drive.updatePoseEstimate();
                Actions.runBlocking(
                        new SequentialAction(
                                drive.actionBuilder(specimentPickUpPose)
                                    .strafeTo(new Vector2d(33,-60), new TranslationalVelConstraint(30))
                                            .build(),

                            //pick up block
                            hangClaw.closeClaw(),
                                new SleepAction(0.3),
                            new ParallelAction(
                            outSlide.spinUp(),
                            // go to submersible to hang block
                            drive.actionBuilder(new Pose2d(33, -60, Math.toRadians(-90)))
                                .strafeTo(new Vector2d(33, -55))
                                .splineToLinearHeading(new Pose2d(4-(1.25*hangCount), -33, -pi / 2), pi / 2)
                                .build()
                            ),
                            new SleepAction(0.2),
                            // hang block 2
                            outSlide.hang(),
                            outSlide.powerDown()
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
                                        drive.actionBuilder(new Pose2d(4-(1.5*hangCount),-33,Math.toRadians(-90)))
                                                .splineToLinearHeading(new Pose2d(33, -55, pi/2), 180)
//                                                .strafeTo(new Vector2d(33,-60), new TranslationalVelConstraint(50))
                                                .build()
                                ),
                                outSlide.powerDown()
                        )
                );
            }


            if (gamepad1.dpad_up) {
                intake.raisearm().run(packet);
            }
            if (gamepad1.dpad_right) {
                outSlide.spinUp().run(packet);;
            }

            if (gamepad1.left_trigger > 0) {
                Actions.runBlocking(
                        new SequentialAction(
                                inSlide.closeSlide(),
                            drive.actionBuilder(new Pose2d(6, -33, Math.toRadians(-90)))
                            //Drive to  Block
                                .splineToLinearHeading(new Pose2d(30, -15, pi/2), pi/2)
                                .strafeTo(new Vector2d(60, -12))
//                                // Push Block
                          .   strafeTo(new Vector2d(60, -50), new TranslationalVelConstraint(30)) // 54.5
                        .build()
                )
                );
            }

            // GAMEPAD 2 Controls


            // Hang Claw Controls

            if (gamepad2.a) {
                hangClaw.openClaw().run(packet);
            }

            // intake claw rotate control

            if (gamepad2.y) {
                intake.rotateMid().run(packet);
            }
            if (gamepad2.b) {
                intake.rotateRight().run(packet);
            }
            if (gamepad2.x) {
                intake.rotateLeft().run(packet);
            }


            //Hang speciment and spin down
            if (gamepad2.right_bumper) {
                Actions.runBlocking(
                        new SequentialAction(
                                outSlide.hang()
//                            hangClaw.openClaw(),
//                            outSlide.spinDown(),
//                            outSlide.powerDown()
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

            if (gamepad1.dpad_left) {
                Actions.runBlocking(
                        new SequentialAction(
                                hangClaw.closeClaw()
                        )
                );
            }


            //Drop controls

            //transfer the specimen
            if (gamepad2.left_trigger > 0) {
                Actions.runBlocking(
                    new SequentialAction(
                            new ParallelAction(
                                    intake.rotateMid(),
                                outtake.armPick(),
                                outtake.openClaw(),
                                intake.raisearm(),
                                inSlide.closeSlide()
                            ),
                        new SleepAction(1.5),
                        outtake.closeClaw(),
                            new SleepAction(1),
                            new ParallelAction(
                        intake.openClaw(),
                            new SleepAction(0.2),
                            outtake.armDrop(),
                            outSlide.drop()
                            )
                    )
                );
            }

            // drop speciment and close slide
            if (gamepad2.right_trigger > 0) {
                Actions.runBlocking(
                    new SequentialAction(
                        outtake.openClaw(),
                            new SleepAction(0.2),
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

            // ascend up
            if (gamepad2.left_stick_y < 0) {
                Actions.runBlocking(
                        outSlide.ascendUp()
                );
            }

            // ascend down

            if (gamepad2.left_stick_y > 0) {
                Actions.runBlocking(
                        outSlide.ascendDown()
                );
            }


            // drop position
            if (gamepad2.right_stick_y < 0) {
                Actions.runBlocking(
                        outSlide.drop()
                );
            }

            // spin down and reset

            if (gamepad2.right_stick_y > 0) {
                Actions.runBlocking(
                        new SequentialAction(
                            outSlide.negativeSpinDown(),
                                new SleepAction(1.5)
//                            outSlide.powerDown()
                        )
                );

            }



            return true;
        }
    }

    public Action teleopControls() {
        return new TeleopControl();
    }
}
