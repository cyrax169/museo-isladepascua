package Museo.RapaNui;

//Clase: ConstructorPrincipal.java
//Este constructor es llamado por la clase Museo y
//a su vez este llama a la clase Ambiente y persona 
//de manera de hacer mas eficiente el codigo teniendo
//una vista global del museo
import Museo.RapaNui.Ambiente;
import java.awt.GraphicsConfiguration;
import java.awt.*;
import java.awt.event.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import javax.media.j3d.*;
import javax.vecmath.*;

public class ConstructorPrincipal extends Frame {

    SimpleUniverse simpleU;
    // camara de la persona
    TransformGroup vp_tg;
    // Ambiente
    Ambiente ambiente;

    // constructor
    public ConstructorPrincipal() {
        setSize(600, 600);
        setLocation(600, 0);
        setTitle("Museo - ConstructorPrincipal");
        setLayout(new BorderLayout());

        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        setVisible(true);

        simpleU = new SimpleUniverse(canvas3D);

        // Mueve el punto de vista un poco atrás para ver los objetos
        vp_tg = simpleU.getViewingPlatform().getViewPlatformTransform();


        Vector3f vp_vec = new Vector3f();
        Transform3D vp_t = new Transform3D();
        Transform3D rot_t = new Transform3D();
        vp_vec.set(0.0f, 62.0f, -20.0f);
        vp_t.setTranslation(vp_vec);
        rot_t.rotX(Math.PI / -2.0);
        vp_t.mul(rot_t);
        vp_tg.setTransform(vp_t);

        // Ambiente del museo
        ambiente = new Ambiente(this);

        // Creacion rama de contenido
        BranchGroup scene = createSceneGraph();

        simpleU.addBranchGraph(scene);


        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                dispose();
            }
        });


        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    // Funcion: createSceneGraph()
    // la rama de contenido
    public BranchGroup createSceneGraph() {

        BranchGroup objRoot = new BranchGroup();

        objRoot.addChild(ambiente.obtenerAmbiente());

        objRoot.compile();

        return objRoot;
    }
}
