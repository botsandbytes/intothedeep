package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
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
                .setConstraints(100, 80, Math.toRadians(180), Math.toRadians(180), 15)
                .setDimensions(17.75, 16)
                .build();

        Pose2d beginPos = new Pose2d(-44, -40, Math.toRadians(90)); //new Pose2d(-10, -62, Math.toRadians(-90));

        myBot.runAction(
                new SequentialAction(
                        myBot.getDrive().actionBuilder(beginPos)
                                .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
//                                .lineToY(-31.5)
//                            .splineToLinearHeading(new Pose2d(-48, -40, Math.toRadians(-90)), Math.toRadians(180))
//                                .waitSeconds(2)
//                                .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
//                                .waitSeconds(1)
//                                .splineToLinearHeading(new Pose2d(-58, -38, Math.toRadians(90)), Math.toRadians(90))
//                                .waitSeconds(2)
//                                .strafeTo(new Vector2d(-56,-38))
//                                .splineToLinearHeading(new Pose2d(-56, -54, Math.toRadians(47)), Math.toRadians(47))
//                                .waitSeconds(1)
//                                .splineToLinearHeading(new Pose2d(-30, -12, Math.toRadians(0)), Math.toRadians(0))
                                .build()
                )
        );

//                // Swayam custom code
//                .lineToY(-34)
//                .strafeTo(new Vector2d(9, -36))
//                // Drive to First Block
//                .splineToLinearHeading(new Pose2d(34, -27, pi/2), pi/2)
//                .strafeTo(new Vector2d(39, -12))
//                // Push Block 1
//                .splineToLinearHeading(new Pose2d(45, -52, pi/2), pi/2)
//                // Go back, Spline Turn, and go to block 2
//                .strafeTo(new Vector2d(45, -48))
//                .splineToConstantHeading(new Vector2d(54, -12), 0)
//                // Push Block 2
//                .strafeTo(new Vector2d(54, -52))
////                 Go back, and go pick up Block 1
//                .strafeTo(new Vector2d(34, -52))
//                .strafeTo(new Vector2d(34, -61.5), new TranslationalVelConstraint(50))
//                .lineToY(-57)
//                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
//                .waitSeconds(0)
//                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
//                .lineToY(-61.5, new TranslationalVelConstraint(30))
//                .lineToY(-57)
//                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
//                .waitSeconds(0)
//                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
//                .lineToY(-61.5, new TranslationalVelConstraint(30))
//


                // Rishabh Code

//                .lineToY(-34)
//                // Drive to First Block
//                .strafeTo(new Vector2d(34, -34))
//                .strafeTo(new Vector2d(34,-8.75))
//                .strafeTo(new Vector2d(45, -8.75))
////                .splineToConstantHeading(new Vector2d(45, -8.75), pi/2)
//                // Push Block 1
//                .strafeTo(new Vector2d(45, -54.5))
//                // Go back, Spline Turn, and go to block 2
//                .strafeTo(new Vector2d(45, -48))
//                .splineToLinearHeading(new Pose2d(45, -8.75, pi/2), pi/2)
//                .strafeTo(new Vector2d(54, -8.75))
//                // Push Block 2
//                .strafeTo(new Vector2d(54, -54.5))
//                // Go back, and go pick up Block 1
//                .strafeTo(new Vector2d(34, -40))
//                .strafeTo(new Vector2d(34, -61.5), new TranslationalVelConstraint(50))
//
//                .lineToY(-55)
//                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
//                .waitSeconds(0)
//                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
//                .lineToY(-61.5, new TranslationalVelConstraint(30))
//                .lineToY(-55)
//                .splineToLinearHeading(new Pose2d(9, -34, -pi/2), pi/2)
//                .waitSeconds(0)
//                .splineToLinearHeading(new Pose2d(34, -50, pi/2), pi/2)
//                .lineToY(-61.5, new TranslationalVelConstraint(30))


//                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}