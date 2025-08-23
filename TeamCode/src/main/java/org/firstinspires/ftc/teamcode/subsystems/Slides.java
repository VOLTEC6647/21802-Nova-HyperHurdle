package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Subsystem;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Bot;
import org.firstinspires.ftc.teamcode.Vision.Vision;

public class Slides implements Subsystem {
    private Servo slides;
    private Bot bot;
   // private Vision vision;
   public double setPoint = 0;

    public Slides(Bot bot){
        this.bot = bot;

       // vision = new Vision(bot);

        slides = bot.hMap.get(Servo.class, "slides");

    }
    @Override
    public void periodic(){
        if(bot.opertator.gamepad.dpad_up) {
            slides.setPosition(0.2);

        }
        else if (bot.opertator.gamepad.dpad_down){
            slides.setPosition(0);

        }



    }
    public void setPosition(double setpoint){
        setPoint = setpoint;
        slides.setPosition(setPoint);


    }
   /* public void setDistance () {
        double distance = vision.getDistance() * 0.03528585102;
        slides.setPosition(distance);

        if (vision.getDistance() > 28.34){
            slides.setPosition(1);
        } else if (vision.getDistance() < 0){
            slides.setPosition(0);

        }*/
    }



