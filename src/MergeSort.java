import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * @author Alejandro Doberenz
 * @version 4/3/2019
 *
 * This class contains the merge sort algorithm, as well as the selection sort algorithm. It also contains
 * trim(), which allows one to select a range from inside an existing list and make a new one from those values,
 * and merge, which sorts two organized lists into one.
 */
public class MergeSort {

    // Uses the merge sort algorithm to sort a list
    public static void mergeSort(ArrayList<Comparable> list) {

        System.out.println("Original List: " + list);

        int midpoint = list.size() / 2;
        ArrayList<Comparable> firstHalf = trim(list, 0, midpoint-1);
        ArrayList<Comparable> secondHalf = trim(list, midpoint, list.size()-1);

        System.out.println("Midpoint Index: " + midpoint);
        System.out.println("List A: " + firstHalf);
        System.out.println("List B: " + secondHalf);

        selectionSort(firstHalf);
        selectionSort(secondHalf);

        list = merge(firstHalf, secondHalf);

        System.out.println("Merged List: " + list);
    }

    // Uses the merge sort algorithm to sort two separate lists
    public static ArrayList<Comparable> mergeSort(ArrayList<Comparable> listA, ArrayList<Comparable> listB) {

        System.out.println( "List A:\n\t" +
                            "Length: " + listA.size() + "\n\t" +
                            "Values: " + listA + "\n" +
                            "List B:\n\t" +
                            "Length: " + listB.size() + "\n\t" +
                            "Values: "+ listB + "\n"
                            );
        selectionSort(listA);
        selectionSort(listB);

        ArrayList<Comparable> mergedList = merge(listA, listB);

        System.out.println("Merged List: " + mergedList);

        return new ArrayList<>(merge(listA, listB));
    }

    // Uses the selection sort to sort a list
    public static void selectionSort(ArrayList<Comparable> list) {

        System.out.println("Original List:\t" + list);

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

        System.out.println("Sorted List:\t" + list + "\n");
    }

    /**
     * Copies a section of an existing list.
     * @param list The list to be copied from
     * @param start The inclusive index to start copying from
     * @param end The inclusive index to stop copying from
     * @return A new list that is a segment of the original list
     */
    public static <T> ArrayList<T> trim(ArrayList<T> list, int start, int end) {
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
    public static ArrayList<Comparable> merge(ArrayList<Comparable> listA, ArrayList<Comparable> listB) {
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

    // <editor-fold defaultstate="collapsed" desc="Genesis Methods">
    public static ArrayList<Comparable> generateList() {
        return generateList(10, 0, 10);
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
        double random = (1-Math.random())*(max-min)+min;    // Generate a random double in between the min and max
        return (random - (int) random <= 0.5D) ? (int) Math.floor(random) : (int) Math.ceil(random);    // Round accordingly and cast to int
    }
    // </editor-fold>

    public static void main(String[] args) {
        // Use merge sort on two randomly generated lists with lengths varying from 1 to 20, and values from 0 to 20
        mergeSort(generateList(random(1, 20), 20), generateList(random(1, 20),20));
    }

}