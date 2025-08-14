package me.johanrong.glare.render

import me.johanrong.glare.util.loadPlain

object Shader {
    const val VERSION = "#version 460"

    const val VERTEX_HEADER = """
        layout(location = 0) in vec3 position;
        layout(location = 1) in vec2 texCoord;
        layout(location = 2) in vec3 normal;
        
        out vec3 FragPos;
        out vec2 TexCoords;
        out vec3 Normal;
        
        uniform mat4 transformMatrix;
        uniform mat4 projectionMatrix;
        uniform mat4 viewMatrix;
    """

    const val FRAGMENT_HEADER = """ 
        in vec3 FragPos;
        in vec2 TexCoords;
        in vec3 Normal;
        
        out vec4 FragColor;
        
        uniform sampler2D textureSampler;
        uniform int hasTexture;
        uniform vec3 uViewPos;
        
        // Material properties
        struct Material {
            vec3 diffuse;
            vec3 specular;
            float shininess;
        };
        uniform Material material;
        
        // Light structures
        struct DirLight {
            vec3 direction;
            vec3 color;
            float intensity;
        };
        
        struct PointLight {
            vec3 position;
            vec3 color;
            float intensity;
            float constant;
            float linear;
            float quadratic;
        };
        
        // Light arrays
        #define MAX_DIR_LIGHTS 4
        #define MAX_POINT_LIGHTS 16
        
        uniform int uNumDirLights;
        uniform int uNumPointLights;
        
        uniform DirLight uDirLights[MAX_DIR_LIGHTS];
        uniform PointLight uPointLights[MAX_POINT_LIGHTS];
    """

    const val GEOMETRY_HEADER = """
    """

    const val CONTROL_HEADER = """
    """

    const val EVAL_HEADER = """
    """

    const val COMPUTE_HEADER = """
    """

    const val VERTEX_FUNCTIONS = """
        void glare_vertex() {
            vec4 worldPosition = transformMatrix * vec4(position, 1.0);
            gl_Position = projectionMatrix * viewMatrix * worldPosition;
            TexCoords = texCoord;
            Normal = normal;
            FragPos = vec3(worldPosition);
        }
    """

    const val FRAGMENT_FUNCTIONS = """
        vec3 calculatePointLight(PointLight light, vec3 normal, vec3 viewDir) {
            vec3 lightDir = normalize(light.position - FragPos);
            float dist = distance(light.position, FragPos);
            float attenuation = 1.0 / ((light.constant + light.linear * dist + light.quadratic * (dist * dist)));
        
            // Diffuse
            float diff = max(dot(normal, lightDir), 0.0);
        
            // Specular
            vec3 reflectDir = reflect(-lightDir, normal);
            float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
        
            vec3 ambient = light.color * light.intensity * 0.01 * attenuation; // Adding attenuation here is not the solution
            vec3 diffuse = light.color * light.intensity * diff * attenuation;
            vec3 specular = light.color * light.intensity * spec * attenuation;
        
            return (ambient + diffuse) * material.diffuse + specular * material.specular;
        }
        
        void glare_light() {
            vec3 normal = normalize(Normal);
            vec3 viewDir = normalize(uViewPos - FragPos);
            vec3 result = vec3(0.05);
        
            // Calculate directional lights
            //for (int i = 0; i < uNumDirLights; i++) {
            //    result += calculateDirectionalLight(uDirLights[i], normal, viewDir);
            //}
        
            // Calculate point lights
            for (int i = 0; i < uNumPointLights; i++) {
                result += calculatePointLight(uPointLights[i], normal, viewDir);
            }
        
            FragColor = vec4(result, 1.0);//* vec4(material.diffuse, 1.0);
        }
        
        void glare_texture() {
            //if (FragColor == vec4(0.0, 0.0, 0.0, 0.0)) {
            //    FragColor = vec4(1.0, 1.0, 1.0, 1.0);
            //}
        
            if (hasTexture == 1) {
                FragColor *= texture(textureSampler, TexCoords);
            //} else {
            //    FragColor *= vec4(1.0, 1.0, 1.0, 1.0); // Default color if no texture
            }
        }
    """

    fun makeVertex(str: String): String {
        var shader = loadPlain(str)
        shader = VERSION + VERTEX_HEADER + VERTEX_FUNCTIONS + shader
        return shader
    }

    fun makeFragment(str: String): String {
        var shader = loadPlain(str)
        shader = VERSION + FRAGMENT_HEADER + FRAGMENT_FUNCTIONS + shader
        return shader
    }

    fun makeGeometry(str: String): String {
        var shader = loadPlain(str)
        shader = VERSION + GEOMETRY_HEADER + shader
        return shader
    }

    fun makeControl(str: String): String {
        var shader = loadPlain(str)
        shader = VERSION + CONTROL_HEADER + shader
        return shader
    }

    fun makeEval(str: String): String {
        var shader = loadPlain(str)
        shader = VERSION + EVAL_HEADER + shader
        return shader
    }

    fun makeCompute(str: String): String {
        var shader = loadPlain(str)
        shader = VERSION + COMPUTE_HEADER + shader
        return shader
    }
}