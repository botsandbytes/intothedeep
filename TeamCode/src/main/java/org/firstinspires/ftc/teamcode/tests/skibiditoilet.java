package org.firstinspires.ftc.teamcode.tests;

//import com.ThermalEquilibrium.homeostasis.Controllers.Feedback.BasicPID;
//import com.ThermalEquilibrium.homeostasis.Parameters.PIDCoefficients;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp
public class skibiditoilet extends LinearOpMode {
    public static double Kp = 1.0;
    public static double Ki = 0.0;
    public static double Kd = 0.0;
    public static double power = 0.0;
    public static int targetPosition = 0;
//    BasicPID controller = new BasicPID(new PIDCoefficients(Kp,Ki,Kd));
    @Override
    public void runOpMode() {
        // Initialize hardware
        // Tunable parameters for PID controller
        DcMotorEx slideL = hardwareMap.get(DcMotorEx.class, "slideL");
//        slideR = hardwareMap.get(DcMotorEx.class, "slideR");

        slideL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        slideR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        slideL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        slideR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slideL.setDirection(DcMotorSimple.Direction.FORWARD);
//        slideR.setDirection(DcMotorSimple.Direction.REVERSE);

        // Initialize FTC Dashboard
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        waitForStart();

        while (opModeIsActive()) {
            // Example target and measured positions
            double measuredPosition = slideL.getCurrentPosition();

            // Calculate motor power using PID controller
            // Set motor power
            slideL.setTargetPosition(targetPosition);
            slideL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            slideL.setPower(power);
//            slideR.setPower(power);

            // Send telemetry data to FTC Dashboard
            telemetry.addData("Target Position", targetPosition);
            telemetry.addData("Measured Position", measuredPosition);
            telemetry.addData("Motor Power", power);
            telemetry.addData("Error", targetPosition - measuredPosition);
            telemetry.update();
        }
    }
}
