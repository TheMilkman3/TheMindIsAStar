#version 330 core

in vec2 UV;

out vec3 color;

uniform sampler2D tex;

void main(){
	color = vec3(1.0, 0.0, 0.0);
}