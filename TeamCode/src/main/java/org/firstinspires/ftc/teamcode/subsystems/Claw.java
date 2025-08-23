package org.firstinspires.ftc.teamcode.subsystems;
import com.qualcomm.robotcore.hardware.Servo;
import com.arcrobotics.ftclib.command.Subsystem;

import org.firstinspires.ftc.teamcode.Bot;

public class Claw implements Subsystem {
    //private Servo clawServo;

    private Servo clawServo;
    private Bot bot;
    public double setPoint = 0;
    boolean garraAbierta = true;

    // Variable para evitar cambios múltiples con una sola pulsación
    boolean botonPresionadoAnteriormente = false;

    public Claw(Bot bot) {
        this.bot = bot;

        clawServo = bot.hMap.get(Servo.class,"claw");

    }
    public void setSetpoint (double setPoint) {
        this.setPoint = setPoint;
        clawServo.setPosition(setPoint);
    }


    @Override
    public void periodic(){
        //double currentPosition = clawServo.getPosition();

        boolean botonPresionadoAhora = bot.opertator.gamepad.a;

        // Verificar si el botón acaba de ser presionado
        if (botonPresionadoAhora && !botonPresionadoAnteriormente) {
            // Cambiar el estado de la garra
            if (garraAbierta) {
                clawServo.setPosition(0);
                garraAbierta = false;
            } else {
                clawServo.setPosition(1);
                garraAbierta = true;
            }
        }

        // Actualizar el estado del botón para el siguiente ciclo
        botonPresionadoAnteriormente = botonPresionadoAhora;









    }
    public void setPosition(double setpoint){
        setPoint = setpoint;
        clawServo.setPosition(setPoint);


    }
}
