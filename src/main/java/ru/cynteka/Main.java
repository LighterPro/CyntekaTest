package ru.cynteka;

import org.apache.lucene.search.spell.JaroWinklerDistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        String inputPath = "";
        String outputPath = "src/main/resources/output.txt";

        System.out.println("\n====================== Start of the program ======================\n");

        try (Scanner scannerInputPath = new Scanner(System.in);
             PrintWriter writer = new PrintWriter(outputPath)) {

            System.out.print("Enter a input file path (e.g. src/main/resources/input.txt): ");
            System.out.flush();
            inputPath = scannerInputPath.nextLine();
            File file = new File(inputPath);

            Reader reader = new Reader(file);
            List<List<String>> lists = reader.readStringsFromFile();

            List<String> listN = lists.get(0);
            List<String> listM = lists.get(1);

            System.out.println("\nlist N = " + listN);
            System.out.println("list M = " + listM);

            int nSize = listN.size();
            int mSize = listM.size();

            /* Calculate distances for all pairs of strings */
            JaroWinklerDistance dis = new JaroWinklerDistance();
            Map<Coordinates, Float> allDistancesMap = new HashMap<>();
            for (int i = 0; i < nSize; i++) {
                for (int j = 0; j < mSize; j++) {
                    float distance = dis.getDistance(listN.get(i), listM.get(j));
                    allDistancesMap.put(new Coordinates(i, j), distance);
                }
            }

            List<Map.Entry<Coordinates, Float>> sortedDistancesList = new ArrayList<>(allDistancesMap.entrySet());
            sortedDistancesList.sort(Map.Entry.<Coordinates, Float>comparingByValue().reversed());

            boolean[] alavailableN = new boolean[nSize];
            boolean[] alavailableM = new boolean[mSize];
            Arrays.fill(alavailableN, true);
            Arrays.fill(alavailableM, true);

            Map<Integer, Integer> pairCoordinatesMap = new HashMap<>();
            int possiblePairs = Math.min(mSize, nSize);
            int foundedPairs = 0;
            for (Map.Entry<Coordinates, Float> entry : sortedDistancesList) {
                if (foundedPairs == possiblePairs) {
                    break;
                }
                Coordinates tmpCoord = entry.getKey();
                int tmpN = tmpCoord.getNCoord();
                int tmpM = tmpCoord.getMCoord();
                if (!alavailableN[tmpN] | !alavailableM[tmpM]) {
                    continue;
                }
                pairCoordinatesMap.put(tmpN, tmpM);
                alavailableN[tmpN] = false;
                alavailableM[tmpM] = false;
                foundedPairs++;
            }

            List<String> result = new ArrayList<>();

            for (int i = 0; i < listN.size(); i++) {
                if (pairCoordinatesMap.containsKey(i)) {
                    result.add(listN.get(i) + ":" + listM.get(pairCoordinatesMap.get(i)));
                } else {
                    result.add(listN.get(i) + ":?");
                }
            }

            if (mSize > nSize) {
                for (int i = 0; i < alavailableM.length; i++) {
                    if (alavailableM[i]) {
                        result.add(listM.get(i) + ":?");
                    }
                }
            }

            for (String s : result) {
                writer.println(s);
            }

            System.out.println("\nThe program has been completed successfully.\n");
            System.out.println("Results: \n");
            for (String s : result) {
                System.out.println(s);
            }
            System.out.println("\nThe results are also written to the output file: " + outputPath);
            System.out.println("\n======================= End of the program =======================");

        } catch (FileNotFoundException fnfe) {
            System.out.println("\nInput file not found on path: " + inputPath);
            System.out.println("Please enter the correct path and restart the program.");
            System.out.println("\n======================= End of the program =======================");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}