package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.MecanumKinematics;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Pose2dDual;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        final double pi = Math.PI;
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(100, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(9, -62, Math.toRadians(-90)))
                // Drive to Submarine
                .lineToY(-34)
                // Drive to First Block
                .strafeTo(new Vector2d(34, -34))
                .strafeTo(new Vector2d(34,-8.75))
                .strafeTo(new Vector2d(45, -8.75))
//                .splineToConstantHeading(new Vector2d(45, -8.75), pi/2)
                // Push Block 1
                .strafeTo(new Vector2d(45, -54.5))
                // Go back, Spline Turn, and go to block 2
                .strafeTo(new Vector2d(45, -48))
                .splineToLinearHeading(new Pose2d(45, -8.75, pi/2), pi/2)
                .strafeTo(new Vector2d(54, -8.75))
                // Push Block 2
                .strafeTo(new Vector2d(54, -54.5))
                // Go back, and go pick up Block 1
                .strafeTo(new Vector2d(34, -40))
                .strafeTo(new Vector2d(34, -61.5), new TranslationalVelConstraint(50))

                .lineToY(-55)
                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
                .waitSeconds(0)
                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
                .lineToY(-61.5, new TranslationalVelConstraint(30))
                .lineToY(-55)
                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
                .waitSeconds(0)
                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
                .lineToY(-61.5, new TranslationalVelConstraint(30))
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}