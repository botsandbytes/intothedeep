package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class OuttakeClaw {
    private Servo claw, arm;

    public OuttakeClaw(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "outClaw");
        arm = hardwareMap.get(Servo.class, "outArm");
    }

    // .58 open for hangClaw
    // .75 close for hangClaw
    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(1);
            packet.put("Out Claw Closed", claw.getPosition());
            return false;
        }
    }
    public Action closeClaw() {
        return new CloseClaw();
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            claw.setPosition(0.85);
            packet.put("Out Claw Open", claw.getPosition());
            return claw.getPosition() != 0.85;
        }
    }
    public Action openClaw() {
        return new OpenClaw();
    }

    public class ArmPick implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(.98);
            packet.put("Out Arm Pick", arm.getPosition());
            return arm.getPosition()!=.98;
        }
    }
    public Action armPick() {
        return new ArmPick();
    }

    public class ArmDrop implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0.2);
            packet.put("Out Arm Drop", arm.getPosition());
            return false;
        }
    }
    public Action armDrop() {
        return new ArmDrop();
    }

    public class ArmPark implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0);
            packet.put("Out Arm Drop", arm.getPosition());
            return false;
        }
    }
    public Action armPark() {
        return new ArmPark();
    }

}

