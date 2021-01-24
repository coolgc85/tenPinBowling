package util;

import exception.BowlingException;

import model.RollLine;

import java.io.*;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileScoreReader {

    public List<RollLine> readFile(String fileName) throws BowlingException {
        List<RollLine> inputList = null;
        try{
            File inputFile = new File(fileName);
            InputStream inputStream = new FileInputStream(inputFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            inputList = br.lines().map(mapToRollLine).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new BowlingException("Error reading the file");
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
            throw new BowlingException("Error reading the file");
        }
        return inputList;
    }

    private Function<String, RollLine> mapToRollLine = (line) -> {
        String[] entry = line.split("\t");
        RollLine rollLine = new RollLine();
        rollLine.setPlayerName(entry[0]);
        rollLine.setPinsKnocked(entry[1]);

        return rollLine;
    };
}
