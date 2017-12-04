/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package volume;

import util.VectorMath;

/**
 *
 * @author michel
 */
public class GradientVolume {

    public GradientVolume(Volume vol) {
        volume = vol;
        dimX = vol.getDimX();
        dimY = vol.getDimY();
        dimZ = vol.getDimZ();
        data = new VoxelGradient[dimX * dimY * dimZ];
        compute();
        maxmag = -1.0;
    }

    public VoxelGradient getGradient(int x, int y, int z) {
//        try{
            return data[x + dimX * (y + dimY * z)];
//        }catch(ArrayIndexOutOfBoundsException e){
//            continue;
//        }
    }

    
    public void setGradient(int x, int y, int z, VoxelGradient value) {
        data[x + dimX * (y + dimY * z)] = value;
    }

    public void setVoxel(int i, VoxelGradient value) {
        data[i] = value;
    }

    public VoxelGradient getVoxel(int i) {
        return data[i];
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public int getDimZ() {
        return dimZ;
    }

    private void compute() {

        // this just initializes all gradients to the vector (0,0,0)
        for (int i=0; i<data.length; i++) {
            data[i] = zero;
        }
        
        
        for(int x = 1; x < volume.getDimX()-2; x++){
            for(int y = 1; y < volume.getDimY()-2; y++){
                for(int z = 1; z < volume.getDimZ()-2; z++){
                 /* double[] gradientVec = new double[3]; */
                 double gradx  = 0.5*(volume.getVoxel(x+1, y, z) - volume.getVoxel(x-1, y, z));
                 double grady  = 0.5*(volume.getVoxel(x, y+1, z) - volume.getVoxel(x, y-1, z));
                 double gradz  = 0.5*(volume.getVoxel(x, y, z+1) - volume.getVoxel(x, y, z-1));
                 /*VectorMath.setVector(gradientVec, gradx, grady, gradz);*/
                 
                 setGradient(x,y,z,new VoxelGradient((int)gradx,(int)grady,(int)gradz));
                }
            }
        }
               
    }
    
    public double getMaxGradientMagnitude() {
        if (maxmag >= 0) {
            return maxmag;
        } else {
            double magnitude = data[0].mag;
            for (int i=0; i<data.length; i++) {
                magnitude = data[i].mag > magnitude ? data[i].mag : magnitude;
            }   
            maxmag = magnitude;
            return magnitude;
        }
    }
    
    private int dimX, dimY, dimZ;
    private VoxelGradient zero = new VoxelGradient();
    VoxelGradient[] data;
    Volume volume;
    double maxmag;
}
