package com.br.codefights.marathon.may2017;

/**
 * Created by rene on 27/05/17.
 */

/**
 * The inhabitants of the country Murmuria want to choose a new capital city.
 * To calculate which cities can become the capital, they draw a map of the country.
 * The map is an n × m matrix in which some cells are marked as cities.
 * A city can become the capital if, in this map, it has at least one city in the same row and at least one city in the
 * same column except itself.

 Given a (0, 1) matrix country where country[i][j] = 1 indicates a city, return the number of all the possible capitals in Murmuria.

 Example

 For

 country = [[0, 0, 1, 0, 1],
 [1, 0, 0, 0, 0],
 [1, 1, 1, 1, 1],
 [0, 0, 0, 1, 0],
 [1, 0, 1, 1, 0]]

 the output should be
 possibleCapitals(country) = 9.

 There are 12 cities on this map, and only 3 of them don't meet the requirements needed to be the capital:
 the city in the second row, the city in the fourth row, and city in the second column.

 Input/Output

 [time limit] 3000ms (java)
 [input] array.array.integer country

 The map of the country. country[i][j] = 1 if there is a city at that location and 0 otherwise.

 Guaranteed constraints:
 1 ≤ country.length ≤ 100,
 1 ≤ country[i].length ≤ 100,
 country[i].length = country[j].length,
 0 ≤ country[i][j] ≤ 1.

 [output] integer
 The number of possible capital cities in this country.
 */
public class PossibleCapitals {

    public static void main(String[] args) {
        int[][] country1 = {{0, 0, 1, 0, 1},
                {1, 0, 0, 0, 0},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 1, 0},
                {1, 0, 1, 1, 0}};

        System.out.println(possibleCapitals(country1) + " Expected: 9");

        int[][] country2 = {{0,0,0},
                {0,1,1},
                {1,0,0},
                {0,1,0}};

        System.out.println(possibleCapitals(country2) + " Expected: 1");

        int[][] country3 = {{1,1,0,0},
                {0,0,1,1},
                {0,0,1,1},
                {0,0,0,0}};

        System.out.println(possibleCapitals(country3) + " Expected: 4");

        int[][] country4 = {{1,0,0,1}};

        System.out.println(possibleCapitals(country4) + " Expected: 0");

        int[][] country5 = {{0,0,0,0},
                {0,1,0,0},
                {1,1,1,1},
                {0,0,1,1}};

        System.out.println(possibleCapitals(country5) + " Expected: 5");
    }

    private static int possibleCapitals(int[][] country) {
        int possibleCapitals = 0;

        for(int i = 0; i < country.length; i++) {

            for(int j = 0; j < country[0].length; j++) {

                if (country[i][j] != 1) {
                    continue;
                }

                int currentColumnCount = 0;

                for(int k = 0; k < country.length; k++) {
                    if (country[k][j] == 1) {
                        currentColumnCount++;
                    }
                }

                int currentRowCount = 0;

                for(int k = 0; k < country[0].length; k++) {
                    if (country[i][k] == 1) {
                        currentRowCount++;
                    }
                }

                if (currentRowCount >= 2 && currentColumnCount >= 2) {
                    possibleCapitals++;
                }
            }
        }

        return possibleCapitals;
    }

}
