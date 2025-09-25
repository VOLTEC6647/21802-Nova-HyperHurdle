package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.Servo;
import com.arcrobotics.ftclib.command.Subsystem;

import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Vision.Vision;
import org.firstinspires.ftc.teamcode.utils.MathUtils;

public class DiffClawUp implements Subsystem {
    private Servo sI;
    private Servo sD;

    private Bot bot;
    private Vision vision;
    public double setPoint = 0;
    double intakeRotatePerDegree = 0.0037037037037037037037037037037;


    public DiffClawUp(Bot bot) {
        this.bot = bot;

        sI = bot.hMap.get(Servo.class,"sIUp");
        sD = bot.hMap.get(Servo.class,"sDUp");

        sI.setDirection(Servo.Direction.REVERSE);
        sD.setDirection(Servo.Direction.FORWARD);

       // sI.setPosition(0);
       // sD.setPosition(0);



    }
    public void setSetpoint (double setPoint) {
        this.setPoint = setPoint;
        sI.setPosition(setPoint);
        sD.setPosition(setPoint);

    }


    @Override
    public void periodic(){


       /* if(bot.opertator.gamepad.left_bumper) {
            sD.setPosition(0.8 -(0 * intakeRotatePerDegree));
            sI.setPosition(0.8 +(0 * intakeRotatePerDegree));
        }
        else if (bot.opertator.gamepad.x){
            sD.setPosition(1 -(87 * intakeRotatePerDegree));
            sI.setPosition(1 +(87 * intakeRotatePerDegree));
        }*/




    }
    public void setPositionI(double setpoint){
        setPoint = setpoint;
        sI.setPosition(setPoint);


    }
    public void setPositionD(double setpoint){
        setPoint = setpoint;
        sD.setPosition(setPoint);


    }


}

