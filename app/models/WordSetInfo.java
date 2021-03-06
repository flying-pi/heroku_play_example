package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mongodb.BasicDBObject;
import org.bson.types.ObjectId;
import org.jongo.MongoCollection;
import play.Play;
import uk.co.panaxiom.playjongo.PlayJongo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yurabraiko on 11.05.17.
 */
public class WordSetInfo {
    public static PlayJongo jongo = Play.application().injector().instanceOf(PlayJongo.class);

    public static MongoCollection wordSetInfo() {
        return jongo.getCollection("WordSetInfo");
    }

    static {

        Map<String,Integer> map = new HashMap<>();
        map.put("type",1);

        wordSetInfo().getDBCollection().createIndex(new BasicDBObject(map),null,true);
    }

    @JsonProperty("_id")
    public ObjectId id;

    public  int type;

    public int scaleK;


    public WordSetInfo(int type, int scaleK){
        this.type = type;
        this.scaleK = scaleK;
    }

    public WordSetInfo insert() {
        wordSetInfo().save(this);
        return this;
    }
}
