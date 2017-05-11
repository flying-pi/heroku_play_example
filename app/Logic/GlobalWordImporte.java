package Logic;

import models.WordFrequencyModel;
import models.WordSetInfo;
import org.jscience.mathematics.number.Real;
import play.Logger;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import static Logic.Util.clearWord;

/**
 * Created by yurabraiko on 11.05.17.
 */
public class GlobalWordImporte {
    private LinkedHashMap<String, Real> wordsFrequency = new LinkedHashMap<>();
    Real total = Real.ZERO;
    Real maxWordLenth = Real.ZERO;

    /**
     * @param lineWithWord line, formated `number word  word frequency` or `number word frequency`
     */
    public void addWord(String lineWithWord) {
        String[] puts = lineWithWord.split("\t");
        String word = clearWord(puts[1]).trim().toLowerCase();
        if (word.length() < 3 || word.contains(" "))
            return;
        Real count = Real.ZERO;
        try {
            count = Real.valueOf(puts[puts.length - 1]);
        } catch (Exception ignored) {
            return;
        }
        if (count.isLessThan(Real.valueOf(3)))
            return;
        total = total.plus(count);
        wordsFrequency.putIfAbsent(word, Real.ZERO);
        count = count.plus(wordsFrequency.get(word));
        wordsFrequency.put(word, count);
        if (count.isLargerThan(maxWordLenth))
            maxWordLenth = count;
    }

    public void importFromFile(String filePath) {
        Path path = FileSystems.getDefault().getPath(filePath);

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(this::addWord);
        } catch (IOException ex) {
            Logger.error("can not read file", ex);
        }

    }

    private Map<String, Real> normolize(Map<String, Real> words) {
        Map<String, Real> result = new LinkedHashMap<>();
        Real k = getNormolizeScaleK();
        words.forEach((s, aReal) -> result.put(s, (aReal.times(k).divide(total))));
        return result;
    }

    private Real getNormolizeScaleK() {
        if (!maxWordLenth.isLargerThan(Real.ZERO))
            return Real.ONE;
        return total.divide(maxWordLenth);
    }

    public void saveToBd() {
        Map<String, Real> words = normolize(Util.sortByValue(wordsFrequency, (o1, o2) -> {
            if (o1.equals(o2))
                return 0;
            return o1.isLessThan(o2) ? 1 : -1;
        }));
        Real k = getNormolizeScaleK();
        words.entrySet().parallelStream().forEach(stringRealEntry -> new WordFrequencyModel(
                stringRealEntry.getKey()
                , stringRealEntry.getValue().doubleValue(), Const.COMMON_WORD_TYPE).insert());

        new WordSetInfo(Const.COMMON_WORD_TYPE, (int)k.doubleValue()).insert();
        Logger.info("finish insert frequency in to db!!!");
    }


    public void dumpIndoToFile() {
        dumpIndoToFile("/Users/yurabraiko/temp/frequance");
    }

    public void dumpIndoToFile(String filePath) {
        File out = new File(filePath);
        OutputStreamWriter writer;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(out));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Logger.error("can not create file ", e);
            return;
        }
        Util.sortByValue(wordsFrequency, (o1, o2) -> {
            if (o1.equals(o2))
                return 0;
            return o1.isLessThan(o2) ? 1 : -1;
        }).forEach((s, aDouble) -> {
            try {
                writer.write(s + " => " + aDouble.toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
                Logger.error("can not write file ", e);
            }
        });
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.error("can not close file ", e);
        }
        Logger.info("finish write stat");
        Map<String, Real> words = normolize(wordsFrequency);
//        words.forEach((s, real) -> System.out.println(real));

        Real k = getNormolizeScaleK();
    }

    /**
     * Created by yurabraiko on 12.05.17.
     */
    public static class Const {
        public static final int COMMON_WORD_TYPE = 1;
    }
}
