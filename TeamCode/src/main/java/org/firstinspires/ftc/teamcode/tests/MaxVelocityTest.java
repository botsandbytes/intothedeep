package org.firstinspires.ftc.teamcode.tests;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MaxVelocityTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        final DcMotorEx leftFront, leftBack, rightBack, rightFront;
        // Declare our motors
        // Make sure your ID's match your configuration
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // TODO: reverse motor directions if needed
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            leftFront.setPower(1);
            leftBack.setPower(1);
            rightFront.setPower(1);
            rightBack.setPower(1);
            telemetry.addData("fl", leftFront.getPower());
            telemetry.addData("bl", leftBack.getPower());
            telemetry.addData("fr", rightFront.getPower());
            telemetry.addData("br", rightBack.getPower());
            telemetry.update();
        }
    }
}