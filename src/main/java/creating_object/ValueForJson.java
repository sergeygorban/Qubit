package creating_object;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.function.Function;

/**
 * Created by Admin on 05.04.2018.
 */

public class ValueForJson implements Function<Cell,JsonElement>{

    @Override
    public JsonElement apply(Cell a) {

        String val = a.toString();
        JsonElement element;

        if (CellType.NUMERIC.name().equals(a.getCellTypeEnum().name())
                && Math.ceil(a.getNumericCellValue()) == a.getNumericCellValue()) {

            val = String.valueOf(Math.round(a.getNumericCellValue()));
        }

        if (val.startsWith("[")) {
            element = new JsonParser().parse(val).getAsJsonArray();

        } else if (val.startsWith("{")) {
            element = new JsonParser().parse(val).getAsJsonObject();

        } else {
            element = new JsonPrimitive(val);
        }

        return element;
    }
}
