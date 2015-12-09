#version 330 core
layout(location = 0) in vec3 vertexPosition_modelspace;
layout(location = 1) in vec2 vertexUV;

uniform mat4 mvp;
uniform vec2 uv_start;
uniform vec2 uv_scale;
uniform vec2 uv_offset;

out vec2 UV;

void main(){
	UV.x = (uv_start.x * uv_scale.x) + (vertexUV.x * uv_scale.x) - uv_offset.x;
	UV.y = (uv_start.y * uv_scale.y) + (vertexUV.y * uv_scale.y) + uv_offset.y;
	gl_Position = mvp * vec4(vertexPosition_modelspace, 1);
}