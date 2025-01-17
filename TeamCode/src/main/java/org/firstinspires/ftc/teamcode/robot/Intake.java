package org.firstinspires.ftc.teamcode.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private Servo arm, claw;
//    private CRServo wheel;

    public Intake(HardwareMap hardwareMap) {
        arm = hardwareMap.get(Servo.class, "intArm");
        claw = hardwareMap.get(Servo.class, "intClaw");
//        wheel = hardwareMap.get(CRServo.class, "intCR");
    }



    public class CloseClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            //  code for CR active intake
            //            wheel.setDirection(DcMotorSimple.Direction.REVERSE);
            //            wheel.setPower(1);
            //            new SleepAction(1);
            //            wheel.setPower(0);

            claw.setPosition(0.8);
            packet.put("Closed Claw", claw.getPosition());
            return false;
        }
    }
    public Action closeClaw() {
        return new CloseClaw();
    }

    public class OpenClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {

            //code for active intake
            // wheel.setDirection(DcMotorSimple.Direction.FORWARD);
            // wheel.setPower(1);
            // new SleepAction(1);
            // wheel.setPower(0);

            claw.setPosition(0.5);
            packet.put("Open Claw", claw.getPosition());
            return false;
        }
    }
    public Action openClaw() {
        return new OpenClaw();
    }

    public class LowerArm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0.8);
            packet.put("Lowered arm", arm.getPosition());
            return false;
        }
    }
    public Action lowerarm() {
        return new LowerArm();
    }

    public class RaiseArm implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0);
            packet.put("Raised arm", arm.getPosition());
            return arm.getPosition()!=0;
        }
    }
    public Action raisearm() {
        return new RaiseArm();
    }

    public class ArmMid implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            arm.setPosition(0.6);
            packet.put("Mid arm", arm.getPosition());
            return arm.getPosition()!=0.6;
        }
    }
    public Action armMid() {
        return new ArmMid();
    }
}

