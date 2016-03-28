//package auxiliary;
//package auxiliary;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daq
 */
public class Evaluation {

    private String clsName;
    private DataSet dataset;
    private DataSet testset;
    private double[][] tesfeatures;
    private double[] testlabel;
    private double accMean;
    private double accStd;
    private double rmseMean;
    private double rmseStd;

    public Evaluation() {
    }

    public Evaluation(DataSet dataset, DataSet testset,String clsName) {
        this.dataset = dataset;
        this.testset = testset;
        this.clsName = clsName;
    }
    public Evaluation(DataSet dataset, double[][] a,double[] b,String clsName) {
        this.dataset = dataset;
        this.tesfeatures = a;
        this.testlabel = b;
        this.clsName = clsName;
    }

    public double[] testWithData(){
    	boolean[] isCategory_train = dataset.getIsCategory();
        double[][] features_train = dataset.getFeatures();
        double[] labels_train = dataset.getLabels();
        
        double[][] features_test = this.tesfeatures;//testset.getFeatures;
        double[] labels_test = this.testlabel;//testset.getLabels;
        double[] measures = new double[1];
        double[] result = new double[labels_test.length];
        boolean isClassification = isCategory_train[isCategory_train.length - 1];
        try {

            NaiveBayes c  = (NaiveBayes) Class.forName("" + clsName).newInstance();
        	c.train(isCategory_train, features_train, labels_train);
        	
        	double error = 0;
            for (int j = 0; j < labels_test.length; j++) {
                double prediction = c.predict(features_test[j]);
                
                if (isClassification) {
                    if (prediction != labels_test[j]) {
                        error = error + 1;
                    }
                } else {
                    error = error + (prediction - labels_test[j]) * (prediction - labels_test[j]);
                }
                //System.out.println("result "+j+":"+prediction);
                result[j] = prediction;
            }
            
            if (isClassification) {
                measures[0] = 1 - error / labels_test.length;//accuracy = 1 - error
            } else {
                measures[0] = Math.sqrt(error / labels_test.length);
            }
            
//		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex) {
		} catch ( Exception ex) {
			// TODO Auto-generated catch block
			Logger.getLogger(Evaluation.class.getName()).log(Level.SEVERE, null, ex);
		}
        double[] mean_std = mean_std(measures);
        if (isClassification) {
            accMean = mean_std[0];
            accStd = mean_std[1];
        } else {
            rmseMean = mean_std[0];
            rmseStd = mean_std[1];
        }
        return result;

    }
    public void crossValidation() {
        int fold = 10;
        Random random = new Random(2013);
        int[] permutation = new int[10000];
        for (int i = 0; i < permutation.length; i++) {
            permutation[i] = i;
        }
        for (int i = 0; i < 10 * permutation.length; i++) {
            int repInd = random.nextInt(permutation.length);
            int ind = i % permutation.length;

            int tmp = permutation[ind];
            permutation[ind] = permutation[repInd];
            permutation[repInd] = tmp;
        }

        int[] perm = new int[dataset.getNumInstnaces()];
        int ind = 0;
        for (int i = 0; i < permutation.length; i++) {
            if (permutation[i] < dataset.getNumInstnaces()) {
                perm[ind++] = permutation[i];
            }
        }

        int share = dataset.getNumInstnaces() / fold;

        boolean[] isCategory = dataset.getIsCategory();
        double[][] features = dataset.getFeatures();
        double[] labels = dataset.getLabels();

        boolean isClassification = isCategory[isCategory.length - 1];

        double[] measures = new double[fold];
        for (int f = 0; f < fold; f++) {
            try {
                int numTest = f < fold - 1 ? share : dataset.getNumInstnaces() - (fold - 1) * share;
                double[][] trainFeatures = new double[dataset.getNumInstnaces() - numTest][dataset.getNumAttributes()];
                double[] trainLabels = new double[dataset.getNumInstnaces() - numTest];
                double[][] testFeatures = new double[numTest][dataset.getNumAttributes()];
                double[] testLabels = new double[numTest];

                int indTrain = 0, indTest = 0;
                for (int j = 0; j < dataset.getNumInstnaces(); j++) {
                    if ((f < fold - 1 && (j < f * share || j >= (f + 1) * share)) || (f == fold - 1 && j < f * share)) {
                        System.arraycopy(features[perm[j]], 0, trainFeatures[indTrain], 0, dataset.getNumAttributes());
                        trainLabels[indTrain] = labels[perm[j]];
                        indTrain++;
                    } else {
                        System.arraycopy(features[perm[j]], 0, testFeatures[indTest], 0, dataset.getNumAttributes());
                        testLabels[indTest] = labels[perm[j]];
                        indTest++;
                    }
                }

                NaiveBayes c = (NaiveBayes) Class.forName("auxiliary." + clsName).newInstance();
                c.train(isCategory, trainFeatures, trainLabels);

                double error = 0;
                for (int j = 0; j < testLabels.length; j++) {
                    double prediction = c.predict(testFeatures[j]);

                    if (isClassification) {
                        if (prediction != testLabels[j]) {
                            error = error + 1;
                        }
                    } else {
                        error = error + (prediction - testLabels[j]) * (prediction - testLabels[j]);
                    }
                }
                if (isClassification) {
                    measures[f] = 1 - error / testLabels.length;//accuracy = 1 - error
                } else {
                    measures[f] = Math.sqrt(error / testLabels.length);
                }
           // } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                } catch (Exception ex) {
                Logger.getLogger(Evaluation.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        double[] mean_std = mean_std(measures);
        if (isClassification) {
            accMean = mean_std[0];
            accStd = mean_std[1];
        } else {
            rmseMean = mean_std[0];
            rmseStd = mean_std[1];
        }
    }

    public double[] mean_std(double[] x) {
        double[] ms = new double[2];
        int N = x.length;

        ms[0] = 0;
        for (int i = 0; i < x.length; i++) {
            ms[0] += x[i];
        }
        ms[0] /= N;

        ms[1] = 0;
        for (int i = 0; i < x.length; i++) {
            ms[1] += (x[i] - ms[0]) * (x[i] - ms[0]);
        }
        ms[1] /= (N - 1);

        return ms;
    }

    public double getAccMean() {
        return accMean;
    }

    public double getAccStd() {
        return accStd;
    }

    public double getRmseMean() {
        return rmseMean;
    }

    public double getRmseStd() {
        return rmseStd;
    }
}
