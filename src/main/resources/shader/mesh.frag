in vec2 fragTexCoord;

out vec4 fragColor;

uniform sampler2D textureSampler;
uniform int hasTexture;

void main() {
    if (hasTexture == 1) {
        fragColor = texture(textureSampler, fragTexCoord);
    } else {
        fragColor = vec4(1.0, 1.0, 1.0, 1.0);
    }
}