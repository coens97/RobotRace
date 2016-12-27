varying vec3 P;
void main()
{
    if (P.z < 0.0) {
        gl_FragColor = vec4(0.45882352941, 0.77647058823, 0.90588235294, 1); 
    }
    else if (P.z < 0.5){
        gl_FragColor = vec4(0.89411764705, 0.85098039215, 0.72549019607, 1);
    }
    else {
        gl_FragColor = vec4(0.00784313725, 0.53333333333, 0.0431372549, 1);
    }
}
