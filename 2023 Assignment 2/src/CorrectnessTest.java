import java.util.ArrayList;
import java.util.List;

/**
 * @Purpose: The CorrectnessTest class is used to validate the correctness of
 *           the implemented algorithms You can add additional methods if you
 *           need
 * 
 * @author RYK
 * @since 30/10/2018 extended by @author Talha Sheikh
 */

public class CorrectnessTest {
	public static void main(String[] args) {
		System.out.println("\t\t*********************************************");
		System.out.println("\t\t*********** Correctness testing *************");
		System.out.println("\t\t*********************************************\n");

		specifiedTest();

		standardTests();

		System.out.println();
		testOutput();

		// THESE TWO METHODS MUST FAIL WITH AN EXCEPTION!
		shapeBoundTest();
		shelfBoundTest();
	}

	/*
	 * Method used to check shape size and ensure exception is produced
	 */

	public static void shapeBoundTest() {
		int maxHeightPlus = 251;
		int maxWidthPlus = 301;

		Shape shape = new Shape(maxWidthPlus, maxHeightPlus);

	}

	/*
	 * Method used to check shape size and ensure exception is produced
	 */

	public static void shelfBoundTest() {
		int maxHeightPlus = 251;
		int maxWidthPlus = 301;

		Shelf shelf = new Shelf();
		shelf.place(new Shape(maxWidthPlus, maxHeightPlus));

	}

	/*
	 * Method used to check that the two algorithms work correctly The first
	 * algorithm will produce three shelves on a single sheet however the second
	 * will produce two as it is the First Fit algorithm and the shapes provided
	 * were created specifically for this as they fit together.
	 */

	public static void specifiedTest() {
		List<Shape> shapes = new ArrayList<Shape>();

		Shape s1 = new Shape(10, 10);
		Shape s2 = new Shape(300, 250);
		Shape s3 = new Shape(290, 240);

		shapes.add(s1);
		shapes.add(s2);
		shapes.add(s3);

		testNextFit(shapes, "Specified");
		testFirstFit(shapes, "Specified");

	}

	/*
	 * Method testing that the algorithm works correctly with a range of provided
	 * values including a negative value.
	 */

	public static void standardTests() {
		// Used in order to create the same random list for both algorithms to ensure
		// that if necessary, the value produced is different
		int random = (int) (Math.random() * 10000) + 1;
		Generator g = new Generator();
		List<Shape> shapes = g.generateShapeList(5);
		List<Shape> shapesRandom = g.generateShapeList(random);

		// Testing both algorithms with the same values
		testNextFit(shapes, "Shared Values");
		testFirstFit(shapes, "Shared Values");

		// Testing both algorithms with the same values on a random scale (most
		// likely larger)
		testNextFit(shapesRandom, "Shared Random");
		testFirstFit(shapesRandom, "Shared Random");

		// Testing both algorithms with 5 randomly generated values
		testNextFit(g.generateShapeList(10), "10 Random Values");
		testFirstFit(g.generateShapeList(10), "10 Random Values");

		// Testing both algorithms with 10 randomly generated values
		testNextFit(g.generateShapeList(10), "10 Random Values");
		testFirstFit(g.generateShapeList(10), "10 Random Values");

		// Testing both algorithms with 0 values
		testNextFit(g.generateShapeList(0), "0 Values Test");
		testFirstFit(g.generateShapeList(0), "0 Values Test");

		// Testing both algorithms with -1 values
		testNextFit(g.generateShapeList(-1), "Negative Test");
		testFirstFit(g.generateShapeList(-1), "Negative Test");

		// Testing both algorithms with 100 values
		testNextFit(g.generateShapeList(100), "100 Values");
		testFirstFit(g.generateShapeList(100), "100 Values");

		// Testing both algorithms with 10000 values
		testNextFit(g.generateShapeList(10000), "10000 Values");
		testFirstFit(g.generateShapeList(10000), "10000 Values");
	}

	/*
	 * Method used to produce Next Fit Algorithm results
	 */

	public static void testNextFit(List<Shape> shapes, String testType) {
		System.out.println("________________________________________________________________________________\n");
		System.out.println("\tNEXT FIT ALGORITHM\t\t\t\t\t" + testType);
		System.out.println("________________________________________________________________________________");
		Algorithms a = new Algorithms();
		List<Sheet> testSheet = new ArrayList<Sheet>();

		testSheet = a.nextFit(shapes);
		display(testSheet);

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Amount of Sheets: " + a.getSheetNo());
		System.out.println("Amount of Shelves: " + a.getShelfNo());
		System.out.println("Amount of Shapes: " + a.getShapeNo());

	}

	/*
	 * Method used to produce First Fit Algorithm results
	 */

	private static void testFirstFit(List<Shape> shapes, String testType) {
		System.out.println("________________________________________________________________________________\n");
		System.out.println("\tFIRST FIT ALGORITHM\t\t\t\t\t" + testType);
		System.out.println("________________________________________________________________________________");
		Algorithms a = new Algorithms();
		List<Sheet> testSheet = new ArrayList<Sheet>();

		testSheet = a.firstFit(shapes);
		display(testSheet);

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println("Amount of Sheets: " + a.getSheetNo());
		System.out.println("Amount of Shelves: " + a.getShelfNo());
		System.out.println("Amount of Shapes: " + a.getShapeNo());

	}

	/*
	 * Method used to output the values produced in each algorithm in order to
	 * ensure that they are correct via the structure provided
	 */

	public static void display(List<Sheet> sheetList) {
		System.out.println();
		for (int sheetCounter = 0; sheetCounter < sheetList.size(); sheetCounter++) {

			System.out.println(sheetList.get(sheetCounter).toString() + "\t\t\t\t\t(No." + (sheetCounter + 1) + ")\n");

			List<Shelf> shelfList = sheetList.get(sheetCounter).getShelves();

			for (int shelfCounter = 0; shelfCounter < shelfList.size(); shelfCounter++) {
				System.out.println(shelfList.get(shelfCounter).toString() + "\t(Shelf No. " + (shelfCounter + 1) + ")");

				List<Shape> shapeList = shelfList.get(shelfCounter).getShapes();

				for (int shapeCounter = 0; shapeCounter < shapeList.size(); shapeCounter++) {
					System.out.println((shapeCounter + 1) + ": " + shapeList.get(shapeCounter).toString());
				}

				System.out.println();

			}
			System.out.println();

		}
	}

	/*
	 * Method to check output values as instructed in task 1
	 */

	public static void testOutput() {
		Shape shape = new Shape(3, 2);

		Shelf shelf = new Shelf();
		Shelf shelf2 = new Shelf();
		shelf.place(shape);

		Sheet sheet = new Sheet();
		sheet.addShelf(shelf);

		Generator g = new Generator();

		for (Shape s : g.generateShapeList(5)) {
			shelf2.place(s);
		}

		sheet.addShelf(shelf2);

		System.out.println(sheet);
		System.out.println(shelf);
		System.out.println(shelf2);
		System.out.println(shape);
	}

}
