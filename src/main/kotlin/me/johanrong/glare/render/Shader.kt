package me.johanrong.glare.render

import me.johanrong.glare.util.loadPlain

object Shader {
    const val VERSION = "#version 460"

    const val VERTEX_HEADER = """
        layout(location = 0) in vec3 aPosition;
        layout(location = 1) in vec2 aTexCoord;
        layout(location = 2) in vec3 aNormal;
        
        uniform mat4 uModelMatrix;
        uniform mat4 uViewMatrix;
        uniform mat4 uProjectionMatrix;
        
        out vec3 FragPos;
        out vec2 TexCoords;
        out vec3 Normal;
    """

    const val FRAGMENT_HEADER = """
        in vec3 FragPos;
        in vec2 TexCoords;
        in vec3 Normal;
        
        out vec4 FragColor;
        
        uniform vec3 uViewPos;
        
        // Material properties
        struct Material {
            sampler2D diffuse;
            sampler2D specular;
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

    const val LIGHTING_FUNCTIONS = """
        vec3 calculateDirectionalLight(DirLight light, vec3 normal, vec3 viewDir) {
            vec3 lightDir = normalize(-light.direction);
            
            // Diffuse
            float diff = max(dot(normal, lightDir), 0.0);
            
            // Specular
            vec3 reflectDir = reflect(-lightDir, normal);
            float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);
            
            // Combine
            vec3 ambient = light.color * light.intensity * 0.1;
            vec3 diffuse = light.color * light.intensity * diff;
            vec3 specular = light.color * light.intensity * spec;
            
            return (ambient + diffuse) * texture(material.diffuse, TexCoords).rgb + 
                   specular * texture(material.specular, TexCoords).rgb;
        }
        
        vec3 calculatePointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDir) {
            vec3 lightDir = normalize(light.position - fragPos);
            float distance = length(light.position - fragPos);
            float attenuation = 1.0 / (light.constant + light.linear * distance + light.quadratic * (distance * distance));
            
            // Diffuse
            float diff = max(dot(normal, lightDir), 0.0);
            
            // Specular
            vec3 reflectDir = reflect(-lightDir, normal);
            float spec = pow(max(dot(viewDir, reflectDir), 0.0), 1);//material.shininess);
            
            // Combine
            vec3 ambient = light.color * light.intensity * 0.1;
            vec3 diffuse = light.color * light.intensity * diff * attenuation;
            vec3 specular = light.color * light.intensity * spec * attenuation;
            
            return (ambient + diffuse) /* * texture(material.diffuse, TexCoords).rgb */ + 
                   specular /* * texture(material.specular, TexCoords).rgb*/;
        }
        
        void light() {
            vec3 normal = normalize(Normal);
            vec3 viewDir = normalize(uViewPos - FragPos);
            vec3 result = vec3(0.0);
            
            // Calculate directional lights
            for (int i = 0; i < uNumDirLights; i++) {
                result += calculateDirectionalLight(uDirLights[i], normal, viewDir);
            }
            
            // Calculate point lights
            for (int i = 0; i < uNumPointLights; i++) {
                result += calculatePointLight(uPointLights[i], normal, FragPos, viewDir);
            }
            
            FragColor = vec4(result, 1.0) ;//* texture(material.diffuse, TexCoords);
        }
    """

    fun makeVertex(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + VERTEX_HEADER + shader
        return shader
    }

    fun makeFragment(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + FRAGMENT_HEADER + LIGHTING_FUNCTIONS + shader
        return shader
    }

    fun makeGeometry(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + GEOMETRY_HEADER + shader
        return shader
    }

    fun makeControl(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + CONTROL_HEADER + shader
        return shader
    }

    fun makeEval(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + EVAL_HEADER + shader
        return shader
    }

    fun makeCompute(path: String): String {
        var shader = loadPlain(path)
        shader = VERSION + shader
        return shader
    }
}