package org.firstinspires.ftc.teamcode.robot;

import static java.lang.Boolean.FALSE;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class OuttakeSlide {
    private final DcMotorEx leftMotor, rightMotor;
    private HangClaw hangClaw;

    public OuttakeSlide(HardwareMap hardwareMap) {

        leftMotor = hardwareMap.get(DcMotorEx.class, "slideL");
        rightMotor = hardwareMap.get(DcMotorEx.class, "slideR");
        leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        hangClaw = new HangClaw(hardwareMap);
    }

    public class SpinUp implements Action {
        private boolean initialized = false;

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            if (!initialized) {
                leftMotor.setTargetPosition(1290);
                rightMotor.setTargetPosition(1290);
                leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftMotor.setPower(0.9);
                rightMotor.setPower(0.9);
                initialized = true;
            }

            double vel = leftMotor.getPower();
            packet.put("Current power in Spin up", vel);
            return leftMotor.isBusy();
        }
    }

    public class Hang implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftMotor.setTargetPosition(800);
            rightMotor.setTargetPosition(800);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setPower(0.9);
            rightMotor.setPower(0.9);

            double vel = leftMotor.getVelocity();
            packet.put("Current Velocity in Hang", vel);
            return leftMotor.isBusy();
        }
    }

    public class drop implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftMotor.setTargetPosition(2120);
            rightMotor.setTargetPosition(2120);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setPower(0.9);
            rightMotor.setPower(0.9);

            double vel = leftMotor.getVelocity();
            packet.put("Current Velocity in Hang", vel);
            return leftMotor.isBusy();
        }
    }

    public class SpinDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftMotor.setTargetPosition(0);
            rightMotor.setTargetPosition(0);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setPower(0.9);
            rightMotor.setPower(0.9);

            double power = leftMotor.getPower();
            packet.put("Curent Power in Spin Down", power);
            return  leftMotor.isBusy();
        }
    }

    public class AscendDown implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftMotor.setTargetPosition(1300);
            rightMotor.setTargetPosition(1300);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setPower(0.9);
            rightMotor.setPower(0.9);

            double power = leftMotor.getPower();
            packet.put("Curent Power in Park", power);
            return  leftMotor.isBusy();
        }
    }

    public class AscendUp implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftMotor.setTargetPosition(1800);
            rightMotor.setTargetPosition(1800);
            leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftMotor.setPower(0.9);
            rightMotor.setPower(0.9);

            double power = leftMotor.getPower();
            packet.put("Curent Power in Park", power);
            return  leftMotor.isBusy();
        }
    }

    public class PowerDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket packet) {
            leftMotor.setPower(0);
            rightMotor.setPower(0);
            packet.put("Poert Down", "slide");
            return  FALSE;
        }
    }

    public Action powerDown() {
        return new PowerDown();
    }

    public Action spinUp() {
        return new SpinUp();
    }

    public Action spinDown() {
        return new SpinDown();
    }

    public Action hang() {
        return new Hang();
    }


    public Action ascendDown() {
        return new AscendDown();
    }

    public Action ascendUp() {
        return new AscendUp();
    }

    public Action drop() {
        return new drop();
    }

    public Action hangOnBar(){
        return new SequentialAction(
                hang(),
                new SleepAction(2),
                hangClaw.openClaw(),
                new SleepAction(1),
                spinDown(),
                powerDown()
        );
    }
}


