package util;

import exception.BowlingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileScoreReaderTest {

    String fileName = null;



    @Test
    void readNotEmptyFile() throws BowlingException {
        FileScoreReader fr = new FileScoreReader();
        fileName = "sample.txt";
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