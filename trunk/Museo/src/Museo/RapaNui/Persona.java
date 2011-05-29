package Museo.RapaNui;

//Clase:   Persona.java
//Esta clase se ocupa principalmente del manejo de la persona en el museo,
//de permitir que esta se desplace por la escena (hacia delante y atrás)
//y observar (izquierda, derecha) en la escena, pueda hacer giros y además
//crea una caja para la persona para permitir el control de colisiones

import java.awt.event.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Box;


public class Persona {
    TransformGroup persona_tg;
    Transform3D t;
    Vector3f vec;
    float[] f;
    
    TransformGroup vp_tg;
    
    Appearance app;
    
    DetectaColision colision;
    
    Transform3D traslacionPX;
    Transform3D traslacionMX;
    Transform3D rotacionD;
    Transform3D rotacionI;
    Transform3D traslacionRotacionI;
    Transform3D traslacionRotacionD;
    
    Transform3D tpx;
    Transform3D tmx;
    Transform3D rr;
    Transform3D rl;
    Transform3D tAr;

//--------------Constructor--------------\\
//--------------Parametro: ViewPlatformTG (Posición de la cámara en el principio)--------------\\
    public Persona(TransformGroup vp_tg) {
        this.vp_tg = vp_tg;
        persona_tg = new TransformGroup();
        t = new Transform3D();
        vec = new Vector3f();
        f = new float[3];
        
        app = new Appearance();

//--------------Dejar invisible el box para la colision--------------\\
        TransparencyAttributes transAtt = new TransparencyAttributes();
        transAtt.setTransparencyMode(TransparencyAttributes.BLENDED);
        transAtt.setTransparency(1.0f);
        app.setTransparencyAttributes(transAtt);
              
//--------------caja utilizada para  detectar colision--------------\\
        Box persona = new Box(0.2f, 1.0f, 0.6f, app);
        DetectaColision colision = new DetectaColision(persona);
        BoundingSphere bounds =	new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0);
        colision.setSchedulingBounds(bounds);

//--------------La persona en sistema de posición de salida//--------------\\
        vec.set(0.0f, 0.0f, -21.0f);
        t.setTranslation(vec);
        persona_tg.setTransform(t);
        persona_tg.addChild(persona);
        persona_tg.addChild(colision);
        persona_tg.setCapability(persona_tg.ALLOW_TRANSFORM_WRITE);
        persona_tg.setCapability(persona_tg.ALLOW_TRANSFORM_READ);

//--------------Definición de las transformaciones para el funcionamiento y la mirada--------------\\
        traslacionPX = new Transform3D(); //avanza
        traslacionMX = new Transform3D(); //retrocede
        rotacionD = new Transform3D(); //rota derecha
        rotacionI = new Transform3D(); //rota izquierda
        traslacionRotacionI = new Transform3D();
        traslacionRotacionD = new Transform3D();

//--------------Control de Velocidades--------------\\
        //velocidad de traslación
        traslacionPX.set(new Vector3f(0.0f, 0.0f, -0.5f)); //avanzar
        traslacionMX.set(new Vector3f(0.0f, 0.0f,  0.5f)); //retroceder
        
        // velocidad de observación
        rotacionD.rotY(Math.PI/-60.0f); //izquierda
        rotacionI.rotY(Math.PI/ 60.0f); //derecha
        
        traslacionRotacionI.set(new Vector3f(-0.03f, 0.0f, 0.0f));
        traslacionRotacionD.set(new Vector3f( 0.03f, 0.0f, 0.0f));
        
//--------------Almacenador intermediario para el TG actual de la figura y de la cámara--------------\\
        tpx = new Transform3D();
        tmx = new Transform3D();
        rr  = new Transform3D();
        rl  = new Transform3D();
        tAr = new Transform3D();
    }

    public TransformGroup getPersona() {
        return persona_tg;
    }
    
//--------------Funcion Camina--------------\\
    public void camina(int key) {
        if (key == KeyEvent.VK_UP) {  //avanza
            persona_tg.getTransform(tpx);
            tpx.mul(traslacionPX);
            persona_tg.setTransform(tpx);
            
            vp_tg.getTransform(tpx);
            tpx.mul(traslacionPX);
            vp_tg.setTransform(tpx);
        }
        
        if (key == KeyEvent.VK_DOWN) { //retrocede
            persona_tg.getTransform(tmx);
            tmx.mul(traslacionMX);
            persona_tg.setTransform(tmx);
            
            vp_tg.getTransform(tmx);
            tmx.mul(traslacionMX);
            vp_tg.setTransform(tmx);
        }
    }
    
//--------------Funcion observar--------------\\
    public void observa(int key) {
        if (key == KeyEvent.VK_LEFT) { // observa hacia izquierda
            vp_tg.getTransform(rl);
            rl.mul(rotacionI);
            vp_tg.setTransform(rl);
            
            persona_tg.getTransform(rl);
            rl.mul(rotacionI);
            persona_tg.setTransform(rl);

            persona_tg.getTransform(tAr);
            tAr.mul(traslacionRotacionI);
            persona_tg.setTransform(tAr);
        }
        
        if (key == KeyEvent.VK_RIGHT) { //observa hacia derecha
            vp_tg.getTransform(rr);
            rr.mul(rotacionD);
            vp_tg.setTransform(rr);
            
            persona_tg.getTransform(rr);
            rr.mul(rotacionD);
            persona_tg.setTransform(rr);
            
            persona_tg.getTransform(tAr);
            tAr.mul(traslacionRotacionD);
            persona_tg.setTransform(tAr);
        }
    }
}

