#version 120
//uniform bool ambient, diffuse, specular;
varying vec3 N, P, LP;

void main() {
 // pick up light LIGHT0 and material properties set in ShaderMaker
 //gl_LightSourceParameters light = gl_LightSource[0];
 //gl_MaterialParameters mat = gl_FrontMaterial;
 N = normalize(gl_NormalMatrix * gl_Normal); // TODO: transform normal vector to view space
 P = (gl_ModelViewMatrix * gl_Vertex).xyz / gl_Vertex.w; // TODO: compute vertex position in 3-D view coordinates
 vec4 lph = /* transform to view coordinate */ (gl_LightSource[0].position) ; // light position homogeneous
 LP = lph.xyz / lph.w;
// output of vertex shader
 gl_TexCoord[0] = gl_MultiTexCoord0;
 //gl_FrontColor = shading(P, N, light, mat); // calculated in fragment
 gl_Position = gl_ProjectionMatrix * gl_ModelViewMatrix * gl_Vertex;
}