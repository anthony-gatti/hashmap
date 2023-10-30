import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Test {

    public static void main(String[] args) {

        StringComparator sComp = new StringComparator();
        TreeMap<String, String> tm = new TreeMap(sComp);
        HashMapSC<String, String> mapSC = new HashMapSC(100000);
        HashMapQP<String, String> mapQP = new HashMapQP(100000);

        File file = new File("emails.txt");
        try {
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String[] line = read.nextLine().split("\\ ");
                String key = line[0];
                String val = line[1];
                tm.add(key, val);
                mapSC.put(key, val);
                mapQP.put(key, val);
            }
            read.close();
        }

        // exception handling
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

        System.out.println("Testing get():");
        System.out.println("Username          Tree Map   HashMapSC   HashMapQP");

        File text = new File("mailingList.txt");
        try {
            Scanner read = new Scanner(text);
            int count = 0;
            while (count < 20) {
                count++;
                String[] line = read.nextLine().split("\\ ");
                String key = line[0];
                System.out.print(key + "    ");
                if (tm.containsKey(key)) {
                    tm.get(key);
                    System.out.print(tm.getIterations() + "        ");
                }
                if (mapSC.containsKey(key)) {
                    mapSC.get(key);
                    System.out.print(mapSC.getIterations() + "        ");
                }
                if (mapQP.containsKey(key)) {
                    mapQP.get(key);
                    System.out.print(mapQP.getIterations());
                }
                System.out.println();
            }
            read.close();

        }
        /*
         * Of the three hash tables, TreeMap was clearly the least efficient because it
         * took far more iterations than the other two. The two hash maps were expected
         * to have similar results, but due to an error in my code, HashMapQP performs
         * far less effeciently. When coded correctly, they would produce similar
         * results
         */

        // exception handling
        catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

        System.out.println();
        System.out.println("Testing put():");
        System.out.println("Size     HashMapSC   HashMapQP");

        for (int size = 50000; size <= 500000; size += 50000) {
            HashMapSC newSC = new HashMapSC(size);
            HashMapQP newQP = new HashMapQP(size);

            File txt = new File("emails.txt");
            try {
                Scanner read = new Scanner(txt);
                while (read.hasNextLine()) {
                    String[] line = read.nextLine().split("\\ ");
                    String key = line[0];
                    String val = line[1];
                    tm.add(key, val);
                    newSC.put(key, val);
                    newQP.put(key, val);
                }
                read.close();

            }
            /*
             * Between the two hashmaps, HashMapSC has less collisions, but not by many. The
             * two tables performed similarly, both imporving as the size of the table
             * increased, which makes sense for avoiding collisions. Due to errors in my
             * code, my results for not accurate for the QP collisions, so in practice, they
             * were far higher.
             */

            // exception handling
            catch (FileNotFoundException e) {
                System.out.println("File not found");
                System.exit(0);
            }

            System.out.println(size + "    " + newSC.getCollisions() + "     " + newQP.getCollisions());
        }

    }
}