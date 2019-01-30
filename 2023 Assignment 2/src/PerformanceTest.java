import java.util.ArrayList;
import java.util.List;

/**
 * @Purpose: The PerformanceTest class is used to compare the implemented
 *           algorithms in term of time and the number of sheets used
 *
 *           You can add additional methods if you need to in this class
 * 
 * @author RYK
 * @since 30/10/2018 extended by @author Talha Sheikh
 */

public class PerformanceTest {

	public static void main(String[] args) {

		// total number of tests - you need to CHANGE this value
		int noOfTests = 10;

		// number of repetitions for each test - you need to CHANGE this value
		int noOfRep = 4;

		// number of shapes needed for the first run - you need to CHANGE this
		// value
		int noOfShapes = 1000;

		// the increment in the number of shapes - you need to CHANGE this value
		int increment = 1000;

		System.out.println("\t\t***********************************************");
		System.out.println("\t\t*********** Performance analysis **************");
		System.out.println("\t\t***********************************************\n");

		// Used in order to create the same random list for both algorithms to ensure
		// that if necessary, the value produced is different
		int random = (int) (Math.random() * 10000) + 1;
		Generator g = new Generator();
		List<Shape> shapes = g.generateShapeList(5);
		List<Shape> shapesRandom = g.generateShapeList(random);

		// Testing both algorithms with the same values
		testAlgorithms(shapes, "Shared Values", noOfRep);

		// Testing both algorithms with the same values on a random scale (most
		// likely larger)
		testAlgorithms(shapesRandom, "Shared Random", noOfRep);

		// Testing both algorithms with 5 randomly generated values
		testAlgorithms(g.generateShapeList(10), "10 Random Values", noOfRep);

		// Testing both algorithms with 10 randomly generated values
		testAlgorithms(g.generateShapeList(10), "10 Random Values", noOfRep);

		// Testing both algorithms with 0 values
		testAlgorithms(g.generateShapeList(0), "0 Values Test", noOfRep);

		// Testing both algorithms with -1 values
		testAlgorithms(g.generateShapeList(-1), "Negative Test", noOfRep);

		// Testing both algorithms with 100 values
		testAlgorithms(g.generateShapeList(100), "100 Values", noOfRep);

		for (int i = 0; i < noOfTests; i++) {
			testAlgorithms(g.generateShapeList(noOfShapes), "Incremented Test", noOfRep);
			noOfShapes += increment;
		}

		// Testing both algorithms with 20000 values
		testAlgorithms(g.generateShapeList(20000), "20000 Values", noOfRep);

	}

	/*
	 * Tests both algorithms and displays them side by side
	 * in order to easily analyse the differences between 
	 * each algorithm and the time taken for each.
	 */
	
	public static void testAlgorithms(List<Shape> shapes, String testType, int noOfRep) {
		System.out.println("________________________________________________________________________________\n");
		System.out.println("Algorithms\tNEXT FIT\t\tFirst FIT\t\t" + testType);
		System.out.println("________________________________________________________________________________");

		int shapeNo = 0;

		long averageTimeNF = 0;
		int averageComparisonsNF = 0;
		int averageSheetsNF = 0;
		int averageShelvesNF = 0;

		long averageTimeFF = 0;
		int averageComparisonsFF = 0;
		int averageSheetsFF = 0;
		int averageShelvesFF = 0;

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println(" Tests:");
		System.out.println("--------------------------------------------------------------------------------");

		for (int i = 0; i < noOfRep; i++) {
			Algorithms nFit = new Algorithms();
			Algorithms fFit = new Algorithms();
			List<Sheet> testSheet = new ArrayList<Sheet>();

			testSheet = nFit.nextFit(shapes);
			averageTimeNF += nFit.getTime();
			averageComparisonsNF += nFit.getComparisons();
			averageSheetsNF += nFit.getSheetNo();
			averageShelvesNF += nFit.getShelfNo();
			shapeNo = nFit.getShapeNo();

			testSheet = fFit.firstFit(shapes);
			averageTimeFF += fFit.getTime();
			averageComparisonsFF += fFit.getComparisons();
			averageSheetsFF += fFit.getSheetNo();
			averageShelvesFF += fFit.getShelfNo();

			System.out.println("Repetion: " + (i + 1) + "\t|\t\t\t| ");
			System.out.println("Time Taken:\t| " + nFit.getTime() + "(ms)\t\t\t| " + fFit.getTime() + "(ms)");
			System.out.println("Comparisons:\t| " + nFit.getComparisons() + "\t\t\t| " + fFit.getComparisons());
			System.out.println("Sheets:\t\t| " + nFit.getSheetNo() + "\t\t\t| " + fFit.getSheetNo());
			System.out.println("Shelves:\t| " + nFit.getShelfNo() + "\t\t\t| " + fFit.getShelfNo());
			System.out.println("--------------------------------------------------------------------------------");

		}

		averageTimeNF = averageTimeNF / 4;
		averageComparisonsNF = averageComparisonsNF / 4;
		averageSheetsNF = averageSheetsNF / 4;
		averageShelvesNF = averageShelvesNF / 4;

		averageTimeFF = averageTimeFF / 4;
		averageComparisonsFF = averageComparisonsFF / 4;
		averageSheetsFF = averageSheetsFF / 4;
		averageShelvesFF = averageShelvesFF / 4;

		System.out.println(" Results:");
		System.out.println("--------------------------------------------------------------------------------");

		System.out.println("Average Time:\t| " + averageTimeNF + "(ms)\t\t\t| " + averageTimeFF + "(ms)");
		System.out.println("Average Comps:\t| " + averageComparisonsNF + "\t\t\t| " + averageComparisonsFF);
		System.out.println("Average Sheets:\t| " + averageSheetsNF + "\t\t\t| " + averageSheetsFF);
		System.out.println("Average Shelves:| " + averageShelvesNF + "\t\t\t| " + averageShelvesFF);

		System.out.println("--------------------------------------------------------------------------------");
		System.out.println(" Shapes Provided: " + shapeNo + "\n");

	}

}
