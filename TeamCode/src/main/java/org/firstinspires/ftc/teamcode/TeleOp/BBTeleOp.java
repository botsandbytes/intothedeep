
package org.firstinspires.ftc.teamcode.TeleOp;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.roadrunner.PinpointDrive;
import org.firstinspires.ftc.teamcode.robot.HangClaw;
import org.firstinspires.ftc.teamcode.robot.Intake;
import org.firstinspires.ftc.teamcode.robot.IntakeSlide;
import org.firstinspires.ftc.teamcode.robot.OuttakeClaw;
import org.firstinspires.ftc.teamcode.robot.OuttakeSlide;
import org.firstinspires.ftc.teamcode.robot.mecanum;

import java.util.ArrayList;
import java.util.List;

@TeleOp
public class BBTeleOp extends LinearOpMode {
    private FtcDashboard dash;
    private List<Action> runningActions;

    // Motor declarations
    private DcMotorEx frontLeftMotor;
    private DcMotorEx backLeftMotor;
    private DcMotorEx frontRightMotor;
    private DcMotorEx backRightMotor;

    private Servo hangServo;
//    Pose2d beginPose = new Pose2d(-10, -62, Math.toRadians(-90));
//    PinpointDrive drive = new PinpointDrive(hardwareMap, beginPose);
//    OuttakeSlide outtakeSlide = new OuttakeSlide(hardwareMap);
//    IntakeSlide inSlide = new IntakeSlide(hardwareMap);
//    HangClaw hangClaw = new HangClaw(hardwareMap);
//    OuttakeClaw outtakeClaw = new OuttakeClaw(hardwareMap);
//    Intake intake = new Intake(hardwareMap);
//    mecanum drivetrain = new mecanum(hardwareMap, gamepad1);

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize dashboard and actions list
        dash = FtcDashboard.getInstance();
        runningActions = new ArrayList<>();

        // Initialize motors
        frontLeftMotor = hardwareMap.get(DcMotorEx.class, "leftFront");
        backLeftMotor = hardwareMap.get(DcMotorEx.class, "leftBack");
        backRightMotor = hardwareMap.get(DcMotorEx.class, "rightBack");
        frontRightMotor = hardwareMap.get(DcMotorEx.class, "rightFront");
        hangServo = hardwareMap.get(Servo.class, "hangClaw");

        // Set motor directions
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            TelemetryPacket packet = new TelemetryPacket();

            // maual drive
            driveManual();
            clawControl();
//            new InstantAction(()-> hangServo.setPosition(0.6));


            // Update running actions
            List<Action> newActions = new ArrayList<>();
//            newActions.add(new InstantAction(()-> hangServo.setPosition(0.6)));
//            newActions.add(new SleepAction(1));
//            newActions.add(new InstantAction(()-> hangServo.setPosition(0.4)));
//            newActions.add(new SleepAction(1));
            for (Action action : runningActions) {
                action.preview(packet.fieldOverlay());
                if (action.run(packet)) {
                    newActions.add(action);
//                    newActions.add(new InstantAction(()-> hangServo.setPosition(0.6)));
//                    newActions.add(new InstantAction(()-> sleep(2000)));
//                    newActions.add(new InstantAction(()-> hangServo.setPosition(0.4)));
//                    newActions.add(hangClaw.openClaw());
                }
            }
            runningActions = newActions;

            // Send telemetry
            dash.sendTelemetryPacket(packet);
        }
    }

    public void clawControl(){
        if (gamepad1.a) {
            hangServo.setPosition(0.6);
        } else if (gamepad1.b) {
            hangServo.setPosition(0.4);
        }
    }

    public void driveManual() {
        // Drive controls
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double rx = gamepad1.right_stick_x;

        // Calculate motor powers
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        // Set motor powers
        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);

    }
}