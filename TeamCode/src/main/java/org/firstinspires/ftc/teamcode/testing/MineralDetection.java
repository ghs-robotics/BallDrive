package org.firstinspires.ftc.teamcode.testing;

import android.util.Log;

import org.firstinspires.ftc.teamcode.core.vuforia.VuforiaFacade;
import org.opencv.core.Mat;
import org.opencv.core.Core;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.Arrays;
import java.util.Vector;

public class MineralDetection {
    private final double[][] mineralLoc = {{62.2358, 118.008,0},{89.293, 91.9572,0},{118.008, 62.2358,0}};
    private final double fieldOfView = 71;

    private double length(double[] pos1, double[] pos2) {
        double a = Math.pow(pos1[0]-pos2[0], 2);
        double b = Math.pow(pos1[1]-pos2[1], 2);
        double c = Math.pow(pos1[2]-pos2[2], 2);
        return Math.sqrt(a+b+c);
    }

    private double distFromMineral(double[] camLoc, int mineralNum) {
        return length(scaleMineralLoc(camLoc)[mineralNum], camLoc);
    }

    private double slope(double x1, double x2, double y1, double y2) {
        return (y2-y1)/(x2-x1);
    }

    private double[] scale(double[] array, double scalar) {
        double[] scaledArray = new double[array.length];
        for (int i=0; i<array.length; i++) {
            scaledArray[i] = array[i] * scalar;
        }
        return scaledArray;
    }

    private double[] addArray(double[] arr1, double[] arr2) {
        double[] result = new double[arr1.length];
        for(int i=0; i<arr1.length; i++) {
            result[i] = arr1[i] + arr2[i];
        }
        return result;
    }

    private double[] translate(double[] pos1, double[] trans, double direction) {
        double[] transScaled = scale(trans, direction);
        return addArray(pos1,transScaled);
    }

    private double[] rotate2d(double[] loc, double theta) {
        double radians = Math.toRadians(theta);
        double nX = (loc[0]*Math.cos(radians))-(loc[1]*Math.sin(radians));
        double nY = (loc[1]*Math.cos(radians))+(loc[0]*Math.sin(radians));
        return new double[]{nX,nY,loc[2]};
    }

    private double[] intersect(double[] line1, double[] line2){
        double m1 = line1[0];
        double b1 = line1[1];
        double m2 = line2[0];
        double b2 = line2[1];
        double x = (b2-b1)/(m1-m2);
        double y = m1*x+b1;
        return new double[] {x,y};
    }

    private double[] lineFromPoints(double[] pt1, double[] pt2) {
        double m = slope(pt1[0],pt2[0],pt1[1],pt2[1]);
        double b = pt1[1]-(pt1[0]*m);
        return new double[] {m,b};
    }

    private double angle2slope(double angle) {
        double radians = Math.toRadians(angle);
        return 1 / (Math.tan(radians));
    }

    private double[] doubleVectortoArray(Vector<Double> doubleVector) {
        double[] array = new double[3];
        for(int i=0; i<doubleVector.size(); i++) {
            array[i] = doubleVector.get(i);
        }
        return array;
    }

    private double[][] scaleMineralLoc(double[] camPos) {


        double[][] newMineralLoc = new double[3][3];
        for (int i=0; i<3; i++) {
            newMineralLoc[i]=mineralLoc[i].clone();
        }

        if (camPos[0] < 0) {
            for(int i=0; i<newMineralLoc.length; i++) {
                newMineralLoc[i][0] = newMineralLoc[i][0] * -1;
            }
        }

        if (camPos[1] < 0) {
            for(int i=0; i<newMineralLoc.length; i++) {
                newMineralLoc[i][1] = newMineralLoc[i][1] * -1;
            }
        }

        return  newMineralLoc;
    }

    private double[] flip(double[] array, int loc1, int loc2) {
        double[] flippedArray = array.clone();
        flippedArray[loc1] = array[loc2];
        flippedArray[loc2] = array[loc1];
        return flippedArray;
    }

    private String hrLine(double[] line) {
        return "y=" + line[0] + "x+"+line[1];
    }

    private double calcAxisLoc(double fov, int mineralNum, double[] camLocOrig, double[] camRotOrig, double[] scalars, boolean yAxis, boolean debug) {
        double[] customMineralLoc = scaleMineralLoc(camLocOrig)[mineralNum];
        double[] customCameraLoc = camLocOrig.clone();
        double[] customCameraRot = camRotOrig.clone();

        if(yAxis) {
            customCameraLoc = flip(customCameraLoc,0,2);
            customCameraLoc = flip(customCameraLoc,1,2);
            customMineralLoc = flip(customMineralLoc,0,2);
            customCameraRot = flip(customCameraRot,0,2);
        }

        double[] shiftedMineralLoc = translate(customMineralLoc, customCameraLoc, -1);
        if(!yAxis) {
            shiftedMineralLoc = rotate2d(shiftedMineralLoc, -1*customCameraRot[2]);
        }
        double[] camLoc = {0,0,0};
        double[] camRot = {0,0,0};
        double[] horizLine = {0,10};
        double[] mineralLine = lineFromPoints(shiftedMineralLoc,camLoc);
        double[] mineralIntersect = intersect(horizLine, mineralLine);
        double[] fovLineR = {angle2slope(fov/2.0),0};
        double[] rIntersect = intersect(horizLine, fovLineR);
        double[] fovLineL = {-1*angle2slope(fov/2.0),0};
        double[] lIntersect = intersect(horizLine, fovLineL);
        double dist = Math.abs(rIntersect[0]-lIntersect[0]);
        double minDistL = mineralIntersect[0]-lIntersect[0];
        double pctOver = minDistL/dist;
        double pctScaled;

        if(pctOver > 0.5) {
            pctScaled = pctOver * Math.abs(1 + (pctOver * scalars[0]));
        } else {
            pctScaled = pctOver * Math.abs(1 - (0.5 - pctOver) * scalars[1]);
        }

        if(debug) {
            Log.d("team-code","Mineral Detection: orig loc and rot: "+Arrays.toString(customCameraLoc)+" "+Arrays.toString(customCameraRot));
            Log.d("team-code","Mineral Detection: mineral #"+mineralNum+" loc: "+Arrays.toString(customMineralLoc));
            Log.d("team-code","Mineral Detection: new_mineral #"+mineralNum+" loc:"+Arrays.toString(shiftedMineralLoc));
            Log.d("team-code","Mineral Detection: new cam loc and rot: "+Arrays.toString(camLoc)+" "+Arrays.toString(camRot));
            Log.d("team-code","Mineral Detection: horizontal line: "+hrLine(horizLine));
            Log.d("team-code","Mineral Detection: line through mineral: "+hrLine(mineralLine));
            Log.d("team-code","Mineral Detection: mineral line intersect w/ horiz line: "+Arrays.toString(mineralIntersect));
            Log.d("team-code","Mineral Detection: FoV line right : "+(hrLine(fovLineR)));
            Log.d("team-code","Mineral Detection: FoV line right eintersect w/ horiz line: "+Arrays.toString(rIntersect));
            Log.d("team-code","Mineral Detection: FoV line left : "+hrLine(fovLineL));
            Log.d("team-code","Mineral Detection: FoV line left intersect w/ horiz line: "+Arrays.toString(lIntersect));
            Log.d("team-code","Mineral Detection: length of FoV: "+dist);
            Log.d("team-code","Mineral Detection: mineral intersect distance from left side: "+minDistL);
            Log.d("team-code","Mineral Detection: calculated percentage: "+pctOver);
            Log.d("team-code","Mineral Detection: scaled percentage: "+pctScaled);
        }

        return pctScaled;
    }

    private double[] getMineralLocation(int mineralNum, double fovScaleFactor, double[] vuforiaCamLoc, double[] vuforiaCamRot) {
        double[] xScalars = {0.1,2};
        double[] yScalars = {0.1,0.1};
        double X = calcAxisLoc(fieldOfView,mineralNum,vuforiaCamLoc,vuforiaCamRot,xScalars,false,false);
        double Y = calcAxisLoc(fieldOfView*fovScaleFactor,mineralNum,vuforiaCamLoc,vuforiaCamRot,yScalars,true,false);
        return new double[]{X,Y};
    }

    public int findGoldMineral() {
        VuforiaFacade vuforiaFacade = new VuforiaFacade();
        double[] vuforiaCamLoc = doubleVectortoArray(vuforiaFacade.getTranslation());
        double[] vuforiaCamRot = doubleVectortoArray(vuforiaFacade.getRotation());
        Mat image = vuforiaFacade.getFrame();
        int width = image.width();
        int height = image.height();
        int[] mineralColors = new int[3]; // -1 is unknown, 0 is silver, 1 is gold
        Arrays.fill(mineralColors, -1);
        for(int i=0;i<3;i++) {
            double[] mineralLocation = getMineralLocation(i, (4 / 3.0) * 1.4, vuforiaCamLoc, vuforiaCamRot);
            if ((mineralLocation[0] >= 0) && (mineralLocation[0] <= 1) && (mineralLocation[1] >= 0) && (mineralLocation[1] <= 1)) {
                int xLoc = (int) Math.round(mineralLocation[0] * width);
                int yLoc = (int) Math.round(mineralLocation[1] * height);
                System.out.println(Arrays.toString(mineralLocation));


                int left = ((xLoc - 250) >= 0) ? (xLoc - 250) : 0;
                int right = ((xLoc + 250) <= width) ? (xLoc + 250) : width;
                int top = ((yLoc - 250) >= 0) ? (yLoc - 250) : 0;
                int bottom = ((yLoc + 250) <= height) ? (yLoc + 250) : height;
                /*
                Log.d("team-code",left);
                Log.d("team-code",right);
                Log.d("team-code",top);
                Log.d("team-code",bottom);
                */

                Mat cropped = image.submat(top, bottom, left, right);
                Mat hsvCropped = new Mat();
                Imgproc.cvtColor(cropped, hsvCropped, Imgproc.COLOR_BGR2HSV);
                Mat goldFilter = new Mat();

                Core.inRange(hsvCropped, new Scalar(15, 0, 0), new Scalar(60, 255, 255), goldFilter);
                double targetNum = Math.pow((distFromMineral(vuforiaCamLoc, i) * 1.6) / 2, 2) * Math.PI;
                int numWhite = Core.countNonZero(goldFilter);

                if (numWhite >= targetNum) {
                    return i;
                } else {
                    mineralColors[i] = 0;
                }
            }
        }
        for(int i=0;i<mineralColors.length;i++) {
            if(mineralColors[i]<0) {
                return i;
            }
        }
        return -1;
    }
}
