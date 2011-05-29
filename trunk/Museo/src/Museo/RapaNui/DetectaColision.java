package Museo.RapaNui;

//Clase:   DetectaColision.java
//Esta se encarga de evitar  que la persona atraviese paredes
//y objetos presentes en el museo haciendo la respectiva
//validacion de dicho suceso

import Museo.RapaNui.Museo;
import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.util.Enumeration;
import com.sun.j3d.utils.geometry.Box;


// Clase, que reacciona a una colisión
public class DetectaColision extends Behavior {
    WakeupOnCollisionEntry wEntrada;
    //caja asignada a la persona para evitar colision
    Box persona;
    
    // constructor
    public DetectaColision(Box persona) {
        this.persona = persona;
    }
    
    // Funcion: initialize()
    // la persona está limitada al criterio de WakeupOnCollisionEntry.
    @Override
    public void initialize() {
        wEntrada = new WakeupOnCollisionEntry(persona, wEntrada.USE_GEOMETRY);
        wakeupOn(wEntrada);
    }
    
    // Funcion: processStimulus()
    // Respuesta al estimulo del usuario
    @Override
    public void processStimulus(Enumeration criteria) {
        Museo.colKey = Museo.curKey;
        wakeupOn(wEntrada);
    }
}
