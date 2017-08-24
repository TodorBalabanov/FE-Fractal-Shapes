import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import eu.printingin3d.javascad.context.ColorHandlingContext;
import eu.printingin3d.javascad.context.IColorGenerationContext;
import eu.printingin3d.javascad.coords.Abstract3d;
import eu.printingin3d.javascad.coords.Basic3dFunc;
import eu.printingin3d.javascad.coords.Coords3d;
import eu.printingin3d.javascad.coords.Dims3d;
import eu.printingin3d.javascad.models.Abstract3dModel;
import eu.printingin3d.javascad.models.Cube;
import eu.printingin3d.javascad.tranform.ITransformation;
import eu.printingin3d.javascad.tranform.TransformationFactory;
import eu.printingin3d.javascad.tranzitions.Colorize;
import eu.printingin3d.javascad.utils.ModelToFile;
import eu.printingin3d.javascad.vrl.CSG;
import eu.printingin3d.javascad.vrl.Facet;
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
	private static final byte SIDES_3_PATTERN[][][] = { { { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 }, },

			{ { 1, 0, 1 }, { 0, 0, 0 }, { 1, 0, 1 }, },

			{ { 1, 1, 1 }, { 1, 0, 1 }, { 1, 1, 1 }, }, };

	/**
	 * What part of the cube with side of size 4 will be empty.
	 */
	private static final byte SIDES_4_PATTERN[][][] = {
			{ { 1, 1, 1, 1 }, { 1, 0, 0, 1 }, { 1, 0, 0, 1 }, { 1, 1, 1, 1 }, },

			{ { 1, 0, 0, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 0, 0, 1 }, },

			{ { 1, 0, 0, 1 }, { 0, 0, 0, 0 }, { 0, 0, 0, 0 }, { 1, 0, 0, 1 }, },

			{ { 1, 1, 1, 1 }, { 1, 0, 0, 1 }, { 1, 0, 0, 1 }, { 1, 1, 1, 1 }, }, };

	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static final byte SIDES_5_PATTERN[][][] = {
			{ { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 }, },

			{ { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 1 }, },

			{ { 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1 }, }, };

	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static final byte SIDES_6_PATTERN[][][] = {
			{ { 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1 }, },

			{ { 1, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 1 }, },

			{ { 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1 }, }, };

	/**
	 * What part of the cube with side of size 5 will be empty.
	 */
	private static final byte SIDES_7_PATTERN[][][] = {
			{ { 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1 }, },

			{ { 1, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1 }, },

			{ { 1, 0, 0, 0, 0, 0, 1 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0 }, { 1, 0, 0, 0, 0, 0, 1 }, },

			{ { 1, 1, 1, 1, 1, 1, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 },
					{ 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1 }, { 1, 1, 1, 1, 1, 1, 1 }, }, };

	/**
	 * All parameters in a single array.
	 */
	private static final Object[] PARAMETERS = {

			// /* One detail with recursive level of one. */
			// 1,
			// (3),
			// new byte[][][][]{SIDES_3_PATTERN},
			// 3.0,
			// +0.001,
			// 1,
			// new Color[]{Color.WHITE},

			// /* One detail with recursive level of one. */
			// 1,
			// (5),
			// new byte[][][][]{SIDES_5_PATTERN},
			// 3.0,
			// +0.001,
			// 1,
			// new Color[]{Color.WHITE},

			// /* One detail with recursive level of one. */
			// 1,
			// (6),
			// new byte[][][][]{SIDES_6_PATTERN},
			// 3.0,
			// +0.001,
			// 1,
			// new Color[]{Color.WHITE},

			// /* One detail with recursive level of one. */
			// 1,
			// (7),
			// new byte[][][][]{SIDES_7_PATTERN},
			// 3.0,
			// +0.001,
			// 1,
			// new Color[]{Color.WHITE},

			// /* Six details with recursive level of one. */
			// 1,
			// (3),
			// new byte[][][][]{SIDES_3_PATTERN},
			// 3.0,
			// +0.001,
			// 6,
			// new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN,
			// Color.RED},

			/* One detail with recursive level of two. */
			2, (4 * 3), new byte[][][][] { SIDES_4_PATTERN, SIDES_3_PATTERN }, 3.0, +0.001, 1,
			new Color[] { Color.WHITE },

			// /* Six details with recursive level of two. */
			// 2,
			// (4 * 3),
			// new byte[][][][]{SIDES_4_PATTERN, SIDES_3_PATTERN},
			// 3.0,
			// -0.01,
			// 6,
			// new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN,
			// Color.RED},

			// /* Six details with recursive level of three. */
			// 3,
			// (5 * 4 * 3),
			// new byte[][][][]{SIDES_5_PATTERN, SIDES_4_PATTERN, SIDES_3_PATTERN},
			// 3.0,
			// -0.01,
			// 6,
			// new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN,
			// Color.RED},

			// /* Six details with recursive level of three. */
			// 3,
			// (6 * 5 * 4),
			// new byte[][][][]{SIDES_6_PATTERN, SIDES_5_PATTERN, SIDES_4_PATTERN},
			// 3.0,
			// -0.01,
			// 6,
			// new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.WHITE, Color.GREEN,
			// Color.RED},

			// /* One detail with recursive level of three. */
			// 3,
			// (3 * 4 * 5),
			// new byte[][][][]{SIDES_3_PATTERN, SIDES_4_PATTERN, SIDES_5_PATTERN},
			// 1.0,
			// +0.001,
			// 1,
			// new Color[]{Color.WHITE},

			// /* One detail with recursive level of four. */
			// 4, (3 * 4 * 5 * 6), new byte[][][][] { SIDES_3_PATTERN, SIDES_4_PATTERN,
			// SIDES_5_PATTERN, SIDES_6_PATTERN },
			// 1.0, +0.001, 1, new Color[] { Color.WHITE },

			// /* One detail with recursive level of four. */
			// 4, (3 * 4 * 4 * 5), new byte[][][][] { SIDES_3_PATTERN, SIDES_4_PATTERN,
			// SIDES_4_PATTERN, SIDES_5_PATTERN },
			// 1.0, +0.001, 1, new Color[] { Color.WHITE },

	};

	/**
	 * How deep recursive calls to be.
	 */
	private static final int RECURSIVE_DEPTH_LEVEL = (Integer) PARAMETERS[0];

	/**
	 * Side size of a cubic 3D space.
	 */
	private static final int SPACE_SIDE_SIZE = (Integer) PARAMETERS[1];

	/**
	 * What part of the cube side will be empty.
	 */
	private static final byte SIDES_PATTERNS[][][][] = (byte[][][][]) PARAMETERS[2];

	/**
	 * Single voxel cube size scale.
	 */
	private static final double VOXEL_SCALE = (Double) PARAMETERS[3];

	/**
	 * Space between two voxels. If it is negative there is space, if it is positive
	 * there is overlap.
	 */
	private static final double VOXEL_DELTA = (Double) PARAMETERS[4];

	/**
	 * Single voxel cube side.
	 */
	private static final double VOXEL_SIDE = VOXEL_SCALE + VOXEL_DELTA;

	/**
	 * How deep recursive calls to be.
	 */
	private static final int NUMBER_OF_CONSECUTIVE_DETAILS = (Integer) PARAMETERS[5];

	/**
	 * Colors of the consecutive details.
	 */
	private static final Color CONSECUTIVE_DETAILS_COLORS[] = (Color[]) PARAMETERS[6];

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
		if (level == 0) {
			return;
		}

		int length = SIDES_PATTERNS[level - 1][0].length;
		for (int x = sides[0], dx = (sides[1] - sides[0] + 1) / length; x <= sides[1]; x += dx) {
			int a = (x - sides[0]) / (dx);
			for (int y = sides[2], dy = (sides[3] - sides[2] + 1) / length; y <= sides[3]; y += dy) {
				int b = (y - sides[2]) / (dy);
				for (int z = sides[4], dz = (sides[5] - sides[4] + 1) / length; z <= sides[5]; z += dz) {
					int c = (z - sides[4]) / (dz);

					if (SIDES_PATTERNS[level - 1][a][b][c] == 1) {
						cube(level - 1, new int[] { x, x + dx - 1, y, y + dy - 1, z, z + dz - 1 });
					} else if (SIDES_PATTERNS[level - 1][a][b][c] == 0) {
						warp(new int[] { x, x + dx - 1, y, y + dy - 1, z, z + dz - 1 });
					}
				}
			}
		}
	}

	/**
	 * Calculate volume of the 2D shape.
	 * 
	 * @return Array of values with the amount of empty space, amount of occupied
	 *         space and total space.
	 */
	private static int[] volume() {
		int counters[] = { 0, 0, 0 };

		for (int x = 0; x < voxels.length; x++) {
			for (int y = 0; y < voxels[x].length; y++) {
				for (int z = 0; z < voxels[x][y].length; z++) {
					if (voxels[x][y][z] == 0) {
						counters[0]++;
					} else if (voxels[x][y][z] == 1) {
						counters[1]++;
					}

					counters[2]++;
				}
			}
		}

		return counters;
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

					if (first == true) {
						model = new Cube(VOXEL_SIDE)
								.move(new Coords3d(x * VOXEL_SCALE, y * VOXEL_SCALE, z * VOXEL_SCALE));
						first = false;
					} else {
						model = model.addModel((new Cube(VOXEL_SIDE))
								.move(new Coords3d(x * VOXEL_SCALE, y * VOXEL_SCALE, z * VOXEL_SCALE)));
					}
				}
			}
		}

		Abstract3dModel group = new Colorize(CONSECUTIVE_DETAILS_COLORS[0], model);
		for (int i = 1; i < NUMBER_OF_CONSECUTIVE_DETAILS; i++) {
			group = group.addModel(new Colorize(CONSECUTIVE_DETAILS_COLORS[i],
					model.move(new Coords3d(0, 0, i * SPACE_SIDE_SIZE * VOXEL_SCALE))));
		}
		model = group;

		return model;
	}

	/**
	 * Transform voxels to CSG data in order to be saved as STL.
	 * 
	 * @param sx
	 *            Start x.
	 * @param ex
	 *            End x.
	 * @param sy
	 *            Start y.
	 * @param ey
	 *            End y.
	 * @param sz
	 *            Start z.
	 * @param ez
	 *            End z.
	 * 
	 * @return Complex 3D model.
	 */
	private static CSG voxelsToCSG(int sx, int ex, int sy, int ey, int sz, int ez) {
		List<CSG> shapes = new ArrayList<CSG>();

		/*
		 * Transform all voxels in list of CSG cubes.
		 */
		for (int x = sx; x < ex; x++) {
			for (int y = sy; y < ey; y++) {
				for (int z = sz; z < ez; z++) {
					if (voxels[x][y][z] == 0) {
						continue;
					}

					shapes.add(
							(new Cube(VOXEL_SIDE).move(new Coords3d(x * VOXEL_SCALE, y * VOXEL_SCALE, z * VOXEL_SCALE)))
									.toCSG());
				}
			}
		}

		/*
		 * Union shape by shape.
		 */
		loop: for (int i = 0; i < shapes.size(); i++) {
			for (int j = i + 1; j < shapes.size(); j++) {
				CSG union = shapes.get(i).union(shapes.get(j));
				if (union.toFacets().size() == 0) {
					continue;
				}

				/*
				 * Union of two shapes.
				 */
				shapes.remove(j);
				shapes.remove(i);
				shapes.add(0, union);
				i = j = 0;
				continue loop;
			}
		}

		CSG model = shapes.get(0).union(shapes.get(1));

		CSG group = shapes.get(0).union(shapes.get(1));
		for (int i = 1; i < NUMBER_OF_CONSECUTIVE_DETAILS; i++) {
			group = group.union(model.transformed(TransformationFactory
					.getTranlationMatrix(new Coords3d(0, 0, i * SPACE_SIDE_SIZE * VOXEL_SCALE - VOXEL_DELTA))));
		}
		model = group;

		return model;
	}

	/**
	 * Generate cube as single slit body.
	 * 
	 * @param alpha
	 *            Size of the cube side.
	 * 
	 * @param delta
	 *            Size of the frame.
	 * @param group
	 *            How many single bodies to be grouped in a raw.
	 */
	private static void singleSlitBody(int alpha, int delta, int group) {
		voxels = new byte[(group - 1) * (alpha - delta) + alpha][alpha][alpha];

		/*
		 * Build single body.
		 */
		for (int x = 0; x < alpha; x++) {
			for (int y = 0; y < alpha; y++) {
				for (int z = 0; z < alpha; z++) {
					if ((x == 0 || x == alpha - 1) && (y == 0 || y == alpha - 1)) {
						voxels[x][y][z] = 1;
					} else if ((x == 0 || x == alpha - 1) && (z == 0 || z == alpha - 1)) {
						voxels[x][y][z] = 1;
					} else if ((y == 0 || y == alpha - 1) && (z == 0 || z == alpha - 1)) {
						voxels[x][y][z] = 1;
					} else {
						voxels[x][y][z] = 0;
					}
				}
			}
		}

		/*
		 * Build group.
		 */
		for (int g = 1; g < group; g++) {
			for (int x = 0; x < alpha; x++) {
				for (int y = 0; y < alpha; y++) {
					for (int z = 0; z < alpha; z++) {
						voxels[x + g * (alpha - delta)][y][z] = voxels[x][y][z];
					}
				}
			}
		}
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

		cube(RECURSIVE_DEPTH_LEVEL,
				new int[] { 0, SPACE_SIDE_SIZE - 1, 0, SPACE_SIDE_SIZE - 1, 0, SPACE_SIDE_SIZE - 1 });
		// singleSlitBody(9, 1, 3);

		int[] volumes = volume();
		System.out.println("Volume: " + Arrays.toString(volumes));

		/*
		 * Use STL Java library for file saving.
		 */
		StlBinaryFile out = new StlBinaryFile(new FileOutputStream("./cube" + "_" + System.currentTimeMillis() + "_"
				+ volumes[0] + "_" + volumes[1] + "_" + volumes[2] + ".stl"));

		/*
		 * Many voxels to STL storage.
		 */ {
			// out.writeToFile(voxelsToModel().toCSG().toFacets());
		}

		/*
		 * Single shape to STL file storage.
		 */ {
			System.out.println("Shape calculated ...");
			CSG csg = voxelsToCSG(0, voxels.length, 0, voxels[0].length, 0, voxels[0][0].length);
			System.out.println("CSG calculated ...");
			List<Facet> facets = csg.toFacets();
			System.out.println("Facets calculated ...");
			out.writeToFile(facets);
			System.out.println("File written ...");
		}

		/*
		 * Close output file with buffer flush.
		 */
		out.close();

		/*
		 * Many voxels to SCAD file storage.
		 */ {
			// ModelToFile out = new ModelToFile( new File("./bin/cube" +
			// System.currentTimeMillis() + ".scad") );
			// out.addModel( voxelsToModel() ).saveToFile( ColorHandlingContext.DEFAULT );
		}

		System.out.println("Stop ...");
	}
}
