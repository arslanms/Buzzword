package sample;

import javax.json.*;
import javax.json.stream.JsonGenerator;
import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arslan on 11/24/16.
 */
public class FileController {

    public static final int NUM_MODES = 4;
    public static final int NUM_LEVELS = 8;

    public void saveData(BuzzData data, File to)  throws FileNotFoundException {


        JsonArrayBuilder mode1 = Json.createArrayBuilder();
        JsonArrayBuilder mode2 = Json.createArrayBuilder();
        JsonArrayBuilder mode3 = Json.createArrayBuilder();
        JsonArrayBuilder mode4 = Json.createArrayBuilder();

        for (int i = 0; i < NUM_LEVELS; i++)    {
            mode1.add(-1);
            mode2.add(-1);
            mode3.add(-1);
            mode4.add(-1);
        }

        JsonObject jsonObject = Json.createObjectBuilder().add("Username", data.getUsername())
                .add("Password", data.getPassword())
                .add("Mode 1", mode1)
                .add("Mode 2", mode2)
                .add("Mode 3", mode3)
                .add("Mode 4", mode4).build();


        Map<String, Object> properties = new HashMap<>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        StringWriter sw = new StringWriter();
        JsonWriter jsonWriter = writerFactory.createWriter(sw);
        jsonWriter.writeObject(jsonObject);
        jsonWriter.close();

        OutputStream os = new FileOutputStream(to.toString()); //to is the path
        JsonWriter jsonFileWriter = Json.createWriter(os);
        jsonFileWriter.writeObject(jsonObject);
        String prettyPrinted = sw.toString();
        PrintWriter pw = new PrintWriter(to.toString());
        pw.write(prettyPrinted);
        pw.close();


    }

}
