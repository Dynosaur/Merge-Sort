import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author Alejandro Doberenz
 * @version 4/5/2019
 *
 * This class contains the merge sort algorithm, as well as the selection sort algorithm. It also contains
 * trim(), which allows one to select a range from inside an existing list and make a new one from those values,
 * and merge, which sorts two organized lists into one.
 * It also contains a recursive merge sort which tells you every action it does to sort the list. There are several
 * instances of overloading which yes, I am aware of. But I like that the public ones (Which are meant to be used
 * by someone else) have a cleaner signature than the private ones (Which are the ones that actually carry out the work)
 */
public class MergeSort {

    // Begins to use a recursive merge sort on the given list
    public static ArrayList<Comparable> recursiveMergeSort(ArrayList<Comparable> list) {
        System.out.println("Original List: " + list);
        return recursiveMergeSort(0, list);
    }

    // <editor-fold defaultstate="collapsed" desc="Recursive Merge Sort Backend">
    private static ArrayList<Comparable> recursiveMergeSort(int iteration, ArrayList<Comparable> list) {

        if(list.size() <= 0) throw new IllegalArgumentException("List size is less than 1.");

        createSpaces(iteration);
        System.out.print(recursiveStatus(list) + ": " + list + " -> ");

        if(list.size() == 1) {
            System.out.println(list);
        }
            if (list.size() == 2) {
                ArrayList<Comparable> sortedList = selectionSort(false, list);
                System.out.println(sortedList);
                return sortedList;
            } else if (list.size() != 1) {

                int midpoint = list.size() / 2;
                ArrayList<Comparable> firstHalf = trim(list, 0, midpoint - 1);
                ArrayList<Comparable> secondHalf = trim(list, midpoint, list.size() - 1);
                System.out.println(firstHalf + " and " + secondHalf);

                firstHalf = new ArrayList<>(recursiveMergeSort(iteration + 1, firstHalf));
                secondHalf = new ArrayList<>(recursiveMergeSort(iteration + 1, secondHalf));
                createSpaces(iteration);
                System.out.println("MERGING: " + firstHalf + " + " + secondHalf);

                list = new ArrayList<>(merge(firstHalf, secondHalf));
                createSpaces(iteration);
                System.out.println("MERGED: " + list + "\n");

                return list;
            } else return list;
    }

    private static String recursiveStatus(ArrayList<Comparable> list) {
        if(list.size() == 1) return "NO CHANGE";
        if(list.size() == 2) {
            if(isSorted(list)) return "NO CHANGE";
            else return "SORTED";
        } else {
            return "SPLIT";
        }
    }

    private static void createSpaces(int iteration) {
        StringBuilder space = new StringBuilder();
        for(int i = 0; i < iteration; i++)
            space.append("    ");
        System.out.print(space.toString());
    }
    // </editor-fold>

    // Sort a single list using merge sort
    public static ArrayList<Comparable> mergeSort(ArrayList<Comparable> list) {
        return mergeSort(true, list);
    }

    // Sort two lists using merge sort
    public static ArrayList<Comparable> mergeSort(ArrayList<Comparable> listA, ArrayList<Comparable> listB) {
        return mergeSort(true, listA, listB);
    }

    // Sort a list using selection sort
    public static ArrayList<Comparable> selectionSort(ArrayList<Comparable> list) {
        return selectionSort(true, list);
    }

    // Checks if a comparable array list is sorted already.
    public static boolean isSorted(ArrayList<Comparable> list) {

        if(list.size() < 1) throw new IllegalArgumentException("List size cannot be less than 1.");

        ListIterator<Comparable> iterator = list.listIterator();
        iterator.next();
        while(iterator.hasNext()) {
            Comparable next = iterator.next();
            iterator.previous();
            Comparable previous = iterator.previous();
            if(next.compareTo(previous) == -1) return false;
            iterator.next();
            iterator.next();
        }

        return true;
    }

    // <editor-fold defaultstate="collapsed" desc="Standard Merge Sort Backend">
    /**
     * Sorts a single list using merge sort. It first divides the list into halves, and then uses selection sort
     * to organize those halves. It then uses the merge method to combine them into one large list.
     *
     * @param verbose   Whether or not to print out results
     * @param list      The list to be sorted
     * @return          A sorted list from least to greatest
     */
    private static ArrayList<Comparable> mergeSort(boolean verbose, ArrayList<Comparable> list) {

        if(list.size() <= 0) throw new IllegalArgumentException("List size is less than 1.");

        if(verbose) System.out.println("Original List: " + list);

        int midpoint = list.size() / 2;
        ArrayList<Comparable> firstHalf = trim(list, 0, midpoint-1);
        ArrayList<Comparable> secondHalf = trim(list, midpoint, list.size()-1);

        if(verbose) System.out.println("Midpoint Index: " + midpoint + "\nList A: " + firstHalf + "\nList B: " + secondHalf);

        selectionSort(verbose, firstHalf);
        selectionSort(verbose, secondHalf);

        list = merge(firstHalf, secondHalf);

        if(verbose) System.out.println("Merged List: " + list);

        return list;
    }

    /**
     * Sorts two lists using merge sort. It uses selection sort to get the initial lists in order, and then uses
     * the merge method to combine the two into one list.
     *
     * @param verbose   Whether or not to print out results
     * @param listA     The first list to be sorted
     * @param listB     The second list to be sorted
     * @return          A sorted list from least to greatest
     */
    private static ArrayList<Comparable> mergeSort(boolean verbose, ArrayList<Comparable> listA, ArrayList<Comparable> listB) {

        if(listA.size() <= 0) throw new IllegalArgumentException("List size is less than 1.");
        if(listB.size() <= 0) throw new IllegalArgumentException("List size is less than 1.");

        if(verbose) System.out.println( "List A:\n\t" +
                "Length: " + listA.size() + "\n\t" +
                "Values: " + listA + "\n" +
                "List B:\n\t" +
                "Length: " + listB.size() + "\n\t" +
                "Values: "+ listB + "\n"
        );
        selectionSort(verbose, listA);
        selectionSort(verbose, listB);

        ArrayList<Comparable> mergedList = merge(listA, listB);

        if(verbose) System.out.println("Merged List: " + mergedList);

        return new ArrayList<>(merge(listA, listB));
    }

    /**
     * Sorts a list using the selection sort algorithm.
     *
     * @param verbose   Whether to print results or not
     * @param list      The list to be sorted
     * @return          A sorted list from least to greatest
     */
    private static ArrayList<Comparable> selectionSort(boolean verbose, ArrayList<Comparable> list) {

        if(list.size() <= 0) throw new IllegalArgumentException("List size is less than 1.");

        if(verbose) System.out.println("Original List:\t" + list);

        int smallestIndex;
        Comparable temp;

        for(int outer = 0; outer < list.size() - 1; outer++) {

            smallestIndex = outer;

            for(int inner = outer + 1; inner < list.size(); inner++)
                if(list.get(inner).compareTo(list.get(smallestIndex)) == -1)
                    smallestIndex = inner;

            temp = list.get(outer);
            list.set(outer, list.get(smallestIndex));
            list.set(smallestIndex, temp);

        }

        if(verbose) System.out.println("Sorted List:\t" + list + "\n");

        return list;
    }

    /**
     * Copies a section of an existing list.
     * @param list The list to be copied from
     * @param start The inclusive index to start copying from
     * @param end The inclusive index to stop copying from
     * @return A new list that is a segment of the original list
     */
    private static <T> ArrayList<T> trim(ArrayList<T> list, int start, int end) {
        ArrayList<T> newList = new ArrayList<>();
        for(int i = start; i <= end; i++)
            newList.add(list.get(i));
        return newList;
    }

    /**
     * Sorts through two sorted lists and combines them into one list. This will only work if BOTH lists
     * are sorted from least to greatest.
     *
     * @param   listA The first sorted list
     * @param   listB The second sorted list
     * @return  A sorted list, least to greatest, that contains both lists' values
     */
    private static ArrayList<Comparable> merge(ArrayList<Comparable> listA, ArrayList<Comparable> listB) {
        ArrayList<Comparable> newList = new ArrayList<>();
        ListIterator<Comparable> listAIt = listA.listIterator();
        ListIterator<Comparable> listBIt = listB.listIterator();

        for(int i = 0; i < listA.size()+listB.size(); i++) {

            if(!listAIt.hasNext()) {
                newList.add(listBIt.next());
                continue;
            }

            if(!listBIt.hasNext()) {
                newList.add(listAIt.next());
                continue;
            }

            newList.add(
                (listA.get(listAIt.nextIndex()).compareTo
                (listB.get(listBIt.nextIndex())) == 1)
                ? listBIt.next()
                : listAIt.next()
            );
        }
        return newList;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Genesis Methods">
    public static ArrayList<Comparable> generateList() {
        return generateList(10, 0, 10);
    }

    public static ArrayList<Comparable> generateList(int size) {
        return generateList(size, size);
    }

    public static ArrayList<Comparable> generateList(int size, int max) {
        return generateList(size, 0, max);
    }

    /**
     * Generates a random list populated with integers. You can control the floor and ceiling of the randomly
     * generated numbers, as well as the size of the overall list.
     *
     * @param size The desired size of the list
     * @param min The inclusive minimum of any random integer
     * @param max The inclusive maximum of any random integer
     * @return An ArrayList filled with random integers.
     */
    public static ArrayList<Comparable> generateList(int size, int min, int max) {
        if(size < 1) throw new IllegalArgumentException("List size is less than 1.");
        if(min > max) throw new IllegalArgumentException("Minimum cannot be larger than maximum");
        ArrayList<Comparable> newList = new ArrayList<>(size);
        for(int i = 0; i < size; i++)
            newList.add(0, random(min, max));
        return newList;
    }

    /**
     * Generates a random integer, however compared to java.util.Random this contains a floor you can
     * control in one method. Uses a primitive form of rounding to allow the minimum and maximum a higher
     * chance of being generated.
     *
     * @param min   The inclusive minimum of a randomly generated number
     * @param max   The inclusive maximum of a randomly generated number
     * @return      A randomly generated number within the provided range
     */
    public static int random(int min, int max) {
        if(min > max) throw new IllegalArgumentException("Minimum cannot be less than maximum.");
        double random = (1-Math.random())*(max-min)+min;    // Generate a random double in between the min and max
        return (random - (int) random <= 0.5D) ? (int) Math.floor(random) : (int) Math.ceil(random);    // Round accordingly and cast to int
    }
    // </editor-fold>

    public static void main(String[] args) {
        // Change the size argument to alter the size of the randomly generated list
        System.out.println(recursiveMergeSort(generateList(10)));
    }

}