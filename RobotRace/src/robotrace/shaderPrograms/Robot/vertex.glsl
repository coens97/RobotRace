#version 120
//uniform bool ambient, diffuse, specular;
varying vec3 N, P;

void main() {
 // pick up light LIGHT0 and material properties set in ShaderMaker
 //gl_LightSourceParameters light = gl_LightSource[0];
 //gl_MaterialParameters mat = gl_FrontMaterial;
 N = normalize(gl_NormalMatrix * gl_Normal); // TODO: transform normal vector to view space
vec4 P4 = gl_ModelViewMatrix * gl_Vertex;
 P = P4.xyz / P4.w; // TODO: compute vertex position in 3-D view coordinates
// output of vertex shader
 gl_TexCoord[0] = gl_MultiTexCoord0;
 //gl_FrontColor = shading(P, N, light, mat); // calculated in fragment
 gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * gl_Vertex;
}