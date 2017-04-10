import java.util.Arrays;

/**
 * Main class for application start.
 * 
 * @author Todor Balabanov
 */
public class Main {
	/**
	 * 3D space for the shape as discrete voxels.
	 */
	private static byte voxels[][][] = new byte[729][729][729];

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
System.out.println(Arrays.toString(sides));

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
	 * Save 3D space in a STL ASCII file.
	 * 
	 * @param string
	 *            File name.
	 */
	private static void stl(String name) {
		// TODO Save in STL format.
	}

	/**
	 * Single entry point.
	 * 
	 * @param args
	 *            Command line arguments.
	 */
	public static void main(String[] args) {
		cube(6, new int[] { 0, 728, 0, 728, 0, 728 });
		stl("cube.stl");
	}
}
