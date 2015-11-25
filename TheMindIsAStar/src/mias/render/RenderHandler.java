package mias.render;

import com.jogamp.nativewindow.util.Dimension;
import com.jogamp.newt.Display;
import com.jogamp.newt.NewtFactory;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.math.FloatUtil;
import com.jogamp.opengl.math.Matrix4;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.GLBuffers;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.util.Collections;
import java.util.LinkedList;

import mias.TheMindIsAStar;
import mias.render.util.MatrixStack;

public class RenderHandler implements GLEventListener,  KeyListener {
	
	private static RenderHandler instance;
	
	private int framesPerSecond = 60;
	
	private Display display;
	private Screen screen;
	private Dimension windowSize = new Dimension(1024, 768);
	private GLProfile glProfile;
	private GLCapabilities glCapabilities;
	private GLWindow glWindow;
	private TextureRegistry textureRegistry;
	private int[] vertexArrayID;
	private float[] rectVertexBufferData = {
			0f, 0f, 0.0f,
			1.0f, 0f, 0.0f,
			0f, 1.0f, 0.0f,
			1.0f, 0f, 0.0f,
			1.0f, 1.0f, 0.0f,
			0f, 1.0f, 0.0f,
			0f, 0f,
			1.0f, 0f,
			0f, 1.0f,
			1.0f, 0f,
			1.0f, 1.0f,
			0f, 1.0f,
	};
	private LinkedList<GUIWindow> guiWindows = new LinkedList<GUIWindow>();
	
	int[] rectVertexBuffer = new int[1];
	int[] vertexBuffer = new int[1];
	int[] textureID = new int[1];
	public FPSAnimator animator;
	MatrixStack modelStack = new MatrixStack();
	MatrixStack viewStack = new MatrixStack();
	int mvpLocation;
	int samplerLocation;

	private int program;
	
	private final String SHADERS_ROOT = "/shaders";
	
	public RenderHandler() {
		display = NewtFactory.createDisplay(null);
		screen = NewtFactory.createScreen(display, 0);
		glProfile = TheMindIsAStar.GL_PROFILE;
		glCapabilities = new GLCapabilities(glProfile);
		glWindow = GLWindow.create(screen, glCapabilities);
		
		glWindow.setSize(windowSize.getWidth(), windowSize.getHeight());
		glWindow.setPosition(50, 50);
		glWindow.setUndecorated(false);
		glWindow.setAlwaysOnTop(false);
		glWindow.setFullscreen(false);
		glWindow.setPointerVisible(true);
		glWindow.confinePointer(false);
		glWindow.setVisible(true);
		glWindow.addKeyListener(this);
		glWindow.setAutoSwapBufferMode(false);
		glWindow.addGLEventListener(this);
		glWindow.setTitle("The Mind Is A Star");
		animator = new FPSAnimator(glWindow, framesPerSecond);
		
		instance = this;
	}
	
	public static RenderHandler instance() {
		return instance;
	}
	
	public void start() {
		animator.start();
	}
	
	public void setFramesPerSecond(int f) {
		framesPerSecond = f;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			animator.stop();
			glWindow.destroy();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable drawable) {
		textureRegistry = TextureRegistry.instance();
		textureRegistry.registerTextures();
		textureRegistry.loadRegisteredTextures();
		GL4 gl4 = drawable.getGL().getGL4();
		vertexArrayID = new int[1];
		gl4.glGenVertexArrays(1, vertexArrayID, 0);
		gl4.glBindVertexArray(vertexArrayID[0]);
		gl4.glGenBuffers(1, rectVertexBuffer, 0);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, rectVertexBuffer[0]);
		gl4.glBufferData(GL4.GL_ARRAY_BUFFER, GLBuffers.SIZEOF_FLOAT * rectVertexBufferData.length, 
				GLBuffers.newDirectFloatBuffer(rectVertexBufferData), GL4.GL_STATIC_DRAW);
		gl4.glVertexAttribPointer(0, 3, GL4.GL_FLOAT, false, 0, 0);
		gl4.glVertexAttribPointer(1, 2, GL4.GL_FLOAT, false, 0, 18 * GLBuffers.SIZEOF_FLOAT);
		
		initProgram(gl4);
		gl4.glUseProgram(program);
		mvpLocation = gl4.glGetUniformLocation(program, "mvp");
		samplerLocation = gl4.glGetUniformLocation(program, "tex");
		
		gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MAG_FILTER, GL4.GL_NEAREST);
		gl4.glTexParameteri(GL4.GL_TEXTURE_2D, GL4.GL_TEXTURE_MIN_FILTER, GL4.GL_NEAREST);
		
		Matrix4 viewMatrix = new Matrix4();
		Matrix4 perspectiveMatrix = new Matrix4();
		float[] eye = {0, 0, 5};
		float[] center = {0f, 0f, 0f};
		float[] up = {0f, 1.0f, 0f};
		Matrix4 mat4Tmp = new Matrix4();
		FloatUtil.makeLookAt(viewMatrix.getMatrix(), 0, eye, 0, center, 0, up, 0, mat4Tmp.getMatrix());
		perspectiveMatrix.makePerspective((float)Math.toRadians(60.0), 
				(float)windowSize.getWidth() / (float)windowSize.getHeight(), 0.1f, 100.0f);
		this.pushView(viewMatrix);
		this.pushView(perspectiveMatrix);
	}

	@Override
	public void dispose(GLAutoDrawable drawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		GL4 gl4 = drawable.getGL().getGL4();
		gl4.glClear(GL4.GL_COLOR_BUFFER_BIT | GL4.GL_DEPTH_BUFFER_BIT);
		gl4.glEnableVertexAttribArray(0);
		gl4.glEnableVertexAttribArray(1);
		for (GUIWindow guiW : guiWindows) {
			if (guiW.active){
				guiW.draw(gl4);
			}
		}
		gl4.glDisableVertexAttribArray(0);
		gl4.glDisableVertexAttribArray(1);
		drawable.swapBuffers();
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		
	}
	
	private void initProgram(GL4 gl4) {
        ShaderCode vertShader = ShaderCode.create(gl4, GL_VERTEX_SHADER, this.getClass(),
                SHADERS_ROOT, null, "vs", "glsl", null, true);
        ShaderCode fragShader = ShaderCode.create(gl4, GL_FRAGMENT_SHADER, this.getClass(),
                SHADERS_ROOT, null, "fs", "glsl", null, true);

        ShaderProgram shaderProgram = new ShaderProgram();
        shaderProgram.add(vertShader);
        shaderProgram.add(fragShader);

        shaderProgram.init(gl4);

        program = shaderProgram.program();

        /**
         These links don't go into effect until you link the program. If you want 
         to change index, you need to link the program again.
         */
        gl4.glBindAttribLocation(program, 0, "position");

        shaderProgram.link(gl4, System.out);
        /**
         Take in account that JOGL offers a GLUniformData class, here we don't 
         use it, but take a look to it since it may be interesting for you.
         */
    }
	
	public void drawTexturedRectangle(GL4 gl4, Matrix4 mvpMatrix, float xOffset, float yOffset, float zOffset, float xScaling, float yScaling) {
		scale(xScaling, yScaling, 1f);
		this.drawTexturedRectangle(gl4, mvpMatrix, xOffset, yOffset, zOffset);
		popModel();
	}
	
	public void drawTexturedRectangle(GL4 gl4, Matrix4 mvpMatrix, float xOffset, float yOffset, float zOffset) {
		translate(xOffset, yOffset, zOffset);
		this.drawTexturedRectangle(gl4, mvpMatrix);
		popModel();
	}
	
	public void drawTexturedRectangle(GL4 gl4, Matrix4 mvpMatrix) {
		gl4.glUniform1i(samplerLocation, 0);
		gl4.glUniformMatrix4fv(mvpLocation, 1, false, mvpMatrix.getMatrix(), 0);
		gl4.glBindBuffer(GL4.GL_ARRAY_BUFFER, rectVertexBuffer[0]);
		gl4.glDrawArrays(GL4.GL_TRIANGLES, 0, 6);
	}
	
	public void translate(float x, float y, float z) {
		Matrix4 m = new Matrix4();
		m.translate(x, y, z);
		modelStack.push(m);
	}
	
	public void scale(float x, float y, float z) {
		Matrix4 m = new Matrix4();
		m.scale(x, y, z);
		modelStack.push(m);
	}
	
	public Matrix4 popModel() {
		return modelStack.pop();
	}
	
	public void pushView(Matrix4 m) {
		viewStack.push(m);
	}
	
	public Matrix4 popView() {
		return viewStack.pop();
	}
	
	public void addGUIWindow(GUIWindow guiW){
		guiWindows.add(guiW);
		Collections.sort(guiWindows);
		guiW.setRenderHandler(this);
	}
	
	public void removeGUIWindow(GUIWindow guiW){
		guiWindows.remove(guiW);
		guiW.setRenderHandler(null);
	}
	
	public void sortGUIWindows(){
		Collections.sort(guiWindows);
	}
}
