package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class IntakeClaw {
    private Servo claw, arm;

    public IntakeClaw(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "outClaw");
        arm = hardwareMap.get(Servo.class, "outArm");
    }

    // .58 open for hangClaw
    // .75 close for hangClaw
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.75);
            packet.put("Out Claw Closed", "0.75");
            return false;
        }
    }
    public Action closeClaw() {
        return new CloseClaw();
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.55);
            packet.put("Out Claw Open", "0.55");
            return false;
        }
    }
    public Action openClaw() {
        return new OpenClaw();
    }

    public class ArmPick implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0);
            packet.put("Out Arm Pick", "0");
            return false;
        }
    }
    public Action armPick() {
        return new ArmPick();
    }

    public class ArmDrop implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0.55);
            packet.put("Out Arm Drop", "0.55");
            return false;
        }
    }
    public Action armDrop() {
        return new ArmDrop();
    }
}

