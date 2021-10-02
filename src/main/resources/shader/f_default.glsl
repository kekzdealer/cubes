#version 330 core

in vec2 uv_for_fragment;

out vec4 out_Color;

uniform sampler2D diffuse;

void main(){

	vec4 texel = texture(diffuse, uv_for_fragment);
	if(texel.a < 0.5) {
		discard;
	}

	out_Color = texel;

}