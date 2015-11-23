#version 330 core
layout(location = 0) in vec3 vertexPosition_modelspace;

uniform mat4 mvp;

out vec2 UV;

void main(){
	gl_Position = mvp * vec4(vertexPosition_modelspace, 1);
	UV = vertexPosition_modelspace.xy;
}