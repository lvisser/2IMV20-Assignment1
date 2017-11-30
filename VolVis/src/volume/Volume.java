/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package volume;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author michel
 */
public class Volume {
    
    public Volume(int xd, int yd, int zd) {
        data = new short[xd*yd*zd];
        dimX = xd;
        dimY = yd;
        dimZ = zd;
    }
    
    public Volume(File file) {
        
        try {
            VolumeIO reader = new VolumeIO(file);
            dimX = reader.getXDim();
            dimY = reader.getYDim();
            dimZ = reader.getZDim();
            data = reader.getData().clone();
            computeHistogram();
        } catch (IOException ex) {
            System.out.println("IO exception");
        }
        
    }
    
    
    public short getVoxel(int x, int y, int z) {
        return data[x + dimX*(y + dimY * z)];
    }
    
    public void setVoxel(int x, int y, int z, short value) {
        data[x + dimX*(y + dimY*z)] = value;
    }

    public void setVoxel(int i, short value) {
        data[i] = value;
    }
    
    public short getVoxel(int i) {
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

    public short getMinimum() {
        short minimum = data[0];
        for (int i=0; i<data.length; i++) {
            minimum = data[i] < minimum ? data[i] : minimum;
        }
        return minimum;
    }

    public short getMaximum() {
        short maximum = data[0];
        for (int i=0; i<data.length; i++) {
            maximum = data[i] > maximum ? data[i] : maximum;
        }
        return maximum;
    }
 
    public int[] getHistogram() {
        return histogram;
    }
    
    private void computeHistogram() {
        histogram = new int[getMaximum() + 1];
        for (int i=0; i<data.length; i++) {
            histogram[data[i]]++;
        }
    }
    
    private int dimX, dimY, dimZ;
    private short[] data;
    private int[] histogram;
}

        /* indentify corners to calulate q0 and q1
        double[] corner1 = new double[3];
        VectorMath.setVector(corner1,0d - volumeCenter[0], 0d - volumeCenter[1], 0d - volumeCenter[2]);
        double[] corner2 = new double[3];
        VectorMath.setVector(corner2,volume.getDimX() - volumeCenter[0], 0d - volumeCenter[1], 0d - volumeCenter[2]);
        double[] corner3 = new double[3];
        VectorMath.setVector(corner3,0d - volumeCenter[0], volume.getDimY() - volumeCenter[1], 0d - volumeCenter[2]);
        double[] corner4 = new double[3];
        VectorMath.setVector(corner4, volume.getDimX() - volumeCenter[0], volume.getDimY() - volumeCenter[1], 0d - volumeCenter[2]);
        */