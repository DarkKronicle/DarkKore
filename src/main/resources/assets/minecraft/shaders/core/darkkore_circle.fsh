#version 150

in vec4 vertexColor;
in vec2 texCoord0;
in float innerRad;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    float x = (texCoord0.x - .5) * 2;
    float y = (texCoord0.y - .5) * 2;
    float calc = x * x + y * y;

    if (calc > 1 || calc < innerRad) {
        discard;
    }

    float d = length(vec2(x, y));
    float wd = d * .02; // <=> float wd = fwidth(d);
    float circle = smoothstep(1 + wd, 1 - wd, d);
    if (circle > .99 && innerRad > 0 && d - innerRad < .3) {
        circle = smoothstep(1 - .3, 1, d / innerRad);
    }
    fragColor = vec4(vertexColor.rgb, vertexColor.a * circle);
}