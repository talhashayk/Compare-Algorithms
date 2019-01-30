import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Purpose: The SortedTest class is used to compare the implemented algorithms
 *           in term of the number of sheets used WHEN the list of shapes is
 *           SORTED
 *
 *           You can add additional methods if you need to in this class
 * 
 * @author RYK
 * @since 30/10/2018 extended by @author Talha Sheikh
 */

public class SortedTest {
	public static void main(String[] args) {
		System.out.println("\t\t*********************************************");
		System.out.println("\t\t**************** Sorted Test ****************");
		System.out.println("\t\t*********************************************");

		Generator g = new Generator();

		// A group of tests with values provided that
		// range in size.		
		testDifference(g.generateShapeList(0));
		testDifference(g.generateShapeList(-1));
		testDifference(g.generateShapeList(5));
		testDifference(g.generateShapeList(10));
		testDifference(g.generateShapeList(100));
		testDifference(g.generateShapeList(1000));
		testDifference(g.generateShapeList(10000));
		testDifference(g.generateShapeList(20000));


	}

	/*
	 * Tests both algorithms with different forms of sorted shapes
	 * and produces results in terms of sheets created to evaluate
	 * the efficiency difference.
	 */
	
	public static void testDifference(List<Shape> shapes) {
		Algorithms unSortedNF = new Algorithms();
		Algorithms AscOrderNF = new Algorithms();
		Algorithms DesOrderNF = new Algorithms();

		Algorithms unSortedFF = new Algorithms();
		Algorithms AscOrderFF = new Algorithms();
		Algorithms DesOrderFF = new Algorithms();

		List<Sheet> sheets = new ArrayList<Sheet>();
		sheets = unSortedNF.nextFit(shapes);
		sheets = unSortedFF.firstFit(shapes);
		List<Shape> ascendingShapes = shapes;
		Collections.sort(ascendingShapes);
		sheets = AscOrderNF.nextFit(ascendingShapes);
		sheets = AscOrderFF.firstFit(ascendingShapes);
		List<Shape> descendingShapes = ascendingShapes;
		Collections.reverse(descendingShapes);
		sheets = DesOrderNF.nextFit(descendingShapes);
		sheets = DesOrderFF.firstFit(descendingShapes);

		System.out.println("________________________________________________________________________________");

		System.out.println("\n Shapes: " + unSortedNF.getShapeNo() + "\tNextFitSheets(unSorted): " + unSortedNF.getSheetNo() + "\tFirstFitSheets(unSorted): "
				+ unSortedFF.getSheetNo());
		System.out.println("\t\tNextFitSheets(AscOrder): " + AscOrderNF.getSheetNo() + "\tFirstFitSheets(AscOrder): "
				+ AscOrderFF.getSheetNo());
		System.out.println("\t\tNextFitSheets(DesOrder): " + DesOrderNF.getSheetNo() + "\tFirstFitSheets(DesOrder): "
				+ DesOrderFF.getSheetNo());



	}

}
