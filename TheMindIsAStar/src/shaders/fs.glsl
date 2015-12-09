#version 330 core

in vec2 UV;

out vec4 color;

uniform sampler2D tex;
uniform vec4 blend_color;

void main(){
	vec4 color_mult = 1 - (blend_color / 256.0);
	color = texture(tex, UV).rgba - color_mult.rgba;
}