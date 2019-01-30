
/**
 * @Purpose: The Algorithms class contains the two algorithms you have to implement  
 * Do NOT modify the names and signatures of the two algorithm methods
 * 
 * @author  RYK
 * @since   30/10/2018
 * extended by @author Talha Sheikh
 *
 **/

import java.util.ArrayList;
import java.util.List;

public class Algorithms {
	private long begin;
	private long time;

	private int sheetNo;
	private int shelfNo;
	private int shapeNo;

	private int comparisons;

	/**
	 * This method is used to implement the next fit algorithm
	 * 
	 * @param shapes:a
	 *            list of shapes representing customer requests(shapes to be
	 *            cut/placed)
	 * @return a list of the sheets used to fulfil all customer requests (i.e. place
	 *         all the shapes). e.g. if you pass a "shapes" list that has two shapes
	 *         {(w=200,h=200),(w=50,h=100)}, then the returned list of sheets should
	 *         show us all the information needed (e.g. that one sheet is used, this
	 *         sheet has one shelf and this shelf has two shapes in it). In the test
	 *         program, you can use the returned list to retrieve the total number
	 *         of sheets used.
	 **/

	public List<Sheet> nextFit(List<Shape> shapes) {
		begin = System.currentTimeMillis();
		int comps = 0;
		List<Sheet> usedSheets = new ArrayList<Sheet>();
		int sheetCounter = 0; // locates and utilises specific sheet
		int shelfCounter = 0; // locates and utilises specific shelf
		int shapeCounter = 0; // locates and utilises specific shape

		for (Shape s : shapes) {
			comps++;
			if (usedSheets.size() == 0) {
				// If the size of the ArrayList is 0, adds a new sheet and starts the algorithm
				usedSheets.add(new Sheet());
				usedSheets.get(sheetCounter).addShelf(new Shelf());
				usedSheets.get(sheetCounter).getShelves().get(sheetCounter).place(s);

			} else if (shapeCounter == new Sheet().SHAPE_LIMIT) {
				// If the amount of shapes on a single sheet has surpassed the limit,
				// a new sheet and shelf are created and all counter values are reset
				shapeCounter = 0;
				shelfCounter = 0;
				sheetCounter++;

				usedSheets.add(new Sheet());
				usedSheets.get(sheetCounter).getShelves().get(shelfCounter).place(s);

			} else {
				// To be used to check comparison methods
				Shelf comparisonShelf = usedSheets.get(sheetCounter).getShelves().get(shelfCounter);
				Sheet comparisonSheet = usedSheets.get(sheetCounter);
				// temporary shelf for inserting new shapes into the sheets, new or current
				Shelf shelfInsert = new Shelf();
				if (compareShelfHeight(comparisonShelf, s)) {
					if (compareShelfWidth(comparisonShelf, s)) {
						usedSheets.get(sheetCounter).getShelves().get(shelfCounter).place(s);

					} else {
						// inserts the shape into the temporary shelf if the previous
						// shelf had used too much width
						shelfInsert.place(s);

						shelfCounter++;

						if (compareSheetHeight(comparisonSheet, shelfInsert)) {
							// Inserts the new shelf if there is enough height space within
							// the current sheet
							usedSheets.get(sheetCounter).addShelf(shelfInsert);

						} else {
							// If the total shelve's heights surpasses the currents sheets
							// maximum height, Creates a new sheet and inserts the temporary
							// shelf along with its value and also resets the shape and shelf
							// counters in order to correspond with the new sheet
							usedSheets.add(new Sheet());
							shapeCounter = 0;
							sheetCounter++;

							usedSheets.get(sheetCounter).addShelf(shelfInsert);
							shelfCounter = 0;
						}
					}

				} else {
					// If the shape is too tall for the current shelf it is inserted into
					// temporary shelf for further evaluation. Increments @param shelfCounter
					shelfInsert.place(s);

					shelfCounter++;

					if (!compareSheetHeight(comparisonSheet, shelfInsert)) {
						usedSheets.add(new Sheet());
						shapeCounter = 0;
						sheetCounter++;

						usedSheets.get(sheetCounter).addShelf(shelfInsert);
						shelfCounter = 0;

					} else {
						usedSheets.get(sheetCounter).addShelf(shelfInsert);

					}
				}
			}
		}

		time = System.currentTimeMillis() - begin; // Evaluates the time taken for the algorithm to complete.
		setComparisons(comps);

		for (Sheet sheet : usedSheets) {
			sheetNo++;
			for (Shelf shelf : sheet.getShelves()) {
				shelfNo++;
				for (Shape shape : shelf.getShapes()) {
					shapeNo++;
				}
			}
		}

		return usedSheets;

	}

	/**
	 * This method is used to implement the first fit algorithm
	 * 
	 * @param shapes:a
	 *            list of shapes representing customer requests (shapes to be
	 *            cut/placed)
	 * @return a list of the sheets used to fulfil all customer requests (i.e. place
	 *         all the shapes). e.g. if you pass a "shapes" list that has two shapes
	 *         {(w=200,h=200),(w=50,h=100)}, then the returned list of sheets should
	 *         show us all the information needed (e.g. that one sheet is used, this
	 *         sheet has one shelf and this shelf has two shapes in it). In the test
	 *         program, you can use the returned list to retrieve the total number
	 *         of sheets used
	 **/

	public List<Sheet> firstFit(List<Shape> shapes) {
		begin = System.currentTimeMillis();
		List<Sheet> usedSheets = new ArrayList<Sheet>();
		boolean first = true; // Boolean value to check if first shape or not

		for (Shape s : shapes) {
			comparisons++;

			if (!first) {
				// If not the first shape, proceeds to check prior sheets and shelves for
				// space to insert current shape
				PriorFit(s, usedSheets);
			} else {
				// If first value, creates new sheet with new shelf and inserts the shape
				// as the initial value. Also sets @param first to false to ensure this
				// process is not repeated
				usedSheets.add(new Sheet());
				usedSheets.get(0).addShelf(new Shelf());
				usedSheets.get(0).getShelves().get(0).place(s);

				first = false;

			}
		}

		time = System.currentTimeMillis() - begin; // Evaluates the time taken for the algorithm to complete.
		
		for (Sheet sheet : usedSheets) {
			sheetNo++;
			for (Shelf shelf : sheet.getShelves()) {
				shelfNo++;
				for (Shape shape : shelf.getShapes()) {
					shapeNo++;
				}
			}
		}

		return usedSheets;

	}

	public void PriorFit(Shape s, List<Sheet> usedSheets) {
		for (Sheet sheet : usedSheets) {
			comparisons++;

			for (Shelf shelf : sheet.getShelves()) {
				comparisons++;
				// Checks through all sheets and shelves prior to find
				// a possible location for insert
				if ((compareShelfWidth(shelf, s)) && (compareShelfHeight(shelf, s))) {
					shelf.place(s);
					return;

				}
			}
		}
		// If a suitable location is not found, a temporary shelf is created
		// in order to find a sheet with a suitable location for a shelf
		// insertion that does not exceed the sheet height limit.
		Shelf shelfInsert = new Shelf();
		shelfInsert.place(s);
		// A counter value that increments in order to direct correct insertion
		int sheetCounter = 0;
		for (Sheet sheet : usedSheets) {
			comparisons++;
			if (compareSheetHeight(sheet, shelfInsert)) {
				// If there is space for the shelf on prior sheet, inserts shelf
				sheet.addShelf(shelfInsert);
				return;

			}
			sheetCounter++;
		}
		// Adds a new Sheet to the ArrayList and inserts the provided shelf
		usedSheets.add(new Sheet());
		usedSheets.get(sheetCounter).addShelf(shelfInsert);

	}

	public static boolean compareShelfHeight(Shelf shelf, Shape shape) {
		// Compares the shelf and shape height
		if (shelf.getHeight() < shape.getHeight()) {
			return false;
		}
		return true;

	}

	public static boolean compareShelfWidth(Shelf shelf, Shape shape) {
		// Compares the (remaining) shelf and shape width
		if ((shelf.getWidth() + shape.getWidth()) > new Sheet().getWidth()) {
			return false;
		}
		return true;

	}

	public static boolean compareSheetHeight(Sheet sheet, Shelf shelf) {
		// Compares the (remaining) sheet and shelf height.
		if ((shelf.getHeight() + sheet.allShelvesHeight()) > sheet.getHeight()) {
			return false;
		}
		return true;

	}

	public long getTime() {
		return time;
	}

	public int getSheetNo() {
		return sheetNo;
	}

	public int getShelfNo() {
		return shelfNo;
	}

	public int getShapeNo() {
		return shapeNo;
	}

	public int getComparisons() {
		return comparisons;
	}

	public void setComparisons(int comparisons) {
		this.comparisons = comparisons;
	}

}