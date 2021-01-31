package util;

import exception.BowlingException;
import model.RollLine;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileScoreReaderTest {

    String fileName = null;


    @Test
    void readStandardFile() throws BowlingException {
        FileScoreReader fr = new FileScoreReader();
        fileName = "TwoPlayers.txt";
        List<RollLine> rolLineList = fr.readFile(fileName);
        assertTrue(!rolLineList.isEmpty() && rolLineList.size() > FileScoreReader.MINIMUM_NORMAL_ROWS);
    }


    @Test
    void readNotEmptyFile() throws BowlingException {
        FileScoreReader fr = new FileScoreReader();
        fileName = "sample1.txt";
        assertTrue(!fr.readFile(fileName).isEmpty());
    }

    @Test
    void NoFileFound(){
        assertThrows(BowlingException.class,() -> {
            FileScoreReader fr = new FileScoreReader();
            fr.readFile(null);
        });
    }
}