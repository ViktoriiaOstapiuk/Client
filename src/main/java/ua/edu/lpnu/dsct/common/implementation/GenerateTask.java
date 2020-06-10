package ua.edu.lpnu.dsct.common.implementation;

import ua.edu.lpnu.dsct.common.abstraction.ITask;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.stream.Collectors;

public class GenerateTask implements ITask<byte[]>, Serializable {
    private final long count;
    private final NumberType type;
    private final long min;
    private final long max;

    public GenerateTask(long count, NumberType type, long min, long max) {
        this.count = count;
        this.type = type;
        this.min = min;
        this.max = max;
    }

    @Override
    public byte[] execute() {
        String delimiter = " ";
        Random random = new Random();

        if (this.type == NumberType.WHOLE) {
            return random
                    .longs(this.count, this.min, this.max)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(delimiter))
                    .getBytes();
        } else {
            DecimalFormat decimalFormat = new DecimalFormat("#.###");
            return random.doubles(this.count, this.min, this.max)
                    .mapToObj(decimalFormat::format)
                    .collect(Collectors.joining(delimiter))
                    .getBytes();
        }
    }
}
