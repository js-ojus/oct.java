/**
 * Copyright (c) 2012-2013 Ojus Software Labs Private Limited.
 * 
 * All rights reserved. Please see the files README.md, LICENSE and COPYRIGHT
 * for details.
 */

package com.ojuslabs.oct.util;

/**
 * Represents the 3-D - or, optionally, 2-D - coordinates of an entity.
 */
public class Point3D
{
    public double x;
    public double y;
    public double z;

    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double x, double y) {
        this(x, y, 0.0);
    }
}
