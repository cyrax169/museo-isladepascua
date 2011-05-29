package Museo.RapaNui;

//Clase:   Museo.java
//Clase Principal:
//Esta llama a la clase Persona, Ambiente, Constructor Principal
//Esta clase además debe controlar la posición inicial de la persona, 
//crea el simple universo y la rama de contenido además de definir y 
//validar los movimientos de la persona en las cuatro direcciones 
//(izquierda, derecha, arriba, abajo) de validar los límites fronterizos
//del museo. Además de la opción de cerrar la aplicación o partir de la
//posición inicial.


//Librerias de java 3D importadas
import Museo.RapaNui.Persona;
import Museo.RapaNui.Ambiente;
import java.awt.GraphicsConfiguration;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

//Clase principal, que arregla la escena
public class Museo extends Frame {
    
    SimpleUniverse simpleU;
    
    //Para la  camara y figura de la persona
    TransformGroup vp_tg;
    TransformGroup persona_tg;
    
    // Para la persona y el ambiente
    Persona persona;
    Ambiente ambiente;
    
    // Coordenadas a poner para la colisión
    float ant_coord_pl[] = new float[3];
    float ant_coord_vp[] = new float[3];
    
    // Para movimientos en la colisión y navegacion
    public static int colKey;
    public static int curKey;
        
    // constructor del Museo
    public Museo() {
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        //Adaptar objeto simpleunivero com los valores de confuguracion grafica propios
        //del sistema
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        
        //Creacion del objeto de soporte Canvas 3D
        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        setVisible(true);
        
        //creacion de objeto simple Universo
        simpleU = new SimpleUniverse(canvas3D);
                
        vp_tg = simpleU.getViewingPlatform().getViewPlatformTransform();
        
        // Sistema de la cámara de la posicion de salida
        Vector3f vp_vec = new Vector3f();
        Transform3D vp_t = new Transform3D();
        
        vp_vec.set(0.0f, 0.5f, -20.0f); //posicion de la persona
        vp_t.setTranslation(vp_vec);
        vp_tg.setTransform(vp_t);
        
        // Nuevo objeto de la persona
        persona = new Persona(vp_tg);
        persona_tg = persona.getPersona();
        
        // Nuevo objeto del ambiente
        ambiente  = new Ambiente(this);
        
        // Crear rama de contenido
        BranchGroup scene = createSceneGraph();
        //agregar rama a la raiz del arbol
        simpleU.addBranchGraph(scene);
                
        // Acontecimiento para controlar evento de presion de teclas para movimiento
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                
                Transform3D vp_t = new Transform3D();
                Vector3f vp_vec = new Vector3f();
                float vp_f[] = new float[3];
                
                Transform3D persona_t = new Transform3D();
                Vector3f persona_vec = new Vector3f();
                float persona_f[] = new float[3];
                
                
                vp_tg.getTransform(vp_t);
                vp_t.get(vp_vec);
                vp_vec.get(vp_f);
                
                persona_tg.getTransform(persona_t);
                persona_t.get(persona_vec);
                persona_vec.get(persona_f);
                
                
                //Control movimiento hacia la izquierda
                if (e.getKeyCode() == KeyEvent.VK_LEFT)
                    if (colKey != KeyEvent.VK_LEFT) {
                    	persona.observa(KeyEvent.VK_LEFT);
                        curKey = KeyEvent.VK_LEFT;
                    	colKey = 0;
                    } else {
                    	vp_vec.set(ant_coord_vp[0], ant_coord_vp[1], ant_coord_vp[2]);
                    	vp_t.setTranslation(vp_vec);
                    	vp_tg.setTransform(vp_t);
                    
                   	persona_vec.set(ant_coord_pl[0], ant_coord_pl[1], ant_coord_pl[2]);
                    	persona_t.setTranslation(persona_vec);
                    	persona_tg.setTransform(persona_t);
                    
                    	colKey = KeyEvent.VK_LEFT;
                      }
                
               //Control movimiento hacia la derecha
                if (e.getKeyCode() == KeyEvent.VK_RIGHT)
                    if (colKey != KeyEvent.VK_RIGHT) {
                    	persona.observa(KeyEvent.VK_RIGHT);
                        curKey = KeyEvent.VK_RIGHT;
                    	colKey = 0;
                    } else {
                    	vp_vec.set(ant_coord_vp[0], ant_coord_vp[1], ant_coord_vp[2]);
                    	vp_t.setTranslation(vp_vec);
                    	vp_tg.setTransform(vp_t);
                    
                    	persona_vec.set(ant_coord_pl[0], ant_coord_pl[1], ant_coord_pl[2]);
                    	persona_t.setTranslation(persona_vec);
                    	persona_tg.setTransform(persona_t);
                       	colKey = KeyEvent.VK_RIGHT;
                    }
                                
                //Control movimiento hacia arriba
                if (e.getKeyCode() == KeyEvent.VK_UP)
                    if (colKey != KeyEvent.VK_UP) {
                    	persona.camina(KeyEvent.VK_UP);
                    
                    	curKey = KeyEvent.VK_UP;
                    	colKey = 0;
                    } else {
                    	vp_vec.set(ant_coord_vp[0], ant_coord_vp[1], ant_coord_vp[2]);
                    	vp_t.setTranslation(vp_vec);
                    	vp_tg.setTransform(vp_t);
                    
                    	persona_vec.set(ant_coord_pl[0], ant_coord_pl[1], ant_coord_pl[2]);
                    	persona_t.setTranslation(persona_vec);
                    	persona_tg.setTransform(persona_t);
                    
                    	colKey = KeyEvent.VK_UP;
                    }
                
               //Control movimiento hacia abajo
                if (e.getKeyCode() == KeyEvent.VK_DOWN)
                    if (colKey !=  KeyEvent.VK_DOWN) {
                    	persona.camina(KeyEvent.VK_DOWN);
                    
                    	curKey = KeyEvent.VK_DOWN;
                    	colKey = 0;
                    } else {
                    	vp_vec.set(ant_coord_vp[0], ant_coord_vp[1], ant_coord_vp[2]);
                    	vp_t.setTranslation(vp_vec);
                    	vp_tg.setTransform(vp_t);
                    
                    	persona_vec.set(ant_coord_pl[0], ant_coord_pl[1], ant_coord_pl[2]);
                    	persona_t.setTranslation(persona_vec);
                    	persona_tg.setTransform(persona_t);
                    
                    	colKey = KeyEvent.VK_DOWN;
                    }
                
                
                //Valida los Limites fronterizos del Museo
                if (persona_f[0] > 28.0f || persona_f[0] < -28.0f || persona_f[2] >  8.0f || persona_f[2] < -40.0f) {
                    
                    persona_vec.set(persona_f[0], persona_f[1]-10, persona_f[2]);
                    persona_t.setTranslation(persona_vec);
                    persona_tg.setTransform(persona_t);
                                         
                    vp_vec.get(vp_f);
                    vp_vec.set(vp_f[0] +1.0f, vp_f[1], vp_f[2] +5.0f);
                    vp_t.set(vp_vec);
                    vp_tg.setTransform(vp_t);
                        
                    //Volver al comienzo presionado la tecla de la barra espaciadora
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        vp_vec.set(0.0f, 0.5f, -20.0f);
                        vp_t.setTranslation(vp_vec);
                        vp_tg.setTransform(vp_t);
                        
                        persona_f[0] = 0.0f;
                        persona_f[1] = 0.0f;
                        persona_f[2] = 5.0f;
                        persona_vec.set(persona_f[0], persona_f[1], persona_f[2]);
                        persona_t = null;
                        persona_t = new Transform3D();
                        persona_t.setTranslation(persona_vec);
                        persona_tg.setTransform(persona_t);
                    }
                }
                
                
                ant_coord_pl[0] = persona_f[0];
                ant_coord_pl[1] = persona_f[1];
                ant_coord_pl[2] = persona_f[2];
                
                ant_coord_vp[0] = vp_f[0];
                ant_coord_vp[1] = vp_f[1];
                ant_coord_vp[2] = vp_f[2];
                
                
                if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    setVisible(false);
                    dispose();
                    System.exit(0);
                }
            }
        });
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });
    } //Fin del Constructor Museo
    
    
    // Funcion: createSceneGraph()
    // Rama de Contenido
    public BranchGroup createSceneGraph() {
        
        BranchGroup objRoot = new BranchGroup();
        
        objRoot.addChild(ambiente.obtenerAmbiente());
        objRoot.addChild(persona_tg);
        objRoot.compile();
        
        return objRoot;
    }    
    
    //Función principal: main () 
    public static void main(String[] args) {
        Museo m = new Museo();

        m.setLocationRelativeTo(null); //centro la ventana principal
        
        ConstructorPrincipal constructor;
        
        for (int i = 0 ; i < args.length ; i++) {
            if(args[i] == null)
                break;
            else if (args[i].equals("-debugwin"))
                constructor = new ConstructorPrincipal();
        }
    }
}
