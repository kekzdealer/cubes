#version 330 core

in vec2 pass_texCoords;

out vec4 out_Color;

uniform sampler2D diffuse;

void main(){

	vec4 texel = texture(diffuse, pass_texCoords);
	if(texel.a < 0.5) {
		discard;
	}

	out_Color = texel;

}