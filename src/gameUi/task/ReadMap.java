package gameUi.task;

import javafx.scene.shape.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadMap {

    public int[][] readMap(String mapLocation) throws IOException {
        int[][] mapArray = new int[20][10];

        try {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(mapLocation)));

            while (sc.hasNextLine()) {
                for (int i = 0; i < mapArray.length; i++) {
                    String[] line = sc.nextLine().trim().split(" ");
                    for (int j = 0; j < line.length; j++) {
                        mapArray[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        return mapArray;
    }



}

