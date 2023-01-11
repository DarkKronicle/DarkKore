#version 150

in vec3 Position;
in vec4 UV0;
in vec4 Color;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec4 ColorModulator;

out vec4 vertexColor;
out vec2 texCoord0;
out float innerRad;

void main() {
    vec4 vertex = vec4(Position, 1.0);
    gl_Position = ProjMat * ModelViewMat * vertex;
    innerRad = ColorModulator[0];
    vertexColor = UV0;
    texCoord0 = Color.xy;
}