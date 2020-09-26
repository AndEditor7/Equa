#version 100
#ifdef GL_ES
#define LOWP lowp
#define MEDIUM mediump
precision highp float;
#else
#define LOWP
#define MEDIUM
#endif

attribute vec4 a_position;
attribute LOWP vec4 a_data;
attribute MEDIUM vec2 a_texCoord;

uniform mat4 u_projTrans;
uniform LOWP float sunLightIntensity;
uniform LOWP float brightness;
uniform float toggleAO;

varying LOWP float v_shade;
varying LOWP float v_light;
varying MEDIUM vec2 v_texCoords;

// data[sideLight&Ambiant, source-light, skylight, unused]
void main()
{
	v_light = clamp(mix(a_data.y + a_data.z * sunLightIntensity, 1.0, brightness), 0.0, 1.0);
	v_shade = 1.0;
	if(toggleAO == 1.0) v_shade = a_data.x;
	v_texCoords = a_texCoord;
	gl_Position = u_projTrans * a_position;
}
