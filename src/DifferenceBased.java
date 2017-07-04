import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import eu.printingin3d.javascad.context.ColorHandlingContext;
import eu.printingin3d.javascad.context.IColorGenerationContext;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Dims3d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cube;
import eu.printingin3d.javascad.tranzitions.Colorize;
import eu.printingin3d.javascad.utils.ModelToFile;
import eu.printingin3d.javascad.vrl.CSG;
import eu.printingin3d.javascad.vrl.export.StlBinaryFile;
import eu.printingin3d.javascad.vrl.export.StlTextFile;

/**
 * Main class for application start.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static final byte SIDES_3_PATTERN[][][] = { 
		{	{ 1, 1, 1 }, 
			{ 1, 0, 1 }, 
			{ 1, 1, 1 }, },

		{ 	{ 1, 0, 1 }, 
			{ 0, 0, 0 }, 
			{ 1, 0, 1 }, },

		{ 	{ 1, 1, 1 }, 
			{ 1, 0, 1 }, 
			{ 1, 1, 1 }, },
	};

	/**
	 * What part of the cube with side of size 4 will be empty.
	 */
	private static final byte SIDES_4_PATTERN[][][] = { 
		{	{ 1, 1, 1, 1 }, 
			{ 1, 0, 0, 1 }, 
			{ 1, 0, 0, 1 }, 
			{ 1, 1, 1, 1 }, },

		{ 	{ 1, 0, 0, 1 }, 
			{ 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0 }, 
			{ 1, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 1 }, 
			{ 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0 }, 
			{ 1, 0, 0, 1 }, },
		
		{	{ 1, 1, 1, 1 }, 
			{ 1, 0, 0, 1 }, 
			{ 1, 0, 0, 1 }, 
			{ 1, 1, 1, 1 }, },
	};

	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static final byte SIDES_5_PATTERN[][][] = { 
		{	{ 1, 1, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 1 }, 
			{ 1, 1, 1, 1, 1 }, },

		{ 	{ 1, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 1 }, },
		
		{	{ 1, 1, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 1 }, 
			{ 1, 1, 1, 1, 1 }, },
	};

	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static final byte SIDES_6_PATTERN[][][] = { 
		{	{ 1, 1, 1, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 1, 1, 1, 1, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 1 }, },

		{	{ 1, 1, 1, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 1 }, 
			{ 1, 1, 1, 1, 1, 1 }, },
	};

	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static final byte SIDES_7_PATTERN[][][] = { 
		{	{ 1, 1, 1, 1, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 1, 1, 1, 1, 1, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, },

		{ 	{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 0, 0, 0, 0, 0, 0, 0 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, },

		{	{ 1, 1, 1, 1, 1, 1, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 0, 0, 0, 0, 0, 1 }, 
			{ 1, 1, 1, 1, 1, 1, 1 }, },
	};

	/**
	 * All parameters in a single array.
	 */
	private static final Object[] PARAMETERS = {
			/* One detail with recursive level of one. */
			1,
			(3),
			new byte[][][][]{SIDES_3_PATTERN},
			3.0,
			0.0,
			1,
			new Color[]{Color.WHITE},
			
//			/* Six details with recursive level of one. */
//			1,
//			(5),
//			new byte[][][][]{SIDES_3_PATTERN},
//			3.0,
//			-0.01,
//			6,
//			new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN, Color.RED},
			
//			/* Six details with recursive level of two. */
//			2,
//			(5 * 4),
//			new byte[][][][]{SIDES_4_PATTERN, SIDES_3_PATTERN},
//			3.0,
//			-0.01,
//			6,
//			new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN, Color.RED},
			
//			/* One detail with recursive level of two. */
//			2,
//			(3 * 3),
//			new byte[][][][]{SIDES_3_PATTERN, SIDES_3_PATTERN},
//			3.0,
//			0.0,
//			1,
//			new Color[]{Color.WHITE},

//			/* Six details with recursive level of four. */
//			3,
//			(5 * 4 * 3),
//			new byte[][][][]{SIDES_5_PATTERN, SIDES_4_PATTERN, SIDES_3_PATTERN},
//			3.0,
//			-0.01,
//			6,
//			new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN, Color.RED},

//			/* Six details with recursive level of four. */
//			4,
//			(6 * 5 * 4),
//			new byte[][][][]{SIDES_6_PATTERN, SIDES_5_PATTERN, SIDES_4_PATTERN},
//			3.0,
//			-0.01,
//			6,
//			new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN, Color.RED},

//			/* One detail with recursive level of four. */
//			4,
//			(3 * 4 * 5),
//			new byte[][][][]{SIDES_3_PATTERN, SIDES_4_PATTERN, SIDES_5_PATTERN},
//			1.0,
//			-0.01,
//			1,
//			new Color[]{Color.WHITE},

//			/* One detail with recursive level of five. */
//			5,
//			(3 * 4 * 5 * 6),
//			new byte[][][][]{SIDES_3_PATTERN, SIDES_4_PATTERN, SIDES_5_PATTERN, SIDES_6_PATTERN},
//			1.0,
//			0.0,
//			1,
//			new Color[]{Color.WHITE},

//			/* One detail with recursive level of five. */
//			5,
//			(3 * 4 * 4 * 5),
//			new byte[][][][]{SIDES_3_PATTERN, SIDES_4_PATTERN, SIDES_4_PATTERN, SIDES_5_PATTERN},
//			1.0,
//			0.0,
//			1,
//			new Color[]{Color.WHITE},
	};

	/**
	 * How deep recursive calls to be.
	 */
	private static final int RECURSIVE_DEPTH_LEVEL = (Integer)PARAMETERS[0];

	/**
	 * Side size of a cubic 3D space.
	 */
	private static final int SPACE_SIDE_SIZE = (Integer)PARAMETERS[1];
	
	/**
	 * What part of the cube side will be empty.
	 */
	private static final byte SIDES_PATTERNS[][][][] = (byte[][][][])PARAMETERS[2];

	/**
	 * Single voxel cube size scale.
	 */
	private static final double VOXEL_SCALE = (Double)PARAMETERS[3];
	
	/**
	 * Single voxel cube side.
	 */
	private static final double VOXEL_SIDE = VOXEL_SCALE + (Double)PARAMETERS[4];

	/**
	 * How deep recursive calls to be.
	 */
	private static final int NUMBER_OF_CONSECUTIVE_DETAILS = (Integer)PARAMETERS[5];
	
	/**
	 * Colors of the consecutive details.
	 */
	private static final Color CONSECUTIVE_DETAILS_COLORS[] = (Color[]) PARAMETERS[6];
	
	/**
	 * 3D fractal shape.
	 */
	private static CSG shape = null;

	/*
	 * Initialize static members.
	 */
	static {
	}

	/**
	 * Remove unneeded volume.
	 * 
	 * @param sides
	 *            Coordinates of the sides.
	 */
	private static void warp(double[] sides) {
		double side = Math.max(sides[1]-sides[0], Math.max(sides[3]-sides[2], sides[5]-sides[4]));
		
		/*
		 * Shape with zero size in maximum dimension can not be removed. 
		 */
		if(side == 0) {
			return;
		}
		
		Cube cube = new Cube(side);
		shape = shape.difference((cube.move(new Coords3d(sides[0], sides[2], sides[4]))).toCSG());
	}

	/**
	 * Generate fractal cube with certain level.
	 * 
	 * @param level
	 *            Level details for the fractal.
	 * @param sides
	 *            Coordinates of the sides.
	 */
	private static void cube(int level, double sides[]) {
		/*
		 * End of details level.
		 */
		if (level == 0) {
			return;
		}

		int length = SIDES_PATTERNS[level-1][0].length;
		double dx = (sides[1] - sides[0]) / length;
		double dy = (sides[3] - sides[2]) / length;
		double dz = (sides[5] - sides[4]) / length;
		
		double x, y, z;
		for (int a = 0; a < length; a++) {
			x = sides[0] + a * dx;
			for (int b = 0; b < length; b++) {
				y = sides[2] + b * dy;
				for (int c = 0; c < length; c++) {
					z = sides[4] + c * dz;

					if (SIDES_PATTERNS[level-1][a][b][c] == 1) {
						cube(level - 1, new double[] { x, x + dx, y, y + dy, z, z + dz });
					} else if (SIDES_PATTERNS[level-1][a][b][c] == 0) {
						warp(new double[] { x, x + dx, y, y + dy, z, z + dz });
					}
				}
			}
		}
	}

	/**
	 * Calculate volume of the 2D shape.
	 * 
	 * @return Array of values with the amount of empty space, amount of occupied space and total space.
	 */
	private static double[] volume() {
		double counters[] = {0, 0, 0};
		
		//TODO Volume calculation is different when CSG object is used.
		
		return counters;
	}
	
	/**
	 * Single entry point.
	 * 
	 * @param args
	 *            Command line arguments.
	 * @throws IOException
	 *             Thrown if there is IO operation problem.
	 */
	public static void main(String[] args) throws IOException {
		System.out.println("Start ...");

		Cube solid = new Cube(SPACE_SIDE_SIZE*VOXEL_SCALE);
		shape = solid.toCSG();
		cube(RECURSIVE_DEPTH_LEVEL, new double[] { 	solid.getBoundaries().getX().getMin(), solid.getBoundaries().getX().getMax(), 
													solid.getBoundaries().getY().getMin(), solid.getBoundaries().getY().getMax(), 
													solid.getBoundaries().getZ().getMin(), solid.getBoundaries().getZ().getMax() });

		//System.out.println("Volume: " + Arrays.toString(volume()));

		/*
		 * Use STL Java library for file saving.
		 */
		StlBinaryFile out = new StlBinaryFile( new FileOutputStream("./cube" + System.currentTimeMillis() + ".stl") );
		out.writeToFile(shape.toFacets());
		out.close();
		//ModelToFile out = new ModelToFile( new File("./cube" + System.currentTimeMillis() + ".scad") );
		//out.addModel( voxelsToModel() ).saveToFile( ColorHandlingContext.DEFAULT );
		
		System.out.println("Stop ...");
	}
}
