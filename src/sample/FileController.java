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
    private int[][] modes;

    public void saveData(BuzzData data, File to)  throws FileNotFoundException {


        JsonArrayBuilder mode1 = Json.createArrayBuilder();
        JsonArrayBuilder mode2 = Json.createArrayBuilder();
        JsonArrayBuilder mode3 = Json.createArrayBuilder();
        JsonArrayBuilder mode4 = Json.createArrayBuilder();

        modes = data.getModes();

        for (int i = 0; i < NUM_LEVELS; i++)    {
            mode1.add(modes[0][i]);
        }

        for (int i = 0; i < NUM_LEVELS; i++)    {
            mode2.add(modes[1][i]);
        }

        for (int i = 0; i < NUM_LEVELS; i++)    {
            mode3.add(modes[2][i]);
        }

        for (int i = 0; i < NUM_LEVELS; i++)    {
            mode4.add(modes[3][i]);
        }

        JsonObject jsonObject = Json.createObjectBuilder().add("Username", data.getUsername())
                .add("Password", encryptPassword(data.getPassword()))
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

    public boolean loadData(String username, String password, BuzzData data)  throws IOException {
        File file = new File(username + ".json");
        if (!file.exists()) {
            return false;
        }

        JsonObject jsonObject = loadJsonFile(username + ".json");

        String jsonUsername = jsonObject.getString("Username");
        String jsonPassword = jsonObject.getString("Password");
        jsonPassword = decryptPassword(jsonPassword);

        if (!password.equals(jsonPassword)) {
            return false;
        }

        JsonArray mode1 = jsonObject.getJsonArray("Mode 1");
        JsonArray mode2 = jsonObject.getJsonArray("Mode 2");
        JsonArray mode3 = jsonObject.getJsonArray("Mode 3");
        JsonArray mode4 = jsonObject.getJsonArray("Mode 4");

        int[][] modes = new int[4][8];

        int counter = 0;
        for (JsonValue obj : mode1) {
            modes[0][counter] = Integer.parseInt(obj.toString());
            counter++;
        }

        counter = 0;
        for (JsonValue obj : mode2) {
            modes[1][counter] = Integer.parseInt(obj.toString());
            counter++;
        }

        counter = 0;
        for (JsonValue obj : mode3) {
            modes[2][counter] = Integer.parseInt(obj.toString());
            counter++;
        }

        counter = 0;
        for (JsonValue obj : mode4) {
            modes[3][counter] = Integer.parseInt(obj.toString());
            counter++;
        }

        data.setModes(modes);
        data.setUsername(jsonUsername);
        data.setPassword(jsonPassword);

        return true;
    }

    private JsonObject loadJsonFile(String path) throws IOException {
        InputStream is = new FileInputStream(path);
        JsonReader jsonReader = Json.createReader(is);
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        is.close();
        return jsonObject;
    }

    private String encryptPassword(String password) {
        StringBuffer passwordBuffer = new StringBuffer(password);

        for (int i = 0; i < password.length(); i++) {
            int passwordChar = password.charAt(i);
            passwordChar *= 9;

            passwordBuffer.setCharAt(i, (char)passwordChar);
        }

        return passwordBuffer.toString();
    }

    private String decryptPassword(String password) {
        StringBuffer passwordBuffer = new StringBuffer(password);

        for (int i = 0; i < password.length(); i++) {
            int passwordChar = password.charAt(i);
            passwordChar /= 9;

            passwordBuffer.setCharAt(i, (char) passwordChar);
        }

        return passwordBuffer.toString();
    }
}
