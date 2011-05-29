package Museo.RapaNui;

//Clase: Cargador.java
//Esta clase se utiliza como loader de objetos en 3D en formato OBJ (WaveFront),
//se define un objeto en el cual se guardará el archivo, cambiar la escala de 
//la imagen para que ocupe todo el espacio,  además posee métodos para controlar
//los errores en la localización de archivos y se define la apariencia modificando
//los atributos de los polígonos que componen la figura.

//Librerias Importadas
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import java.awt.event.*;
import java.awt.AWTEvent;
import java.awt.*;
import java.util.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.applet.MainFrame; 
import java.applet.Applet;
import com.sun.j3d.utils.geometry.ColorCube;



public class Cargador extends Applet{
    
   // Creates a una nueva instancia de cargador 
  
    
    //Constructor
    public TransformGroup Carga(String nombre, float x, float y, float z) {
        
        //Crea objeto transformgroup
        TransformGroup objTrans = new TransformGroup();
        Vector3f vec;
        Transform3D t;
        
		//Nombre del archivo obj a cargar 
		String nombreURL = nombre;
		
		//Para poder modificar el objeto TransformGroup
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        //Da informacion sobre los objetos que dependen del TransformGroup
        objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
     
		//Construir un objeto para introducir una imagen de fondo de otra imagen
		Background bg = new Background(new Color3f(0.5f,0.5f,0.5f));
		bg.setApplicationBounds(new BoundingSphere(new Point3d(),100.0));
	
		//imagen ocupe todo el espacio asignado
		int flags = ObjectFile.RESIZE;
		ObjectFile f = new ObjectFile(flags);
        
        Scene s = null;
      	
      	//control errores en la localizacion de archivos
        try {
        	s = f.load(nombreURL);
            
        }
        catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        Group sceneGroup = s.getSceneGroup();
		Hashtable namedObjects = s.getNamedObjects();
		Enumeration e =  namedObjects.keys();
		
		while (e.hasMoreElements()) {
		    String name = (String) e.nextElement();
		    Shape3D shape = (Shape3D) namedObjects.get(name);
		    shape.setAppearance(app());
		}
		
		vec = new Vector3f();
		t = new Transform3D();
		
		vec.set(x, y, z);
		t.setTranslation(vec);
		objTrans.setTransform(t);
	
		//agregarlos como hijo al transformgroup
		objTrans.addChild(bg);
		objTrans.addChild(s.getSceneGroup());
	
		return objTrans;
	}//Fin del Constructor 
    
    
    //Funcion para definir apariencia del objeto
    //en este caso poligono
	private Appearance app() {
	
		Appearance app=new Appearance();
	
		PolygonAttributes polyAtt=new PolygonAttributes();
		polyAtt.setCullFace(PolygonAttributes.CULL_NONE);
		//Poligono compacto   
		polyAtt.setPolygonMode(PolygonAttributes.POLYGON_FILL);  
		polyAtt.setPolygonOffset(1.0f);
		polyAtt.setBackFaceNormalFlip(true);  
		app.setPolygonAttributes(polyAtt);
		
		Material mat=new Material();	
		app.setMaterial(mat);
	
		return app;
	}
}

    

