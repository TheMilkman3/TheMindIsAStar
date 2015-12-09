#version 330 core
layout(location = 0) in vec3 vertexPosition_modelspace;
layout(location = 1) in vec2 vertexUV;

uniform mat4 mvp;
uniform float uv_x_start = 0.0;
uniform float uv_y_start = 0.0;
uniform float uv_x_scale = 1.0;
uniform float uv_y_scale = 1.0;

out vec2 UV;

void main(){
	UV.x = (uv_x_start + vertexUV.x) * uv_x_scale;
	UV.y = (uv_y_start + vertexUV.y) * uv_y_scale;
	gl_Position = mvp * vec4(vertexPosition_modelspace, 1);
}