//uniform bool ambient, diffuse, specular;
varying vec3 N, P;

vec4 shading(vec3 P, vec3 N, gl_MaterialParameters mat) {
 vec4 result = vec4(0.0, 0.0, 0.0, 1.0); // opaque black
 for(int i=0; i<3;i++){ // loop through the lights

  gl_LightSourceParameters light = gl_LightSource[i];

  
  //if (ambient) {
   result += mat.ambient * light.ambient; // add ambient color to sum
  //}

 

  vec3 L = normalize(light.position.xyz - P); // vector towards light source
  float cosTheta = max(0.0, dot(N, L)); // angle between the normal and the light source
  //if (diffuse) {
   result += mat.diffuse * light.diffuse * cosTheta; // sum the color of diffuse times the intensity
  //}
 
  //if (specular) {
   vec3 E = vec3(0.0); // position of camera in View space
   vec3 V = normalize(E - P); // direction towards viewer
   vec3 iL = normalize(-1.0 * L); // vector comming from light source
   vec3 reflection = normalize(iL - 2.0 * dot(iL, N) * N); //http://math.stackexchange.com/questions/13261/how-to-get-a-reflection-vector
   float alpha = max(0.0, dot(reflection, V)); // alpha like the one in the image on page 28 shading.pdf
   result += mat.specular * light.specular * pow(alpha, mat.shininess);
  //}
 }
 return result;
}

void main()
{
	//gl_LightSourceParameters light = gl_LightSource[0];
	gl_MaterialParameters mat = gl_FrontMaterial;
	gl_FragColor = shading(P, N, mat);
}
