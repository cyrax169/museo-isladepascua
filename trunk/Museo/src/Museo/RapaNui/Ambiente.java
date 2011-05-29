package Museo.RapaNui;

//Clase: Ambiente.java
//Esta clase se encarga de crear los muros (con textura y caja) y
//definir las coordenadas de dichos muros tanto exteriores e interiores,
//lo mismo realiza con el piso, del cielo (con esfera) y de además controla
//la iluminación presente en el museo.


import java.awt.Frame;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.image.TextureLoader;


// Clase, que produce el ambiente del museo
public class Ambiente {
	private TransformGroup ambiente_tg;
	private Transform3D t;
	private Vector3f vec;
	private BoundingSphere bounds;
	private Frame observador;
	Cargador carga_obj;
	SimpleTextureApp cuadro;
	
	// Posición de las paredes
private float posicion[][] = {//bordes del museo
        { 0.0f,   0.0f,   0.0f},
        {-20.0f,   0.0f, -20.0f},
        { 20.0f,   0.0f, -20.0f},
        { 0.0f,   0.0f, -40.0f},
        //paredes interiores
        //{ 20.0f,   0.0f, -9.5f},
        //{ -3.0f,  0.0f, -20.0f},
        { -3.0f,  0.0f, -5.0f},
        { -3.0f,  0.0f, -40.0f}};
        //{  3.0f,   0.0f, -20.0f},
        //{- 3.0f,   0.0f, -5.0f},
        //{  3.0f,   0.0f, -5.0f},
        //{- 3.0f,   0.0f, -35.0f},
        //{ 3.0f,   0.0f, -35.0f},
        //{- 11.0f,   0.0f, -20.0f},
        //{ 11.0f,   0.0f, -20.0f}};
																
	// Tamaño de las paredes														
	private float muroTamano[][] = {//tamaño de los bordes del museo
        {  19.0f,  2.0f,   1.0f},
        {  1.0f,   2.0f,  21.0f},
        {  1.0f,   2.0f,  21.0f},
        {  19.0f,  2.0f,   1.0f},
        //tamaño de las paredes interiores
        //{  0.99f,   2.0f,   10.0f},
        {  1.0f,   2.0f,   10.0f},
        {  1.0f,   2.0f,   20.0f}};
        //{  1.0f,   2.0f,   4.0f},
        //{  1.0f,   2.0f,   20.0f},
        //{  1.0f,   2.0f,   4.0f}};
        //{  1.0f,   2.0f,   4.0f},
        //{  1.0f,   2.0f,   4.0f},
        //{  8.0f,   2.0f,   1.0f},
        //{  8.0f,   2.0f,   1.0f}};
																
	// constructor
	public Ambiente(Frame observador) {
		ambiente_tg = new TransformGroup();
		t = new Transform3D();
		vec = new Vector3f();
		bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
		
		this.observador = observador;	
	}

	
	// Funcion: crearAmbiente() {
	public TransformGroup crearAmbiente() {		
		// Texturas para las paredes
		String fname = "muro.jpg";
		
		carga_obj = new Cargador();
		cuadro = new SimpleTextureApp();
		
		Appearance app = new Appearance();
		Texture tex = new TextureLoader(fname, observador).getTexture();
		app.setTexture(tex);
		
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		app.setTextureAttributes(texAttr);
		
		// Iluminacion paredes
		Material m = new Material();
		m.setLightingEnable(true);
		app.setMaterial(m);
		
		app.setColoringAttributes(new ColoringAttributes(0.5f, 0.5f, 0.5f, ColoringAttributes.NICEST));	
		
		// ciclo para crear los muros
		for (int i=0; i < muroTamano.length; i++) {
			
			Box muro = new Box(muroTamano[i][0], muroTamano[i][1], muroTamano[i][2], Box.GENERATE_TEXTURE_COORDS | Box.GENERATE_NORMALS, app);
			TransformGroup muro_tg = new TransformGroup();
			
			vec.set(posicion[i]);
			t.setTranslation(vec);
			muro_tg.setTransform(t);
			
			muro_tg.addChild(muro);
			
			// cada pared se agrega al objeto ambiente_tg
			ambiente_tg.addChild(muro_tg);			
		}
		
	// elementos restantes que el campo que juega agrega 
        ambiente_tg.addChild(crearPiso());
        ambiente_tg.addChild(crearTecho());
        ambiente_tg.addChild(crearCielo());
        ambiente_tg.addChild(crearLuz());
        
        //carga las estatuas
        /*ambiente_tg.addChild(carga_obj.Carga("venusm.obj", 11.5f, 0.0f, -18.0f));
        ambiente_tg.addChild(carga_obj.Carga("arbol.obj", 6.0f, 0.0f, -18.0f));
	ambiente_tg.addChild(carga_obj.Carga("fildefer2.obj",  12.0f, 0.0f, -10.5f));
	ambiente_tg.addChild(carga_obj.Carga("sculpture.obj",  12.0f, 0.0f, -2.0f));
	ambiente_tg.addChild(carga_obj.Carga("ateneam.obj",-18.0f,0.0f,-25.0f));
	ambiente_tg.addChild(carga_obj.Carga("ateneam.obj",-18.0f,0.0f,-35.0f));
	ambiente_tg.addChild(carga_obj.Carga("moai.obj",5.0f,0.0f,-25.0f));*/
	ambiente_tg.addChild(carga_obj.Carga("moai.obj",10f,-1.2f,-35.0f));
	ambiente_tg.addChild(carga_obj.Carga("moai.obj",-12.0f,0.0f,-10.5f));
		
	//cuadros en el pasillo
	/*	ambiente_tg.addChild(cuadro.createScene("cuadro_monalisa.gif",  -0.5f, 0.5f, 1.0f, 0.0f, -38.9f, -38.9f));//frente
		
		ambiente_tg.addChild(cuadro.createScene("Transfiguracion.gif",  1.99f, 1.99f, 1.0f, 0.0f, -35.0f, -34.0f));//derecha
		ambiente_tg.addChild(cuadro.createScene("sagrada_familia.gif",  1.99f, 1.99f, 1.0f, 0.0f, -25.0f, -24.0f));//derecha
		ambiente_tg.addChild(cuadro.createScene("mentiras.gif",         1.99f, 1.99f, 1.0f, 0.0f, -20.0f, -19.0f));//derecha
		ambiente_tg.addChild(cuadro.createScene("velazquez_aguero.gif", 1.99f, 1.99f, 1.0f, 0.0f, -15.0f, -14.0f));//derecha
		ambiente_tg.addChild(cuadro.createScene("picaso.gif",           1.99f, 1.99f, 1.0f, 0.0f, -6.0f, -5.0f));//derecha
		
		ambiente_tg.addChild(cuadro.createScene("trinidad_greco.gif",   -1.99f, -1.99f, 1.0f, 0.0f, -34.0f, -35.0f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("amante_coronado.gif",  -1.99f, -1.99f, 1.0f, 0.0f, -24.0f, -25.0f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("munch.gif",            -1.99f, -1.99f, 1.0f, 0.0f, -19.0f, -20.0f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("cristo_amarillo.gif",  -1.99f, -1.99f, 1.0f, 0.0f, -14.0f, -15.0f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("monet1.gif",           -1.99f, -1.99f, 1.0f, 0.0f, -5.0f, -6.0f));//izquierda
		
		ambiente_tg.addChild(cuadro.createScene("girasoles.gif", 0.5f,  -0.5f, 1.0f, 0.0f, -1.1f, -1.1f));//a la espalda
		
		//cuadros sala fondo derecha
		ambiente_tg.addChild(cuadro.createScene("monet.gif", 10.0f, 12.0f, 1.3f, -0.3f, -38.9f, -38.9f));//frente
		ambiente_tg.addChild(cuadro.createScene("cuadro_cena.gif", 18.9f, 18.9f, 1.3f, -0.3f, -30.5f, -28.5f));//derecha
		ambiente_tg.addChild(cuadro.createScene("atenas.gif", 12.0f, 10.0f, 1.3f, -0.3f, -21.1f, -21.1f));//espalda
		ambiente_tg.addChild(cuadro.createScene("isla2.gif",  4.1f, 4.1f, 1.0f, 0.0f, -36.0f, -37.0f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("isla2.gif",  4.1f, 4.1f, 1.0f, 0.0f, -33.0f, -34.0f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("isla.gif",  4.1f, 4.1f, 1.3f, -0.3f, -25.5f, -27.5f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("isla.gif",  4.1f, 4.1f, 1.3f, -0.3f, -22.0f, -24.0f));//izquierda
		
		//cuadros sala  derecha
		ambiente_tg.addChild(cuadro.createScene("cuadro2.gif",       8.0f, 9.0f, 1.0f, 0.0f, -18.9f, -18.9f));//frente
		ambiente_tg.addChild(cuadro.createScene("hijo.gif",          14.0f, 15.0f, 1.0f, 0.0f, -18.9f, -18.9f));//frente
		ambiente_tg.addChild(cuadro.createScene("monet2.gif",        18.9f, 18.9f, 1.0f, 0.0f, -7.0f, -6.0f));//derecha
		ambiente_tg.addChild(cuadro.createScene("jirafa.gif",        18.9f, 18.9f, 1.0f, 0.0f, -15.5f, -14.5f));//derecha
		ambiente_tg.addChild(cuadro.createScene("girasoles.gif",     18.9f, 18.9f, 1.0f, 0.0f, -4.0f, -3.0f));//derecha
		ambiente_tg.addChild(cuadro.createScene("jinetes.gif",       18.9f, 18.9f, 1.3f, -0.3f, -11.0f, -9.0f));//derecha
		
		//cuadros sala fondo izquierda
		ambiente_tg.addChild(cuadro.createScene("miguel_angel.gif", -12.0f, -10.0f, 1.3f, -0.3f, -38.9f, -38.9f));//frente
		ambiente_tg.addChild(cuadro.createScene("romeria.gif", -18.9f, -18.9f, 1.3f, -0.3f, -28.5f, -30.5f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("tres_bailarinas.gif", -10.0f, -12.0f, 1.3f, -0.3f, -21.1f, -21.1f));//espalda
		
		//cuadros sala izquierda
		ambiente_tg.addChild(cuadro.createScene("remb.gif", -12.0f, -11.0f, 1.0f, 0.0f, -18.9f, -18.9f));//frente
		ambiente_tg.addChild(cuadro.createScene("miguel_angel.gif", -9.0f, -7.0f, 1.3f, -0.3f, -18.9f, -18.9f));//frente
		ambiente_tg.addChild(cuadro.createScene("tres_bailarinas.gif", -16.0f, -14.0f, 1.3f, -0.3f, -18.9f, -18.9f));//frente
		ambiente_tg.addChild(cuadro.createScene("picaso2.gif", -18.9f, -18.9f, 1.0f, 0.0f, -14.5f, -15.5f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("manet.gif", -18.9f, -18.9f, 1.0f, 0.0f, -5.5f, -6.5f));//izquierda
		ambiente_tg.addChild(cuadro.createScene("picaso3.gif", -11.0f, -12.0f, 1.0f, 0.0f, -1.1f, -1.1f));//espalda
		ambiente_tg.addChild(cuadro.createScene("atenas.gif", -7.0f, -9.0f, 1.3f, -0.3f, -1.1f, -1.1f));//espalda
		ambiente_tg.addChild(cuadro.createScene("monet.gif", -14.0f, -16.0f, 1.3f, -0.3f, -1.1f, -1.1f));//espalda
	*/
		return ambiente_tg;
	}

	
	// Funcion: crearPiso()
    // produce la plataforma, en la cual el museo está parado
    public TransformGroup crearPiso() {
        
        //Carga de las texturas
        String fname = "piso.jpg";
        
        TransformGroup piso_tg = new TransformGroup();
        Appearance pisoApp = new Appearance();
        Texture tex = new TextureLoader(fname, observador).getTexture();
        
        pisoApp.setTexture(tex);
        
        //Objeto caja para el piso
        Box piso = new Box(31.0f, 0.001f, 31.0f, Box.GENERATE_TEXTURE_COORDS, pisoApp);
        
        // Posición de la plataforma
        vec.set(0.0f, -1.01f, -20.0f);
        t.setTranslation(vec);
        piso_tg.setTransform(t);
        piso_tg.addChild(piso);
        
        return piso_tg;
    }
    
    
    // Funcion: crearTecho()
    // produce la plataforma, para el techo del museo
    public TransformGroup crearTecho() {
        
        //Carga de las texturas
        String fname = "techo.jpg";
        
        TransformGroup techo_tg = new TransformGroup();
        Appearance techoApp = new Appearance();
        Texture tex = new TextureLoader(fname, observador).getTexture();
        
        techoApp.setTexture(tex);
        
        // producto de la plataforma
        Box techo = new Box(21.0f, 0.001f, 31.0f, Box.GENERATE_TEXTURE_COORDS, techoApp);
        
        // Posición de la plataforma
        vec.set(0.0f, 2.01f, -20.0f);
        t.setTranslation(vec);
        techo_tg.setTransform(t);
        techo_tg.addChild(techo);
        
        return techo_tg;
    }
    
    // Funcion: crearCielo()
    // produce el fondo (el cielo)
    public Background crearCielo() {
        String fname = "bg.jpg";
        
        Background bg = new Background();
        bg.setApplicationBounds(bounds);
        
        BranchGroup backGeoBranch = new BranchGroup();
        Sphere sphereObj = new Sphere(1.0f, Sphere.GENERATE_NORMALS |
                Sphere.GENERATE_NORMALS_INWARD |
                Sphere.GENERATE_TEXTURE_COORDS, 45);
        Appearance cieloApp = sphereObj.getAppearance();
        backGeoBranch.addChild(sphereObj);
        bg.setGeometry(backGeoBranch);
        
        TextureLoader tex = new TextureLoader(fname, observador);
        cieloApp.setTexture(tex.getTexture());
        
        return bg;
    }
    
    
    // Funcion: crearLuz()
    //tres fuentes de la luz se producen y que brillan para AmbientLight
    // en tres diversas intensidades y a partir de tres diversas posiciones
    // para iluminar las zonas, en las cuales ninguna luz de las otras
    // fuentes de la luz llega
    public TransformGroup crearLuz() {
        TransformGroup luz_tg = new TransformGroup();
        
        AmbientLight luzA = new AmbientLight();
        luzA.setInfluencingBounds(bounds);
        
        Color3f lColor1 = new Color3f(0.5f, 0.5f, 0.5f);
        Color3f lColor2 = new Color3f(0.6f, 0.6f, 0.6f);
        Color3f lColor3 = new Color3f(0.2f, 0.2f, 0.2f);
        
        Vector3f lDir1  = new Vector3f( 1.0f, 1.0f,  1.0f);
        Vector3f lDir2  = new Vector3f( 1.0f, 1.0f, -1.0f);
        Vector3f lDir3  = new Vector3f(-1.0f, 1.0f, 1.0f);
        
        DirectionalLight lgt1 = new DirectionalLight(lColor1, lDir1);
        DirectionalLight lgt2 = new DirectionalLight(lColor2, lDir2);
        DirectionalLight lgt3 = new DirectionalLight(lColor3, lDir3);
        
        lgt1.setInfluencingBounds(bounds);
        lgt2.setInfluencingBounds(bounds);
        lgt3.setInfluencingBounds(bounds);
        
        luz_tg.addChild(lgt1);
        luz_tg.addChild(lgt2);
        luz_tg.addChild(lgt3);
        
        luz_tg.addChild(luzA);
        
        return luz_tg;
    }
    
    
    // el ambiente creado se retorna  al diagrama de escena
    public TransformGroup obtenerAmbiente() {
        return crearAmbiente();
    }
}

