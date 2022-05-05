package ru.cynteka;

import org.apache.lucene.search.spell.JaroWinklerDistance;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        /* You can change the input path to try other prepared input files */
        String inputPath = "src/main/resources/input.txt";
        String outputPath = "src/main/resources/output.txt";

        System.out.println("\n============ Start of the program ============\n");
        System.out.println("Input file path: " + inputPath + "\n");

        try (Scanner scannerMN = new Scanner(System.in);
             Scanner scanner = new Scanner(new File(inputPath));
             PrintWriter writer = new PrintWriter(outputPath)) {

            System.out.print("Please enter N: ");
            int n = scannerMN.nextInt();
            System.out.print("Please enter M: ");
            int m = scannerMN.nextInt();

            ArrayList<String> listN = new ArrayList<>();
            ArrayList<String> listM = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                if (scanner.hasNext()) {
                    listN.add(scanner.nextLine());
                }
            }

            for (int i = 0; i < m; i++) {
                if (scanner.hasNext()) {
                    listM.add(scanner.nextLine());
                }
            }

            n = listN.size();
            m = listM.size();

            /* Calculate distances for all pairs of strings */
            JaroWinklerDistance dis = new JaroWinklerDistance();
            Map<Coordinates, Float> allDistancesMap = new HashMap<>();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    float distance = dis.getDistance(listN.get(i), listM.get(j));
                    allDistancesMap.put(new Coordinates(i, j), distance);
                }
            }

            List<Map.Entry<Coordinates, Float>> sortedDistancesList = new ArrayList<>(allDistancesMap.entrySet());
            sortedDistancesList.sort(Map.Entry.<Coordinates, Float>comparingByValue().reversed());

            boolean[] alavailableN = new boolean[n];
            boolean[] alavailableM = new boolean[m];
            Arrays.fill(alavailableN, true);
            Arrays.fill(alavailableM, true);

            Map<Integer, Integer> pairCoordinatesMap = new HashMap<>();
            int possiblePairs = Math.min(m, n);
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

            for (int i = 0; i < listN.size(); i++) {
                if (pairCoordinatesMap.containsKey(i)) {
                    writer.println(listN.get(i) + ":" + listM.get(pairCoordinatesMap.get(i)));
                } else {
                    writer.println(listN.get(i) + ":?");
                }
            }

            if (m > n) {
                for (int i = 0; i < alavailableM.length; i++) {
                    if (alavailableM[i]) {
                        writer.println(listM.get(i) + ":?");
                    }
                }
            }

            System.out.println("\nThe program has been completed successfully.");
            System.out.println("Output file path: " + outputPath);
            System.out.println("\n============ End of the program ============");

        } catch (FileNotFoundException fnfe) {
            System.out.println("\nInput file not found on path: " + inputPath);
            System.out.println("Please enter the correct path and restart the program.");
        }
    }
}