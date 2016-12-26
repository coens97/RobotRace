#version 120
uniform sampler2D baseImage;

void main()
{
    //gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);//gl_Color;
    gl_FragColor = texture2D( baseImage, gl_TexCoord[0].xy);
}
