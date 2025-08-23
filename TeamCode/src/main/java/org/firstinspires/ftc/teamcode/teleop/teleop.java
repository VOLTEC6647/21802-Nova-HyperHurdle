package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.geometry.Rotation2d;
import com.qualcomm.robotcore.util.ReadWriteFile;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.commands.TeleopDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.ClawPivot;
import org.firstinspires.ftc.teamcode.subsystems.ClawUp;
import org.firstinspires.ftc.teamcode.subsystems.DiffClaw;
import org.firstinspires.ftc.teamcode.subsystems.DiffClawUp;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Slides;

import java.io.File;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "TeleOp")
public class teleop extends CommandOpMode {
    private Bot bot;
    private MultipleTelemetry telem;
    private GamepadEx driverGamepad;
    private GamepadEx operatorGamepad;
    private MecanumDrive drive;
    private Slides slides;
    private DiffClaw dClaw;
    private DiffClawUp diffClawUp;
    private ClawUp clawUp;

    private Claw claw;
    private ClawPivot clawPivot;
    private Arm arm;

    double intakeRotatePerDegree = 0.000555555556;

    double intakeRotatePerDegreeUp = 0.0037037037037037037037037037037;





    public void initialize() {

        CommandScheduler.getInstance().reset();
        FtcDashboard dashboard = FtcDashboard.getInstance();


        telem = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        driverGamepad = new GamepadEx(gamepad1);
        operatorGamepad = new GamepadEx(gamepad2);

        // drive region

        bot = new Bot(telem, hardwareMap, driverGamepad, operatorGamepad);
        bot.getImu().resetYaw();

        File myFileName = AppUtil.getInstance().getSettingsFile("team.txt");
        String team = ReadWriteFile.readFile(myFileName);


        if (team.equals("blue")){
            bot.setRotationOffset(Rotation2d.fromDegrees(0));
        }
        if (team.equals("red")){
            bot.setRotationOffset(Rotation2d.fromDegrees(0));
        }

       //vision = new Vision(bot);
        // vision.register();

        drive = new MecanumDrive(bot);
        drive.register();

        slides = new Slides(bot);
        slides.register();

        dClaw = new DiffClaw(bot);
        dClaw.register();

        claw = new Claw(bot);
        claw.register();

        clawPivot = new ClawPivot(bot);
        clawPivot.register();

        arm = new Arm(bot);
        arm.register();

        diffClawUp = new DiffClawUp(bot);
        diffClawUp.register();

        clawUp = new ClawUp(bot);
        clawUp.register();








      TeleopDriveCommand driveCommand = new TeleopDriveCommand(
                drive,
                () -> -driverGamepad.getRightX()*.80,
                () -> -driverGamepad.getLeftY()*1,
                () -> driverGamepad.getLeftX()*1,
                () -> bot.speed
        );
        bot.speed = 0.75;

        register(drive);
        drive.setDefaultCommand(driveCommand);


        new GamepadButton(operatorGamepad, GamepadKeys.Button.RIGHT_BUMPER)
                .whileActiveOnce(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> clawPivot.setPosition(0.24)),
                                new InstantCommand(()-> dClaw.setPositionD(0.5-(0*intakeRotatePerDegree))),
                                new InstantCommand(()-> dClaw.setPositionI(0.5+(0*intakeRotatePerDegree)))
                        )
                );

        new GamepadButton(operatorGamepad, GamepadKeys.Button.LEFT_STICK_BUTTON)
                .whileActiveOnce(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> arm.setPosition(0.4)),
                                new InstantCommand(()-> diffClawUp.setPositionD(0.9)),
                                new InstantCommand(()-> diffClawUp.setPositionI(0.9))

                        )
                );
        new GamepadButton(operatorGamepad, GamepadKeys.Button.RIGHT_STICK_BUTTON)
                .whileActiveOnce(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> clawPivot.setPosition(0.9)),
                                new InstantCommand(()-> slides.setPosition(0)),
                                new InstantCommand(()-> dClaw.setPositionD(0.56)),
                                new InstantCommand(()-> dClaw.setPositionI(0.56))

                        )
                );

        new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_UP)
                .whileActiveOnce(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> slides.setPosition(0.25)),
                                new InstantCommand(()-> clawPivot.setPosition(0.5)),
                                new InstantCommand(()-> claw.setPosition(1))
                        )
                );

        new GamepadButton(operatorGamepad, GamepadKeys.Button.DPAD_DOWN)
                .whileActiveOnce(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> slides.setPosition(0)),
                                new InstantCommand(()-> clawPivot.setPosition(0.9))
                        )
                );

        new GamepadButton(operatorGamepad, GamepadKeys.Button.B)
                .whileActiveOnce(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> arm.setPosition(0.12)),
                                new InstantCommand(()-> clawUp.setSetpoint(0.3)),
                                new WaitCommand(50),
                                new InstantCommand(()-> diffClawUp.setPositionD(0.3)),
                                new InstantCommand(()-> diffClawUp.setPositionI(0.3))
                        )
                );


        new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
                .whileActiveOnce(
                        new SequentialCommandGroup(
                            new InstantCommand(()-> clawUp.setPosition(0)),
                            new WaitCommand(50),
                            new InstantCommand(()-> arm.setPosition(0.8)),
                            new WaitCommand(500),
                                new InstantCommand(()-> diffClawUp.setPositionD(0.6-(85*intakeRotatePerDegreeUp))),
                            new InstantCommand(()-> diffClawUp.setPositionI(0.6+(85*intakeRotatePerDegreeUp)))
                )
        );
        new GamepadButton(operatorGamepad, GamepadKeys.Button.Y)
                .whenReleased(
                        new SequentialCommandGroup(
                                new InstantCommand(()-> arm.setPosition(1)),
                                new InstantCommand(()-> diffClawUp.setPositionD(0.6-(85*intakeRotatePerDegreeUp))),
                                new InstantCommand(()-> diffClawUp.setPositionI(0.6+(85*intakeRotatePerDegreeUp))),
                                new WaitCommand(500),
                                new InstantCommand(()-> clawUp.setPosition(0.3))
                        )
                );



       /*new GamepadButton(driverGamepad, GamepadKeys.Button.A)
               .whenPressed(
                       new ParallelCommandGroup(
                        new InstantCommand(()-> drive.teleopDrive(vision.getTurnPower(),1)),
                        new InstantCommand(()-> slides.setDistance()),
                        new InstantCommand(()-> rClaw.setTurnServo()),
                        new InstantCommand(()->claw.setSetpoint(0.94))
            ));
       */






        while (opModeInInit()){
            telem.update();
        }
        //endregion


    }
    @Override
    public void run() {
        //periodicBindings();
        CommandScheduler.getInstance().run();
        telem.update();
    }
    Boolean BPressed = false;
    Boolean APressed = false;
    public void periodicBindings(){

        /*
        if(operatorGamepad.getButton(GamepadKeys.Button.A)){
            if(!APressed){
                APressed = true;

            }


        }
        if(!operatorGamepad.getButton(GamepadKeys.Button.A)){
            APressed = false;
        }

        if(operatorGamepad.getButton(GamepadKeys.Button.B)){
            if(!BPressed){
                BPressed = true;

            }

        }
        if(!operatorGamepad.getButton(GamepadKeys.Button.B)){
            BPressed = false;
        }

         */


      /*  if(operatorGamepad.wasJustPressed(GamepadKeys.Button.A)){
            schedule(
            new SequentialCommandGroup(
                    new InstantCommand(()->{
                        elbow.setPosition(ElbowSubsystem.ElbowSetpoints.grabbing);
                    }),

                new WaitCommand(1000),
                    new InstantCommand(()->{
                        arm.setPosition(ArmPIDSubsystem.ArmSetpoints.grabbing);
                    })
            )
            );
        }

        if(operatorGamepad.wasJustPressed(GamepadKeys.Button.Y)){
           schedule(
            new SequentialCommandGroup(
                    new InstantCommand(()->{
                        arm.setPosition(ArmPIDSubsystem.ArmSetpoints.grabbing);
                    }),
                    new WaitCommand(1000),
                    new InstantCommand(()->{
                        elbow.setPosition(ElbowSubsystem.ElbowSetpoints.placing);
                    })
            ));
        }*/
    }
}