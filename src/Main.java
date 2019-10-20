import java.util.Scanner;

public class Main {

    static int[][] linearizedTridimensional(int[][][] matrix){
        int[][] dataset = new int [matrix.length][matrix[0].length * matrix[0][0].length + 1];
        for (int k = 0; k < matrix.length; k++){
            for (int i = 0; i < matrix[0].length; i++){
                for (int j = 0; j < matrix[0][0].length; j++){
                    dataset[k][i * matrix[0][0].length + j] = matrix[k][i][j];
                }
            }
            dataset[k][dataset[0].length - 1] = 1;
        }
        return dataset;
    }

    static int[] linearizedBidimensional(int[][] matrix){
        int[] data = new int [matrix.length * matrix[0].length + 1];
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[0].length; j++){
                data[i * matrix[0].length + j] = matrix[i][j];
            }
        }
        data[data.length - 1] = 1;
        return data;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Brain brain = new Brain();

        System.out.print("1. Learn the network\n2. Guess a number\nYour choice: ");
        choice = scanner.nextInt();

        switch (choice){
            case 1:
                int[][][] dataset = new int [][][] {
                        {
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 0, 1},
                                {1, 0, 1},
                                {1, 1, 1}
                        },
                        {
                                {0, 1, 0},
                                {0, 1, 0},
                                {0, 1, 0},
                                {0, 1, 0},
                                {0, 1, 0}
                        },
                        {
                                {1, 1, 1},
                                {0, 0, 1},
                                {1, 1, 1},
                                {1, 0, 0},
                                {1, 1, 1}
                        },
                        {
                                {1, 1, 1},
                                {0, 0, 1},
                                {1, 1, 1},
                                {0, 0, 1},
                                {1, 1, 1}
                        },
                        {
                                {1, 0, 1},
                                {1, 0, 1},
                                {1, 1, 1},
                                {0, 0, 1},
                                {0, 0, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 0},
                                {1, 1, 1},
                                {0, 0, 1},
                                {1, 1, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 0},
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1}
                        },
                        {
                                {1, 1, 1},
                                {0, 0, 1},
                                {0, 0, 1},
                                {0, 0, 1},
                                {0, 0, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1},
                                {0, 0, 1},
                                {1, 1, 1}
                        },
                        {
                                {0, 1, 0},
                                {0, 1, 0},
                                {1, 1, 0},
                                {1, 1, 0},
                                {0, 1, 1}
                        },
                        {
                                {1, 1, 0},
                                {0, 0, 1},
                                {0, 0, 1},
                                {1, 0, 0},
                                {1, 1, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 1},
                                {0, 0, 1},
                                {0, 0, 1},
                                {0, 0, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1},
                                {0, 0, 1},
                                {0, 1, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 0, 1},
                                {1, 0, 1},
                                {0, 1, 1}
                        },
                        {
                                {1, 1, 1},
                                {0, 0, 1},
                                {1, 1, 1},
                                {0, 0, 1},
                                {0, 1, 1}
                        },
                        {
                                {1, 0, 1},
                                {0, 0, 1},
                                {1, 1, 1},
                                {0, 0, 1},
                                {0, 0, 1}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 0},
                                {1, 1, 1},
                                {0, 0, 1},
                                {1, 1, 0}
                        },
                        {
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 0}
                        },
                        {
                                {0, 1, 1},
                                {1, 0, 0},
                                {1, 1, 1},
                                {1, 0, 1},
                                {1, 1, 1}
                        },

                };

                int[] rightLabels = new int [] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 7, 9, 0, 3, 4, 5, 8, 6};

                System.out.println("Learning...");

                brain.train(50000, linearizedTridimensional(dataset), rightLabels, 0.1, true);
                brain.save("weights.txt");

                System.out.println("Done! Saved to the file.");

                break;

            case 2:

                brain.load("weights.txt");

                int[][] matrix = new int[5][3];

                scanner.nextLine();

                String currentLine;
                System.out.println("Input grid:");
                for (int i = 0; i < matrix.length; i++){
                    currentLine = scanner.nextLine();
                    for (int j = 0; j < currentLine.trim().length(); j++){
                        matrix[i][j] = (currentLine.charAt(j) == '_' ? 0 : 1);
                    }
                }

                System.out.println("This number is: " + brain.guess(linearizedBidimensional(matrix)));

                break;
        }
    }
}