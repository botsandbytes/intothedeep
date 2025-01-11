
package org.firstinspires.ftc.teamcode.TeleOp;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
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

        // Set motor directions
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            TelemetryPacket packet = new TelemetryPacket();

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

            // Update running actions
            List<Action> newActions = new ArrayList<>();
            for (Action action : runningActions) {
                action.preview(packet.fieldOverlay());
                if (action.run(packet)) {
                    newActions.add(action);
                }
            }
            runningActions = newActions;

            // Send telemetry
            dash.sendTelemetryPacket(packet);
        }
    }
}