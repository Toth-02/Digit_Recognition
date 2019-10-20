import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Brain {
    private double[][] weights;
    private int[][] correct;
    private static final int NUMBER_OF_NEURONS = 10;
    private static int NUMBER_OF_WEIGHTS_FOR_NEURON;
    private static final int MAX_NO_IMPROVEMENT_EPOCH = 100;

    void train(int epochs, int[][] inputs, int[] correct, double learningRate, boolean smartLearning){

        double totalWeights = 0;
        int consecutiveEqualTurns = 0;

        if (inputs.length != correct.length) {
            throw new RuntimeException("Inputs should have the length of correct for the training part!");
        }

        Random generator = new Random();

        NUMBER_OF_WEIGHTS_FOR_NEURON = inputs[0].length;

        this.correct = new int[correct.length][NUMBER_OF_NEURONS];
        for (int i = 0; i < correct.length; i++){
            this.correct[i][correct[i]] = 1;
        }

        weights = new double[NUMBER_OF_NEURONS][NUMBER_OF_WEIGHTS_FOR_NEURON];

        for (int i = 0; i < NUMBER_OF_NEURONS; i++){
            for (int j = 0; j < NUMBER_OF_WEIGHTS_FOR_NEURON; j++){
                weights[i][j] = generator.nextGaussian();
            }
        }

        for (int e = 0; e < epochs; e++){
            for (int i = 0; i < inputs.length; i++){
                double[] results = new double[NUMBER_OF_NEURONS];
                for (int j = 0; j < NUMBER_OF_NEURONS; j++){
                    double guess = 0;
                    for (int k = 0; k < NUMBER_OF_WEIGHTS_FOR_NEURON; k++){
                        guess += inputs[i][k] * weights[j][k];
                    }
                    results[j] = activation(guess);
                }
                for (int j = 0; j < NUMBER_OF_NEURONS; j++) {
                    double error = this.correct[i][j] - results[j];
                    if (!(error < -0.01 && error > 0.01)){
                        for (int k = 0; k < NUMBER_OF_WEIGHTS_FOR_NEURON; k++){
                            weights[j][k] += inputs[i][k] * error * learningRate;
                        }
                    }
                }
            }
            if (smartLearning){
                if (e == 0){
                    for (int i = 0; i < NUMBER_OF_NEURONS; i++){
                        for (int j = 0; j < NUMBER_OF_WEIGHTS_FOR_NEURON; j++){
                            totalWeights += weights[i][j];
                        }
                    }
                }
                else{
                    double currentWeight = 0;
                    for (int i = 0; i < NUMBER_OF_NEURONS; i++){
                        for (int j = 0; j < NUMBER_OF_WEIGHTS_FOR_NEURON; j++){
                            currentWeight += weights[i][j];
                        }
                    }

                    if (totalWeights == currentWeight){
                        if (++consecutiveEqualTurns == MAX_NO_IMPROVEMENT_EPOCH){
                            break;
                        }
                    }
                    else{
                        consecutiveEqualTurns = 0;
                    }
                    totalWeights = currentWeight;
                }
            }
        }
    }

    int guess(int[] number){
        double[] results = new double[NUMBER_OF_NEURONS];
        for (int i = 0; i < NUMBER_OF_NEURONS; i++) {
            double guess = 0;
            for (int j = 0; j < NUMBER_OF_WEIGHTS_FOR_NEURON; j++) {
                guess += number[j] * weights[i][j];
            }
            results[i] = activation(guess);
        }
        return getActivated(results);
    }

    void save(String path){
        File file = new File(path);
        try {
            FileWriter fw = new FileWriter(file);
            for (int i = 0; i < NUMBER_OF_NEURONS; i++) {
                for (int j = 0; j < NUMBER_OF_WEIGHTS_FOR_NEURON; j++) {
                    fw.write(weights[i][j] + "\n");
                }
            }
            fw.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    void load(String path){
        try {
            Scanner reader = new Scanner(new File(path));
            reader.useDelimiter("\\Z");

            String[] raw = reader.next().split("\n");
            NUMBER_OF_WEIGHTS_FOR_NEURON = raw.length / NUMBER_OF_NEURONS;

            weights = new double[NUMBER_OF_NEURONS][NUMBER_OF_WEIGHTS_FOR_NEURON];

            for (int i = 0; i < raw.length; i++){
                try {
                    weights[i / NUMBER_OF_WEIGHTS_FOR_NEURON][i % NUMBER_OF_WEIGHTS_FOR_NEURON] = Double.valueOf(raw[i]);
                }
                catch (NullPointerException e){
                    System.out.println(e.getMessage());
                }
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    private int getActivated(double[] results){
        int activated = 0;
        for (int j = 0; j < results.length; j++){
            if (results[j] == 1){
                activated = j;
                break;
            }
        }
        return activated;
    }

    private double sigmoid(double num){
        return 1. / (1 + Math.pow(Math.E, -num));
    }

    private int activation(double num){
        if (sigmoid(num) > 0.5){
            return 1;
        }
        return 0;
    }

}

