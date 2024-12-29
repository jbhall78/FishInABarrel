import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

public class Textured2DShader extends Shader {
	public void init() {
	    // Load and compile shaders
        int vertexShader = Shader.createShader(GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = Shader.createShader(GL_FRAGMENT_SHADER, fragmentShaderSource);
        shaderProgram = glCreateProgram();
        glAttachShader(shaderProgram, vertexShader);
        glAttachShader(shaderProgram, fragmentShader);
        glLinkProgram(shaderProgram);

        if (glGetProgrami(shaderProgram, GL_LINK_STATUS) == GL_FALSE) {
            throw new RuntimeException("Failed to link shader program: " + glGetProgramInfoLog(shaderProgram));
        }

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        
        modelLocation = glGetUniformLocation(shaderProgram, "model");
        projectionLocation = glGetUniformLocation(shaderProgram, "projection");
 	}
	
    // Vertex shader source
    private final String vertexShaderSource = """
                #version 330 core
                layout (location = 0) in vec2 position;
                layout (location = 1) in vec2 texCoord;
        		uniform mat4 model;
                uniform mat4 projection;
                out vec2 vTexCoord;
                void main() {
                    vTexCoord = texCoord;
                    gl_Position = model * projection * vec4(position, 0.0, 1.0);
                }
    """;

    // Fragment shader source
    private final String fragmentShaderSource = """
               #version 330 core
                in vec2 vTexCoord;
                out vec4 FragColor;
                uniform sampler2D texture1;
                void main() {
                    FragColor = texture(texture1, vTexCoord);
                }
    """;
}