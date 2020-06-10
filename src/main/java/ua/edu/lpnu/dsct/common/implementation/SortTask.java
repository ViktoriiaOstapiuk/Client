package ua.edu.lpnu.dsct.common.implementation;

import ua.edu.lpnu.dsct.common.abstraction.ITask;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SortTask implements ITask<byte[]>, Serializable {
    byte[] byteArray;

    public SortTask(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public double[] sort(double [] array) {
        int size = array.length;
        for(int i = 1; i < size; ++i) {
            for(int j = i; j > 0 && array[j-1] > array[j]; --j) {
                double tmp = array[j];
                array[j] = array[j-1];
                array[j-1] = tmp;
            }
        }
        return array;
    }

    @Override
    public byte[] execute() {
        String delimiter = " ";

        String content = new String(this.byteArray);
        String[] strArray = content.replace(System.lineSeparator(), delimiter).split(delimiter);
        double[] numberArray = Arrays.stream(strArray).mapToDouble(Double::parseDouble).toArray();

        double[] result = this.sort(numberArray);

        DecimalFormat decimalFormat = new DecimalFormat("#.#####");
        String strResult = Arrays.stream(result).mapToObj(decimalFormat::format).collect(Collectors.joining(delimiter));
        return strResult.getBytes();
    }
}
