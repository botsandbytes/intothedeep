package org.firstinspires.ftc.teamcode.tests;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp
public class SlideTest extends LinearOpMode {
    public static int position = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        final DcMotorEx slideL, slideR;
        // Declare our motors
        // Make sure your ID's match your configuration
        slideL = hardwareMap.get(DcMotorEx.class, "slideL");
        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        //Reverse the direction for one motor
        slideR.setDirection(DcMotorEx.Direction.REVERSE);

        slideL.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        slideR.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        slideL.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        slideR.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);


//        slideL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        slideR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            telemetry.addData("pid", slideL.getPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION));
            telemetry.addData("Before Start L", slideL.getCurrentPosition());
            telemetry.addData("Before Start R", slideR.getCurrentPosition());
            telemetry.update();

            slideR.setTargetPosition(position);
            slideL.setTargetPosition(position);

            slideR.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            slideL.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            slideR.setPower(0.9);
            slideL.setPower(0.9);

            telemetry.addData("pid", slideL.getPIDFCoefficients(DcMotorEx.RunMode.RUN_TO_POSITION));
            telemetry.addData("L", slideL.getCurrentPosition());
            telemetry.addData("R", slideR.getCurrentPosition());
            telemetry.update();
        }
    }
}