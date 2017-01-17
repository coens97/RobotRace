// simple vertex shader
uniform float tAnim;
void main()
{
    float x = gl_Vertex.x;
    float y =  gl_Vertex.y;
    float z = 0.3* sin(x  * 40.0 + tAnim * 2.0)+ 0.2* sin(x * 120.0 + tAnim * 1.4);
    gl_Position = gl_ModelViewProjectionMatrix * vec4(x, y, z,1);
    gl_FrontColor = gl_Color;
}
