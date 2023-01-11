#version 150

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float GameTime;
uniform vec4 ColorModulator;

out vec4 vertexColor;

void main() {
    vec4 vertex = vec4(Position, 1.0);

    gl_Position = ProjMat * ModelViewMat * vertex;
    float dist = gl_Position.x + gl_Position.y;
    float size = 50 * (1 - ColorModulator[0]);
    float speed = 5000 * ColorModulator[1];
    float saturation = ColorModulator[2];

    if (size <= 0) {
        // Solid color
        dist = 0;
    }

    dist = dist * size;

    vec4 colorInbetween = (
        (.6 + .6 * cos((dist + GameTime * speed) + vec4(0, 23, 21, 0)))
    );

    if (saturation < 1) {
        float gray = colorInbetween[0] * 0.299 + colorInbetween[1] * 0.587 + colorInbetween[2] * 0.114;
        if (saturation > 0) {
            colorInbetween[0] = (gray * (1 - saturation)) + (colorInbetween[0] * saturation);
            colorInbetween[1] = (gray * (1 - saturation)) + (colorInbetween[1] * saturation);
            colorInbetween[2] = (gray * (1 - saturation)) + (colorInbetween[2] * saturation);
        } else {
            colorInbetween = vec4(gray, gray, gray, 1);
        }
    }
    colorInbetween[3] = ColorModulator[3];
    vertexColor = colorInbetween;
}