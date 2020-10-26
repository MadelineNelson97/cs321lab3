import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HashTest {

    public static void main(String[] args) {
        System.out.println("A good table size is found: 95791");

        HashTable linearHT = new HashTable(95791, "linear");
        HashTable doubleHT = new HashTable(95791, "double");
        File linearDump = new File("linear-dump");
        File doubleDump = new File("double-dump");
        double load = Double.parseDouble(args[1]);

        try {
            if (linearDump.createNewFile()) {
                System.out.println("Linear dump created");
            }
        } catch (IOException e) {
            System.out.println("Error occurred with linear dump");
            e.printStackTrace();
        }
        try {
            if (doubleDump.createNewFile()) {
                System.out.println("Double dump created");
            }
        } catch (IOException e) {
            System.out.println("Error occurred with double dump");
            e.printStackTrace();
        }

        switch (args[0]) {
            case "1":
                System.out.println("Data source type: java.util.Random\n");
                Random rand = new Random();
                while (linearHT.getLoad() <= load && doubleHT.getLoad() <= load) {
                    HashObject<Integer> hashObject = new HashObject<Integer>(rand.nextInt());
                    linearHT.add(hashObject);
                    doubleHT.add(hashObject);
                }
                printResults(linearHT);
                printResults(doubleHT);
                if (args.length == 3 && args[2].equals("1")) {
                    printTableToFile(linearHT, linearDump);
                    printTableToFile(doubleHT, doubleDump);
                }
                break;
            case "2":
                System.out.println("Data source type: System.currentTimeMillis()\n");
                // TODO: fix System.currentTimeMillis() case
                while (linearHT.getLoad() <= Double.parseDouble(args[1]) && doubleHT.getLoad() <= Double.parseDouble(args[1])) {
                    HashObject<Long> hashObject = new HashObject<Long>(System.currentTimeMillis());
                    linearHT.add(hashObject);
                    doubleHT.add(hashObject);
                }
                printResults(linearHT);
                printResults(doubleHT);
                if (args.length == 3 && args[2].equals("1")) {
                    printTableToFile(linearHT, linearDump);
                    printTableToFile(doubleHT, doubleDump);
                }
                break;
            case "3":
                System.out.println("Data source type: word-list\n");
                // TODO: complete for word-list file case, CHANGE AT END to use filename input!!!!!
                File words = new File("/Users/MadelineNelson/Desktop/lab3/src/files/word-list");
                try {
//                    Scanner scanerino = new Scanner(words);
//                    while (scanerino.hasNext() && linearHT.getLoad() < Double.parseDouble(args[1]) && doubleHT.getLoad() < Double.parseDouble(args[1])) {
//                        HashObject<String> hashObject= new HashObject<String>(scanerino.next());
//                        linearHT.add(hashObject);
//                        doubleHT.add(hashObject);
//                    }
//                    scanerino.close();
//                    BufferedReader in = new BufferedReader(new FileReader(words));
//                    String line;
//                    while ((line = in.readLine()) != null && linearHT.getLoad() < Double.parseDouble(args[1])) {
//                            StringTokenizer stToken = new StringTokenizer(line);
//                            while(stToken.hasMoreTokens()){
//                                HashObject<String> hashObject= new HashObject<String>(stToken.nextToken());
//                                linearHT.add(hashObject);
//                                doubleHT.add(hashObject);
//                            }
//                    }

                    BufferedReader in = new BufferedReader(new FileReader(words));
                    String line;
                    while ((linearHT.getLoad() <= Double.parseDouble(args[1]) && (line = in.readLine()) != null)) {
//                        if (linearHT.getN() >= 47890) {
//                            System.out.println(linearHT.getTotalProbes() + " " + linearHT.getN());
//                        }
                        HashObject<String> hashObject = new HashObject<String>(line);
                        linearHT.add(hashObject);
                        doubleHT.add(hashObject);
                    }
                    printResults(linearHT);
                    printResults(doubleHT);
                    if (args.length == 3 && args[2].equals("1")) {
                        printTableToFile(linearHT, linearDump);
                        printTableToFile(doubleHT, doubleDump);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }

    }

    private static void printResults(HashTable hashTable) {
        System.out.println("Using " + hashTable.getType() + " hashing....");
        System.out.println("Input " + hashTable.getTotalElements() + " elements of which " + hashTable.getTotalDuplicates() + " are duplicates");
        System.out.print("load factor = " + String.format("%.1f", hashTable.getLoad()));
        System.out.println(", Avg. no. of probes: " + (double) hashTable.getTotalProbes() / hashTable.getN() + "\n");
    }

    private static void printTableToFile(HashTable hashTable, File dump) {
        try {
            FileWriter write2 = new FileWriter(dump);
            PrintWriter pwOb2 = new PrintWriter(write2, false);
            pwOb2.flush();
            write2.write(hashTable.toString());
            write2.close();
            pwOb2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
