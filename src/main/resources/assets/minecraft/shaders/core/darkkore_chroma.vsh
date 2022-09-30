#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float GameTime;

out vec4 vertexColor;

void main() {
    vec4 vertex = vec4(Position, 1.0);

    gl_Position = ProjMat * ModelViewMat * vertex;
    float dist = gl_Position.x + gl_Position.y;
    float size = 30 * Color[0];
    float speed = 1000 * Color[1];
    float saturation = Color[2];

    vec4 colorInbetween = (
        (.6 + .6 * cos(size * (dist + GameTime * speed) + vec4(0, 23, 21, 0)))
    );
    colorInbetween[0] = colorInbetween[0] * saturation;
    colorInbetween[1] = colorInbetween[1] * saturation;
    colorInbetween[2] = colorInbetween[2] * saturation;
    colorInbetween[3] = Color[3];
    vertexColor = colorInbetween;
}