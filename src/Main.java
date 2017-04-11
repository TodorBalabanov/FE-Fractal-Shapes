import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

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
	 * Side size of a cubic 3D space.
	 */
	private static int SPACE_SIDE_SIZE = (int) Math.pow(3, DETAILS_LEVEL);

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

		for (int index = 0, x = sides[0], dx = (sides[1] - sides[0] + 1) / 3; x < sides[1]; x += dx) {
			for (int y = sides[2], dy = (sides[3] - sides[2] + 1) / 3; y < sides[3]; y += dy) {
				for (int z = sides[4], dz = (sides[5] - sides[4] + 1) / 3; z < sides[5]; z += dz, index++) {
					switch (index) {
					case 0:
					case 1:
					case 2:
					case 3:
					case 5:
					case 6:
					case 7:
					case 9:
					case 11:
					case 15:
					case 17:
					case 18:
					case 19:
					case 20:
					case 21:
					case 23:
					case 24:
					case 25:
					case 26:
						warp(new int[] { x, x + dx - 1, y, y + dy - 1, z, z + dz - 1 });
						break;
					case 4:
					case 10:
					case 12:
					case 13:
					case 14:
					case 16:
					case 22:
						cube(level - 1, new int[] { x, x + dx - 1, y, y + dy - 1, z, z + dz - 1 });
						break;
					}
				}
			}
		}
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
		String stl = "   facet normal -1 0 0\r\n" + "      outer loop\r\n" + "         vertex %d %d %d\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "      endloop\r\n"
				+ "   endfacet\r\n" + "   facet normal -1 0 0\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 0 1\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 0 1\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 1 0 0\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 1 0 0\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 0 -1\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 0 -1\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 1 0\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 1 0\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 -1 0\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n" + "   facet normal 0 -1 0\r\n" + "      outer loop\r\n"
				+ "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n" + "         vertex %d %d %d\r\n"
				+ "      endloop\r\n" + "   endfacet\r\n";

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
	private static void stl(DataOutputStream out) throws IOException {
		out.writeUTF("solid Shape" + System.currentTimeMillis());
		out.writeUTF("\r\n");

		for (int x = 0; x < voxels.length; x++) {
			for (int y = 0; y < voxels[x].length; y++) {
				for (int z = 0; z < voxels[x][y].length; z++) {
					if (voxels[x][y][z] == 1) {
						out.writeUTF(cubeToSTL(new int[] { x, x + 1, y, y + 1, z, z + 1 }));
					}
				}
			}
		}

		out.writeUTF("endsolid");
		out.writeUTF("\r\n");
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

		stl(new DataOutputStream(new FileOutputStream("./bin/cube.stl")));
		// stl(new DataOutputStream(System.out));

		// DataOutputStream out = new DataOutputStream(new
		// FileOutputStream("./bin/cube.stl"));
		// DataOutputStream out = new DataOutputStream(System.out);
		//
		// out.writeUTF("solid Cube10x10x10x90x90x90\r\n");
		// out.writeUTF(cubeToSTL(new int[] { 10, 90, 10, 90, 10, 90 }));
		// out.writeUTF("endsolid\r\n");
		
		System.out.println("Stop ...");

	}
}
