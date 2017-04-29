import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Dims3d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cube;
import eu.printingin3d.javascad.vrl.export.StlTextFile;

/**
 * Main class for application start.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * How deep recursive calls to be.
	 */
	private static int DETAILS_LEVEL = 4;

	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static byte SIDES_3_PATTERN[][][] = { 
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
	private static byte SIDES_4_PATTERN[][][] = { 
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
	private static byte SIDES_5_PATTERN[][][] = { 
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
	private static byte SIDES_6_PATTERN[][][] = { 
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
	private static byte SIDES_7_PATTERN[][][] = { 
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
	 * What part of the cube side will be empty.
	 */
	private static byte SIDES_PATTERNS[][][][] = { 
		SIDES_5_PATTERN,
		SIDES_4_PATTERN,
		SIDES_3_PATTERN,
	};

	/**
	 * Side size of a cubic 3D space.
	 */
	private static int SPACE_SIDE_SIZE = 3 * 4 * 5;

	/**
	 * 3D space for the shape as discrete voxels.
	 */
	private static byte voxels[][][] = new byte[SPACE_SIDE_SIZE][SPACE_SIDE_SIZE][SPACE_SIDE_SIZE];

	/*
	 * Initialize static members.
	 */
	static {
		for (int x = 0; x < voxels.length; x++) {
			for (int y = 0; y < voxels[x].length; y++) {
				for (int z = 0; z < voxels[x][y].length; z++) {
					voxels[x][y][z] = 1;
				}
			}
		}
	}

	/**
	 * Remove unneeded voxels.
	 * 
	 * @param sides
	 *            Coordinates of the sides.
	 */
	private static void warp(int[] sides) {
		for (int x = sides[0]; x <= sides[1]; x++) {
			for (int y = sides[2]; y <= sides[3]; y++) {
				for (int z = sides[4]; z <= sides[5]; z++) {
					voxels[x][y][z] = 0;
				}
			}
		}
	}

	/**
	 * Generate fractal cube with certain level.
	 * 
	 * @param level
	 *            Level details for the fractal.
	 * @param sides
	 *            Coordinates of the sides.
	 */
	private static void cube(int level, int sides[]) {
		/*
		 * End of details level.
		 */
		if (level == 1) {
			return;
		}

		int pattern = level - 2;
		int length = SIDES_PATTERNS[pattern][0].length;
		for (int x = sides[0], dx = (sides[1] - sides[0] + 1) / length; x <= sides[1]; x += dx) {
			int a = (x - sides[0]) / (dx);
			for (int y = sides[2], dy = (sides[3] - sides[2] + 1) / length; y <= sides[3]; y += dy) {
				int b = (y - sides[2]) / (dy);
				for (int z = sides[4], dz = (sides[5] - sides[4] + 1) / length; z <= sides[5]; z += dz) {
					int c = (z - sides[4]) / (dz);

					if (SIDES_PATTERNS[pattern][a][b][c] == 1) {
						cube(level - 1, new int[] { x, x + dx - 1, y, y + dy - 1, z, z + dz - 1 });
					} else if (SIDES_PATTERNS[pattern][a][b][c] == 0) {
						warp(new int[] { x, x + dx - 1, y, y + dy - 1, z, z + dz - 1 });
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
	private static int[] volume() {
		int counters[] = {0, 0, 0};
		
		for (int x = 0; x < voxels.length; x++) {
			for (int y = 0; y < voxels[x].length; y++) {
				for (int z = 0; z < voxels[x][y].length; z++) {
					if(voxels[x][y][z] == 0) {
						counters[0]++;
					} else if(voxels[x][y][z] == 1) {
						counters[1]++;
					}
					
					counters[2]++;
				}
			}
		}
		
		return counters;
	}

	/**
	 * Transform cube sides to STL commands.
	 * 
	 * @param sides
	 *            Coordinates of the cube sides.
	 * 
	 * @return String representation of STL commands.
	 */
	private static String cubeToSTL(int[] sides) {
		String stl = "   facet normal -1 0 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal -1 0 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 0 1\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 0 1\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 1 0 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 1 0 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 0 -1\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 0 -1\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 1 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 1 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 -1 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n"
				+ "   facet normal 0 -1 0\n" + "      outer loop\n" + "         vertex %d %d %d\n"
				+ "         vertex %d %d %d\n" + "         vertex %d %d %d\n" + "      endloop\n" + "   endfacet\n";

		return String.format(stl, sides[0], sides[3], sides[5], sides[0], sides[3], sides[4], sides[0], sides[2],
				sides[5],

				sides[0], sides[2], sides[5], sides[0], sides[3], sides[4], sides[0], sides[2], sides[4],

				sides[1], sides[3], sides[5], sides[0], sides[3], sides[5], sides[1], sides[2], sides[5],

				sides[1], sides[2], sides[5], sides[0], sides[3], sides[5], sides[0], sides[2], sides[5],

				sides[1], sides[3], sides[4], sides[1], sides[3], sides[5], sides[1], sides[2], sides[4],

				sides[1], sides[2], sides[4], sides[1], sides[3], sides[5], sides[1], sides[2], sides[5],

				sides[0], sides[3], sides[4], sides[1], sides[3], sides[4], sides[0], sides[2], sides[4],

				sides[0], sides[2], sides[4], sides[1], sides[3], sides[4], sides[1], sides[2], sides[4],

				sides[1], sides[3], sides[5], sides[1], sides[3], sides[4], sides[0], sides[3], sides[5],

				sides[0], sides[3], sides[5], sides[1], sides[3], sides[4], sides[0], sides[3], sides[4],

				sides[1], sides[2], sides[4], sides[1], sides[2], sides[5], sides[0], sides[2], sides[4],

				sides[0], sides[2], sides[4], sides[1], sides[2], sides[5], sides[0], sides[2], sides[5]);
	}

	/**
	 * Save 3D space in a STL ASCII file.
	 * 
	 * @param out
	 *            Output stream.
	 * 
	 * @throws IOException
	 *             Thrown if there is IO operation problem.
	 */
	private static void stl(Writer out) throws IOException {
		out.write("solid Shape" + System.currentTimeMillis());
		out.write("\n");

		for (int x = 0; x < voxels.length; x++) {
			for (int y = 0; y < voxels[x].length; y++) {
				for (int z = 0; z < voxels[x][y].length; z++) {
					if (voxels[x][y][z] == 1) {
						out.write(cubeToSTL(new int[] { x, x + 1, y, y + 1, z, z + 1 }));
					}
				}
			}
		}

		out.write("endsolid");
		out.write("\n");
	}

	/**
	 * Transform voxels to 3D model in order to be saved as STL.
	 * 
	 * @return Complex 3D model.
	 */
	private static Abstract3dModel voxelsToModel() {
		Abstract3dModel model = new Cube(new Dims3d(voxels.length, voxels[0].length, voxels[0][0].length));
		
		boolean first = true;
		for (int x = 0; x < voxels.length; x++) {
			for (int y = 0; y < voxels[x].length; y++) {
				for (int z = 0; z < voxels[x][y].length; z++) {
					if (voxels[x][y][z] == 0) {
						continue;
					}
					
					if(first == true) {
						model = new Cube(1).move(new Coords3d(x, y, z));
						first = false;
					} else {
						model = model.addModel((new Cube(1)).move(new Coords3d(x, y, z)));
					}
				}
			}
		}
		
		return model;
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

		cube(DETAILS_LEVEL, new int[] { 0, SPACE_SIDE_SIZE - 1, 0, SPACE_SIDE_SIZE - 1, 0, SPACE_SIDE_SIZE - 1 });

		System.out.println("Volume: " + Arrays.toString(volume()));

		/*
		 * Use STL Java library for file saving.
		 */
		StlTextFile out = new StlTextFile( new FileOutputStream("./bin/cube" + System.currentTimeMillis() + ".stl") );
		out.writeToFile(voxelsToModel().toCSG().toFacets());
		out.close();
		
		System.out.println("Stop ...");
	}
}
