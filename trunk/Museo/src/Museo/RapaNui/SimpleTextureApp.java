package Museo.RapaNui;

//Clase: SimpleTextureApp
//Esta clase se ocupa para cargar las imágenes GIF que componen los cuadros del museo
//Para implementar esta clase se procedió a Preparar las Imágenes de Texura, Cargar
//la Textura, configurar la textura en Appearance y por último especificar las TextureCoordinates
//del Geometry. Se crea un solo plano usando un objeto de geometría QuadArray (cuadrángulo
//en tres dimensiones). Las coordenadas de la textura se asignan a cada vértice del cuadrángulo.


import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.media.j3d.*;
import javax.vecmath.*;



public class SimpleTextureApp extends Applet {
    
    TransformGroup createScene(String nombre, float x1, float x2, float y1, float y2, float z1, float z2) {
        TransformGroup objRoot = new TransformGroup();
        
        Transform3D transform = new Transform3D();
        
        QuadArray plane = new QuadArray(4, GeometryArray.COORDINATES
                | GeometryArray.TEXTURE_COORDINATE_2);
        
        Point3f p = new Point3f(x1,  y1,  z1);
        plane.setCoordinate(0, p);
        p.set(x1, y2,  z1);
        plane.setCoordinate(1, p);
        p.set(x2, y2,  z2);
        plane.setCoordinate(2, p);
        p.set(x2,  y1,  z2);
        plane.setCoordinate(3, p);
        
        Point2f q = new Point2f( 0.0f,  1.0f);
        plane.setTextureCoordinate(0, q);
        q.set(0.0f, 0.0f);
        plane.setTextureCoordinate(1, q);
        q.set(1.0f, 0.0f);
        plane.setTextureCoordinate(2, q);
        q.set(1.0f, 1.0f);
        plane.setTextureCoordinate(3, q);
        
        Appearance appear = new Appearance();
        
        String filename = nombre;
        TextureLoader loader = new TextureLoader(filename, null);
        ImageComponent2D image = loader.getImage();
        
        if(image == null) {
            System.out.println("load failed for texture: "+filename);
        }
        
        Texture2D texture = new Texture2D(Texture.BASE_LEVEL, Texture.RGBA,
                image.getWidth(), image.getHeight());
        texture.setImage(0, image);
        
        appear.setTexture(texture);
        
        appear.setTransparencyAttributes(
                new TransparencyAttributes(TransparencyAttributes.FASTEST, 0.1f));
        
        Shape3D planeObj = new Shape3D(plane, appear);
        objRoot.addChild(planeObj);
        
        Background background = new Background();
        background.setColor(1.0f, 1.0f, 1.0f);
        background.setApplicationBounds(new BoundingSphere());
        
        objRoot.addChild(background);
        
        return objRoot;
    }
}
