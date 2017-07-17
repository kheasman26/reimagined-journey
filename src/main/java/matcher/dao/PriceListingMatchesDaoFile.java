package matcher.dao;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import matcher.model.PriceListingMatches;

/**
 * Implementation that writes to a file, matcher_challenge_results.json, in the directory where the app is run.
 */
public class PriceListingMatchesDaoFile implements PriceListingMatchesDao {
    public static final String OUTPUT_FILENAME = "matcher_challenge_results.json";

    private static final Logger logger = LoggerFactory.getLogger(PriceListingMatchesDaoFile.class);

    public void saveAll(List<PriceListingMatches> matches) {
        String outputPath = System.getProperty("user.dir") + OUTPUT_FILENAME;
        logger.info("writing matches to " + outputPath + " ...");

        ObjectMapper objectMapper = new ObjectMapper();

        OutputStream os = null;
        BufferedWriter br = null;
        try {
            os = new DataOutputStream(new FileOutputStream(OUTPUT_FILENAME));
            br = new BufferedWriter(new OutputStreamWriter(os));

            for (PriceListingMatches match : matches) {
                try {
                    br.write(objectMapper.writeValueAsString(match));
                    br.newLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e1) {
            throw new DaoRuntimeException(e1);

        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
